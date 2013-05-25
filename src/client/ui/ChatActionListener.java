package client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Client;

public class ChatActionListener implements ActionListener {
	
	private ChatFrame chatFrame;
	
	public ChatActionListener(ChatFrame chatFrame) {
		this.chatFrame = chatFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(chatFrame.getSendText())) {
			String message = chatFrame.getSendTextArea().getText();
			if (!isBlank(message)) {
				Client.startMessage(chatFrame.getTitle(), message);
			}
		}
	}
	
	private static boolean isBlank(String s) {
	    return (s == null) || (s.trim().length() == 0);
	}

}
