package server.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerActionListener implements ActionListener {
	
	ServerMainFrame mainFrame;
	
	public ServerActionListener (ServerMainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(mainFrame.getStartButton())) {
			System.out.println(mainFrame.getStartButton().getText());
		}
		
	}

}
