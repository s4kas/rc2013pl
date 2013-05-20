package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

public class ClientMainFrame extends JFrame {

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
		//user list
		userList = new JList<JLabel>();
		//FIXME BM remove this
		userList.add(new JLabel("bruno@pist.com"));
		
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
	
	public void showError(String errorMsg) {
		JOptionPane.showMessageDialog(this, errorMsg,"An error ocorred" ,JOptionPane.ERROR_MESSAGE);
	}
}
