/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import common.ui.DebugFrame;

import client.ui.ClientMainFrame;

/**
 *
 * @author Emanuel
 */
public class Client {
	
	private static ClientMainFrame mainFrame;
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
		mainFrame = new ClientMainFrame();
        
        //Create and set up the Debug window.
        debugFrame = new DebugFrame(mainFrame.getX() + mainFrame.getWidth(), mainFrame.getY());

        mainFrame.toFront();
	}
	
	private static void log(String msg) {
		debugFrame.log("Client: " + msg);
	}
}
