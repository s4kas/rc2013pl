package server.ui;

import common.ui.ConnectionHandler;
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
                    common.ui.ConnectionHandler conn = new ConnectionHandler(null, 666, true);
                    new Thread(conn).start();
                    System.out.println(mainFrame.getStartButton().getText());
		}
		
	}

}
