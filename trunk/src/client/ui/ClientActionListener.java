package client.ui;

import common.ui.ConnectionHandler;
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
                    ConnectionHandler conn = new ConnectionHandler("localhost", 666, false);
                    conn.setObject(username);
                    new Thread(conn).start();
                    System.out.println(username);
		}
	}

}
