package client.ui;

import client.Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientActionListener implements ActionListener {
	
	ClientMainFrame mainFrame;
	
	public ClientActionListener (ClientMainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(mainFrame.getSignInButton())) {
			String username = mainFrame.getSignInUser().getText();
			Client.sendRegister(username);
		}
	}

}
