/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import common.ConnectionHandler;
import common.error.ThreadUncaughtExceptionHandler;
import common.protocol.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import server.ui.ServerMainFrame;
import server.ui.ServerModel;

/**
 *
 * @author Emanuel
 */
public class Server {

    private static ServerModel serverModel;
    private static IProtocol protocol;
    private static ConnectionHandler conn;
    private static ConcurrentHashMap<String, Contact> users = new ConcurrentHashMap<>();

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
        ServerMainFrame mainFrame = new ServerMainFrame();

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
        
        conn = new ConnectionHandler(protocol.getCurrentHost(), protocol.getServerPort(), true);
        conn.addObserver(protocol);

        // send a register every 1 min to update user list
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //send a register to the server
                updateUsers();
            }
        }, 1 * 30 * 1000, 1 * 30 * 1000);

        //a thread to listen for incoming messages
        ThreadUncaughtExceptionHandler exceptionHandler = protocol.getExceptionHandlerInstance();
        Thread thread = new Thread(conn);
        thread.setName("startServer");
        thread.setUncaughtExceptionHandler(exceptionHandler);
        thread.start();
        
        
    }

    private static void updateUsers() {
        for (String key : users.keySet()) {
            Contact user = users.get(key);
            // create a java calendar instance
            Calendar calendar = Calendar.getInstance();

            // get a java.util.Date from the calendar instance.
            // this date will represent the current instant, or "now".
            Date now = calendar.getTime();
            
            calendar.setTime(user.getLastUpdate());
            calendar.add(Calendar.SECOND, 30);
            Date lastUpdate = calendar.getTime();
            
            if (lastUpdate.compareTo(now)<0) {
                users.remove(key);
            }
        }
    }

    public static void stopServer() {
        conn.stopServer();
        serverModel.setStarted(false);
    }

    public static boolean processRegister(CSRegister msg) {
        Contact user = msg.getUser();
        user.setConnected(true);
        user.setLastUpdate(new Date());
        users.put(user.getName(), user);
        Message response = new SCRegister(getOnlineUser(user.getName()), conn);
        protocol.sendMessage(response, user.getHost(), user.getPort());
        return true;
    }

    public static boolean processStartMessage(CSStartMessage msg) {
        // get contact from users
        Contact destinationUser = users.get(msg.getUser2Talk());
        if (destinationUser == null) {
            return false;
        }
        // Create message to check if user is connected
        SCUpdateInfo request = new SCUpdateInfo(conn);
        request.setDestUser(msg.getUser2Talk());
        request.setRequester(msg.getUser());

        protocol.sendMessage(request, destinationUser.getHost(), destinationUser.getPort());
        return true;
    }

    public static boolean processUpdateInfo(CSUpdateInfo msg) {
        Contact requesterContact = users.get(msg.getRequester().getName());
        SCStartMessage response = new SCStartMessage(msg.getUser(), conn);
        protocol.sendMessage(response, requesterContact.getHost(), requesterContact.getPort());
        return true;
    }
    
    public static void processExceptionSCUpdateInfo(SCUpdateInfo sCmsg) {
    	CSUpdateInfo response = new CSUpdateInfo(new Contact(sCmsg.getDestUser()), conn);
		response.setRequester(sCmsg.getRequester());
		processUpdateInfo(response);
	}

    private static ArrayList<String> getOnlineUser(String requesterName) {
        ArrayList<String> resultList = new ArrayList<>();
        for (Contact contact : users.values()) {
            if (contact.isConnected() && !requesterName.equals(contact.getName())) {
                resultList.add(contact.getName());
            }
        }
        Collections.sort(resultList);
        return resultList;
    }
}
