/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import common.ConnectionHandler;
import common.protocol.*;
import common.ui.DebugFrame;
import common.ui.UIConstants;
import client.ui.ChatFrame;
import client.ui.ChatModel;
import client.ui.ClientMainFrame;
import client.ui.ClientModel;

/**
 *
 * @author Emanuel
 */
public class Client {

    private static final int CLIENT_SERVER_TIMEOUT = 10000; //10 sec.
    private static Map<String, ChatModel> listOfOpenChatModels;
    private static ClientModel clientModel;
    private static DebugFrame debugFrame;
    private static IProtocol protocol;
    private static ConnectionHandler conn;

    public static void main(String[] args) {
        //start the UI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
            }
        });

        //start a connection to process incoming messages
        startConnection();

        //instantiate a new Map to control the open chat windows
        listOfOpenChatModels = new HashMap<String, ChatModel>();
    }

    private static void createAndShowUI() {
        //Create and set up the Main window.
        ClientMainFrame mainFrame = new ClientMainFrame();

        //create the client model and
        //register the mainFrame as observer
        clientModel = new ClientModel();
        clientModel.addObserver(mainFrame);

        //Create and set up the Debug window.
        //debugFrame = new DebugFrame(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());

        mainFrame.toFront();
    }

    public static void signIn(String username) {
        //update the Model
        clientModel.setSignedInUser(new Contact(username, true,
                "localhost", conn.getPort(), new ArrayList<String>()));
        clientModel.setSigningIn();

        //Timeout if sign in unsuccessful
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (clientModel.isSigningIn()) {
                    clientModel.setErrorMsg(UIConstants.CLIENT_FAILED_CONNECT);
                    clientModel.setSignedOut();
                }
            }
        }, CLIENT_SERVER_TIMEOUT);
        // send a register every 1 min to update user list
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //send a register to the server
                sendRegister();
            }
        }, 0, 1 * 10 * 1000);

    }

    public static void startChat(int userListIndex) {
        String user2Talk = clientModel.getUserList().get(userListIndex);

        //open new chat
        ChatModel chatModel = listOfOpenChatModels.get(user2Talk);
        if (chatModel == null) {
            chatModel = new ChatModel();
            chatModel.addObserver(new ChatFrame(user2Talk));
            listOfOpenChatModels.put(user2Talk, chatModel);
            sendStartMessage(user2Talk);
        } else {
            chatModel.setFocus();
        }
    }

    public static void startMessage(String user2Talk, String message) {
        //Update Model
        ChatModel chatModel = listOfOpenChatModels.get(user2Talk);
        chatModel.setMessage(new String[]{clientModel.getSignedInUser().getName(),
                    message});

        //get the contact for this user
        Contact contact = clientModel.getUserWithChat(user2Talk);
        if (contact == null) { //no connection established to the user
            sendStartMessage(user2Talk);
        } else { //direct connection to user
            sendMessage(contact, message);
        }

    }

    private static void startConnection() {
        //Instantiate a new connection
        conn = new ConnectionHandler(null, 0, true);

        //Protocol is listening to process incoming messages
        protocol = new Protocol();
        conn.addObserver(protocol);

        //Start a thread to listen for connections
        new Thread(conn).start();
    }

    private static void sendRegister() {
        ConnectionHandler connSend = new ConnectionHandler(protocol.getServerHost(),
                protocol.getServerPort(), false);
        Message msg = new CSRegister(clientModel.getSignedInUser(), conn);
        connSend.setObject(msg);
        new Thread(connSend).start();
    }

    private static void sendStartMessage(String user2Talk) {
        ConnectionHandler connSend = new ConnectionHandler("localhost",
                protocol.getServerPort(), false);
        Message msg = new CSStartMessage(clientModel.getSignedInUser(), user2Talk, conn);
        connSend.setObject(msg);
        new Thread(connSend).start();
    }

    private static void sendMessage(Contact contact, String message) {
        CCMessage request = new CCMessage(message, conn, clientModel.getSignedInUser().getName());
        protocol.sendMessage(request, contact.getHost(), contact.getPort());
    }

    public static boolean processRegister(SCRegister msg) {
        clientModel.setUserList(msg.getUsersList());
        clientModel.setSignedIn();
        return true;
    }

    public static boolean processStartMessage(SCStartMessage msg) {
        // TODO: this is the settings of the window for msg.user
        clientModel.addToUserListWithChat(msg.getUser());
        return true;
    }

    public static boolean processUpdateInfo(SCUpdateInfo msg) {
        CSUpdateInfo response = new CSUpdateInfo(clientModel.getSignedInUser(), conn);
        response.setRequesterName(msg.getRequesterName());
        protocol.sendMessage(response, msg.getSenderHost(), msg.getSenderPort());
        return true;
    }

    public static boolean processMessage(CCMessage msg) {
        //Update Model
        ChatModel chatModel = listOfOpenChatModels.get(msg.getSenderName());
        chatModel.setMessage(new String[]{msg.getSenderName(),msg.getText()});
        return true;
    }
}
