/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import common.ConnectionHandler;
import common.protocol.*;
import common.ui.DebugFrame;
import java.util.ArrayList;
import java.util.HashMap;
import server.ui.ServerMainFrame;
import server.ui.ServerModel;

/**
 *
 * @author Emanuel
 */
public class Server {

    private static ServerMainFrame mainFrame;
    private static ServerModel serverModel;
    private static DebugFrame debugFrame;
    private static IProtocol protocol;
    private static ConnectionHandler conn;
    private static HashMap<String, Contact> users = new HashMap<>();

    public static void main(String[] args) {
        //start the UI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
            }
        });
    }

    private static void createAndShowUI() {
        //Create and set up the Main window.
        mainFrame = new ServerMainFrame();

        //start a server Model and register mainFrame observer
        serverModel = new ServerModel();
        serverModel.addObserver(mainFrame);

        //Create and set up the Debug window.
        //debugFrame = new DebugFrame(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());

        mainFrame.toFront();
    }

    public static void startServer() {
        //update UI
        serverModel.setStarted(true);

        //a protocol to process incoming messages
        //instantiate a new connection handler
        protocol = new Protocol();
        conn = new ConnectionHandler(null, protocol.getServerPort(), true);
        conn.addObserver(protocol);

        //a thread to listen for incoming messages
        new Thread(conn).start();
    }

    public static void stopServer() {
        conn.stopServer();
        serverModel.setStarted(false);
    }

    public static boolean processRegister(CSRegister msg, String host, int port) {
        Contact user = msg.getUser();
        user.setConnected(true);
        users.put(user.getName(), user);
        Message response = new SCRegister(getOnlineUser());
        sendMessage(response, user.getHost(), user.getPort());
        return true;
    }

    public static boolean processStartMessage(CSStartMessage msg, String host, int port) {
        // get contact from users
        Contact destinationUser = users.get(msg.getUser2Talk());
        // Create message to check if user is connected
        SCUpdateInfo request = new SCUpdateInfo();
        request.setRequesterName(destinationUser.getName());
        
        if (destinationUser == null) {
            return false;
        } else {
            sendMessage(request, destinationUser.getHost(), destinationUser.getPort());
            return true;
        }
    }
    //TODO: change object to the proper message

    public static boolean processUpdateInfo(Object msg, String host, int port) {
        return true;
    }

    private static void sendMessage(Message msg, String host, int port) {
        ConnectionHandler connection = new ConnectionHandler(host, port, false);
        connection.setObject(msg);
        new Thread(connection).start();
    }

    private static ArrayList<String> getOnlineUser() {
        ArrayList<String> resultList = new ArrayList<>();
        for (Contact contact : users.values()) {
            if (contact.isConnected()) {
                resultList.add(contact.getName());
            }
        }
        return resultList;
    }
}
