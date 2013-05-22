package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import common.ui.UIConstants;

public class ClientMainFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -5271727290864197949L;
	private static final int MAINPANEL_WIDTH = 320;
	private static final int MAINPANEL_HEIGHT = 500;
	
	private JPanel mainPanel;
	private JScrollPane mainPanelScroll;
	private JButton signInButton;
	private JTextField signInUser;
	private JLabel signInLabel;
	private JList<JLabel> userList;
	private ClientActionListener clientActionListener;
	
	@Override
	public void update(Observable o, Object arg) {
		ClientModel clientModel = (ClientModel)o;
		
		//update userList
		List<String> clientModelUserList = clientModel.getUserList();
		userList = new JList<JLabel>();
		for (String user : clientModelUserList) {
			userList.add(new JLabel(user));
		}
		
		//update status
		if (clientModel.isSigningIn()) {
			signInUser.setEnabled(false);
			signInButton.setEnabled(false);
			signInLabel.setText(UIConstants.SIGNING_IN_LABEL);
		} else if (clientModel.isSignedIn()) {
			startUserListPanel();
		} else {
			signInUser.setEnabled(true);
			signInButton.setEnabled(true);
			signInLabel.setText(UIConstants.SIGN_IN_LABEL);
		}
	}
	
	public JButton getSignInButton() {
		return signInButton;
	}

	public JTextField getSignInUser() {
		return signInUser;
	}
	
	public ClientMainFrame() {
		//start the action listener
		clientActionListener = new ClientActionListener(this);
		
		setTitle(UIConstants.CLIENT_MAIN_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startMainPanel();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void startMainPanel() {	
		//main panel
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(new Dimension(MAINPANEL_WIDTH, MAINPANEL_HEIGHT));
		mainPanel.add(Box.createVerticalGlue());

		//username label
		signInLabel = new JLabel(UIConstants.SIGN_IN_LABEL, JLabel.CENTER);
		signInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(signInLabel);
		mainPanel.add(Box.createVerticalStrut(10));
		
		//username textfield
		signInUser = new JTextField();
		signInUser.setMaximumSize(new Dimension(MAINPANEL_WIDTH / 2, 20));
		signInUser.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(signInUser);
		mainPanel.add(Box.createVerticalStrut(10));
		
		//sign ing button
		signInButton = new JButton(UIConstants.SIGN_IN_BUTTON);
		signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		signInButton.addActionListener(clientActionListener);
		mainPanel.add(signInButton);
		mainPanel.add(Box.createVerticalGlue());

		add(mainPanel);
	}
	
	private void startUserListPanel() {		
		//main panel scroll
		mainPanelScroll = new JScrollPane(userList);
		
		//main panel
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		mainPanel.setLayout(new BorderLayout(5,5));
		mainPanel.setPreferredSize(new Dimension(MAINPANEL_WIDTH, MAINPANEL_HEIGHT));
		mainPanel.add(new JLabel("User List", JLabel.CENTER), BorderLayout.PAGE_START);
		mainPanel.add(mainPanelScroll, BorderLayout.CENTER);

		add(mainPanel);
	}
	
	private void showError(String errorMsg) {
		JOptionPane.showMessageDialog(this, errorMsg,"An error ocorred" ,JOptionPane.ERROR_MESSAGE);
	}
}
