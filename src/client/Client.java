/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import common.ConnectionHandler;
import common.protocol.CSRegister;
import common.protocol.Contact;
import common.protocol.Message;
import common.protocol.Protocol;
import common.ui.DebugFrame;

import client.ui.ClientMainFrame;
import client.ui.ClientModel;

/**
 *
 * @author Emanuel
 */
public class Client {
	
	private static final int CLIENT_SERVER_TIMEOUT = 10000;
	
	private static ClientMainFrame mainFrame;
	private static ClientModel clientModel;
	private static DebugFrame debugFrame;
	private static Protocol protocol;
	private static int localPort;

	public static void main (String[] args) {          
		//start the UI
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
            }
        });
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
		clientModel.setSigningIn(true);
		
		//Timeout if sign in unsuccessful
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if (clientModel.isSigningIn() && !clientModel.isSignedIn()) {
					clientModel.setSigningIn(false);
				}
			}
		}, CLIENT_SERVER_TIMEOUT);
		
		//send a register to the server
		sendRegister(username);
	}
	
	private static void startConnection() {
		//Instantiate a new connection
        ConnectionHandler conn = new ConnectionHandler(null, 0, true);
        localPort = conn.getPort();
        
        //Protocol is listening to process incoming messages
        protocol = new Protocol();
		conn.addObserver(protocol);
		
		//Start a thread to listen for connections
        new Thread(conn).start();
	}
	
	private static void sendRegister(String username) {
		ConnectionHandler conn = new ConnectionHandler("localhost", 
				Protocol.SERVER_PORT, false);
		
        Message msg = new CSRegister(new Contact(username, true,
        		"localhost", localPort, new ArrayList<String>()));
        
        conn.setObject(msg);
        
        new Thread(conn).start();
	}
	
	private static void log(String msg) {
		if (debugFrame != null) {
			debugFrame.log("Client: " + msg);
		}
	}
}
