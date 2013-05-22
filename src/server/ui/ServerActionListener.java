package server.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import common.ui.UIConstants;

import server.Server;

public class ServerActionListener implements ActionListener {
	
	ServerMainFrame mainFrame;
	
	public ServerActionListener (ServerMainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(mainFrame.getStartButton())) {
			if (e.getActionCommand().equals(UIConstants.START_BUTTON)) {
				Server.startServer();
			} else if (e.getActionCommand().equals(UIConstants.STOP_BUTTON)) {
				Server.stopServer();
			}
		}
		
	}

}
