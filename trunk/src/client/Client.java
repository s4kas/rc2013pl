/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import common.ConnectionHandler;
import common.protocol.*;
import common.ui.DebugFrame;
import common.ui.UIConstants;
import client.ui.ClientMainFrame;
import client.ui.ClientModel;

/**
 *
 * @author Emanuel
 */
public class Client {
	
	private static final int CLIENT_SERVER_TIMEOUT = 10000; //10 sec.
	
	private static ClientMainFrame mainFrame;
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
	}
    
    private static void log(String msg) {
        debugFrame.log("Client: " + msg);
    }
	
	private static void createAndShowUI() {
		//Create and set up the Main window.
		mainFrame = new ClientMainFrame();
		
		//create the client model and
		//register the mainFrame as observer
		clientModel = new ClientModel();
		clientModel.addObserver(mainFrame);
        
        //Create and set up the Debug window.
        //debugFrame = new DebugFrame(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());

        mainFrame.toFront();
	}
	
	public static void signIn(String username) {
		//update the UI
		clientModel.setSigningIn();
		
		//Timeout if sign in unsuccessful
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if (clientModel.isSigningIn()) {
					clientModel.setErrorMsg(UIConstants.CLIENT_FAILED_CONNECT);
					clientModel.clearErrorMsg();
					clientModel.setSignedOut();
				}
			}
		}, CLIENT_SERVER_TIMEOUT);
		
		//send a register to the server
		sendRegister(username);
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

    public static void sendRegister(String username) {
    	clientModel.setSignedInUser(username);
    	
        ConnectionHandler connSend = new ConnectionHandler("localhost",
                protocol.getServerPort(), false);
		
        Message msg = new CSRegister(new Contact(username, true,
                "localhost", conn.getPort(), new ArrayList<String>()));
        connSend.setObject(msg);
        
        new Thread(connSend).start();
    }
    
    public static void sendStartMessage(int userListIndex) {
    	String user2Talk = clientModel.getUserList().get(userListIndex);
    	
    	ConnectionHandler connSend = new ConnectionHandler("localhost",
                protocol.getServerPort(), false);
		
        Message msg = new CSStartMessage(new Contact(clientModel.getSignedInUser(), 
        		true, "localhost", conn.getPort(), new ArrayList<String>()), user2Talk);
        connSend.setObject(msg);
        
        new Thread(connSend).start();
    }

    public static boolean processRegister(SCRegister msg) {
    	clientModel.setSignedIn();
    	clientModel.setUserList(msg.getUsersList());
        return true;
    }    
    //TODO: change object to the proper message

    public static boolean processStartMessage(Object msg) {
        return true;
    }    
    //TODO: change object to the proper message

    public static boolean processUpdateInfo(Object msg) {
        return true;
    }    
    //TODO: change object to the proper message

    public static boolean processMessage(Object msg) {
        return true;
    }   
}
