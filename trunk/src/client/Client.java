/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;

import common.ConnectionHandler;
import common.protocol.CSRegister;
import common.protocol.Contact;
import common.protocol.Message;
import common.protocol.Protocol;
import common.ui.DebugFrame;

import client.ui.ClientMainFrame;

/**
 *
 * @author Emanuel
 */
public class Client {
	
	private static ClientMainFrame mainFrame;
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
		
		//start listening
		startConnection();
	}
	
	private static void createAndShowUI() {
		//Create and set up the Main window.
		mainFrame = new ClientMainFrame();
        
        //Create and set up the Debug window.
        //debugFrame = new DebugFrame(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());

        mainFrame.toFront();
	}
	
	private static void startConnection() { 
        common.ConnectionHandler conn = new ConnectionHandler(null, 0, true);
        localPort = conn.getPort();
        
        protocol = new Protocol();
		conn.addObserver(protocol);
        new Thread(conn).start();
	}
	
	public static void sendRegister(String username) {
		ConnectionHandler conn = new ConnectionHandler("localhost", 
				Protocol.SERVER_PORT, false);
        Message msg = new CSRegister(new Contact(username, true,
        		"localhost", localPort, new ArrayList()));
        conn.setObject(msg);
        new Thread(conn).start();
	}
	
	private static void log(String msg) {
		debugFrame.log("Client: " + msg);
	}
}
