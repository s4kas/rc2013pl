/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import common.ConnectionHandler;
import common.protocol.Protocol;
import common.ui.DebugFrame;
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
	private static Protocol protocol;
	private static ConnectionHandler conn;
	
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
		
		//instantiate a new connection handler
		conn = new ConnectionHandler(null, Protocol.SERVER_PORT, true);
		
		//a protocol to process incoming messages
		protocol = new Protocol();
		conn.addObserver(protocol);
		
		//a thread to listen for incoming messages
        new Thread(conn).start();
	}
	
	public static void stopServer() {
		serverModel.setStarted(false);
	}
	
	private static void log(String msg) {
		if (debugFrame != null) {
			debugFrame.log("Server: " + msg);
		}
	}
}
