/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import common.ConnectionHandler;
import common.io.FileUtils;
import common.protocol.*;
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
    private static ClientMainFrame mainFrame;
    private static IProtocol protocol;
    private static ConnectionHandler conn;
    private static Timer sendRegisterTimer, signInTimeout;

    public static void main(String[] args) {
    	prepareClient();
    	
        //start the UI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
                //register the mainFrame as observer
                clientModel.addObserver(mainFrame);
            }
        });

        //start a connection to process incoming messages
        startConnection();
    }
    
    private static void prepareClient() {
    	//instantiate a new Map to control the open chat windows
        listOfOpenChatModels = new ConcurrentHashMap<String, ChatModel>();

        //create the client model
        clientModel = new ClientModel();
        
        //Protocol
        protocol = new Protocol();
    }

    private static void createAndShowUI() {
        //Create and set up the Main window.
        mainFrame = new ClientMainFrame();

        //Create and set up the Debug window.
        //DebugFrame debugFrame = new DebugFrame(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());

        mainFrame.toFront();
    }

    public static void signIn(String username) {
        //update the Model
        clientModel.setSignedInUser(new Contact(username, true,
        		conn.getHost() , conn.getPort(), new ArrayList<String>()));
        clientModel.setSigningIn();

        //Timeout if sign in unsuccessful
        signInTimeout = new Timer();
        signInTimeout.schedule(new TimerTask() {
            @Override
            public void run() {
                if (clientModel.isSigningIn()) {
                    clientModel.setErrorMsg(UIConstants.CLIENT_FAILED_CONNECT);
                    clientModel.setSignedOut();
                }
            }
        }, CLIENT_SERVER_TIMEOUT);
        
        // send a register every 1 min to update user list
        sendRegisterTimer = new Timer();
        sendRegisterTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //send a register to the server
                sendRegister();
            }
        }, 0, 1 * 10 * 1000);
    }
    
    public static void logOut() {
    	//stop
    	sendRegisterTimer.cancel();
    	sendRegisterTimer.purge();
    	signInTimeout.cancel();
    	signInTimeout.purge();
    	conn.stopServer();
    	//update model
    	clientModel.setSignedOut();
    	
    	//start
    	prepareClient();
    	//register the mainFrame as observer
        clientModel.addObserver(mainFrame);
    	startConnection();
    }
    
    public static void saveFile(String chatUser, int fileIndex, File file) {
    	ChatModel chatModel = listOfOpenChatModels.get(chatUser);
    	byte[] b = chatModel.getReceivedFile(fileIndex);
    	
    	FileUtils.saveFile(b, file);
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
    
    private static void openNewChat(Contact contact) {
    	//update the model
    	clientModel.addToUserListWithChat(contact);
    	
    	//open new chat
    	ChatModel chatModel = new ChatModel();
    	String user2Talk = contact.getName();
        chatModel.addObserver(new ChatFrame(user2Talk));
        listOfOpenChatModels.put(user2Talk, chatModel);
    }
    
    public static void startMessage(String user2SendImage, File file) {
    	byte[] image = FileUtils.loadFile(file);
    	
    	//Update Model
        ChatModel chatModel = listOfOpenChatModels.get(user2SendImage);
        chatModel.addContent(new Object[]{clientModel.getSignedInUser().getName(),
        		image});

    	//send the image
        Contact contact = clientModel.getUserWithChat(user2SendImage);
        if (contact == null) { //no connection established to the user
            sendStartMessage(user2SendImage);
        } else { //direct connection to user
            sendMessage(contact, image);
        }
    }

    public static void startMessage(String user2Talk, String message) {
        //Update Model
        ChatModel chatModel = listOfOpenChatModels.get(user2Talk);
        chatModel.addContent(new String[]{clientModel.getSignedInUser().getName(),
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
        conn = new ConnectionHandler(protocol.getCurrentHost(), 0, true);

        //Protocol is listening to process incoming messages
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
        ConnectionHandler connSend = new ConnectionHandler(protocol.getServerHost(),
        		protocol.getServerPort(), false);
        Message msg = new CSStartMessage(clientModel.getSignedInUser(), user2Talk, conn);
        connSend.setObject(msg);
        new Thread(connSend).start();
    }
    
    private static void sendMessage(Contact contact, byte[] image) {
    	CCMessage request = new CCMessage(new PngCapability(image), 
        		conn, clientModel.getSignedInUser().getName());
        protocol.sendMessage(request, contact.getHost(), contact.getPort());
    }

    private static void sendMessage(Contact contact, String message) {
        CCMessage request = new CCMessage(new TextCapability(message), 
        		conn, clientModel.getSignedInUser().getName());
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
    	//open new chat window
    	openNewChat(msg.getRequester());
    	
        CSUpdateInfo response = new CSUpdateInfo(clientModel.getSignedInUser(), conn);
        response.setRequester(msg.getRequester());
        protocol.sendMessage(response, msg.getSenderHost(), msg.getSenderPort());
        return true;
    }

    public static boolean processMessage(CCMessage msg) {
    	ChatModel chatModel = listOfOpenChatModels.get(msg.getSenderName());
    	
    	//Update Model
    	ICapability cap = msg.getCapabilityContent();
    	if (cap instanceof TextCapability) {
    		chatModel.addContent(new Object[] {msg.getSenderName(), 
    				((TextCapability) cap).getText()});
    	} else if (cap instanceof PngCapability) {
    		chatModel.addContent(new Object[] {msg.getSenderName(), 
    				((PngCapability) cap).getPngImage()});
    	}
        return true;
    }
}
