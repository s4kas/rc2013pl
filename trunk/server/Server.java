/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import common.ui.DebugFrame;
import server.ui.ServerMainFrame;

/**
 *
 * @author Emanuel
 */
public class Server {
	
	private static ServerMainFrame mainFrame;
	private static DebugFrame debugFrame;
	
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
        
        //Create and set up the Debug window.
        debugFrame = new DebugFrame(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());

        mainFrame.toFront();
	}
	
	private static void log(String msg) {
		debugFrame.log("Server: " + msg);
	}
}
