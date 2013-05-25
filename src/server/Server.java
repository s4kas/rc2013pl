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

    public static boolean processRegister(CSRegister msg) {
        Contact user = msg.getUser();
        user.setConnected(true);
        users.put(user.getName(), user);
        Message response = new SCRegister(getOnlineUser(user.getName()), conn);
        protocol.sendMessage(response, user.getHost(), user.getPort());
        return true;
    }

    public static boolean processStartMessage(CSStartMessage msg) {
        // get contact from users
        Contact destinationUser = users.get(msg.getUser2Talk());
        // Create message to check if user is connected
        SCUpdateInfo request = new SCUpdateInfo(conn);
        request.setRequesterName(destinationUser.getName());

        if (destinationUser == null) {
            return false;
        } else {
            protocol.sendMessage(request, destinationUser.getHost(), destinationUser.getPort());
            return true;
        }
    }

    public static boolean processUpdateInfo(CSUpdateInfo msg) {
        Contact requesterContact = users.get(msg.getRequesterName());
        SCStartMessage response = new SCStartMessage(msg.getUser(), conn);
        protocol.sendMessage(response, requesterContact.getHost(), requesterContact.getPort());
        return true;
    }

    private static ArrayList<String> getOnlineUser(String requesterName) {
        ArrayList<String> resultList = new ArrayList<>();
        for (Contact contact : users.values()) {
            if (contact.isConnected() && !requesterName.equals(contact.getName())) {
                resultList.add(contact.getName());
            }
        }
        return resultList;
    }
}
