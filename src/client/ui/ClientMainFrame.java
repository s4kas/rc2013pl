package client.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
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
	private JLabel signInLabel, usersSizeLabel;
	private JList<String> userList;
	private DefaultListModel<String> listModel;
	private ClientActionListener clientActionListener;
	
	public ClientMainFrame() {
		//start the action listener
		clientActionListener = new ClientActionListener(this);
		
		//start the lists
		listModel = new DefaultListModel<String>();
		userList = new JList<String>(listModel);
		userList.addMouseListener(clientActionListener);
		
		setTitle(UIConstants.CLIENT_MAIN_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loadSignInPanel();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public JButton getSignInButton() {
		return signInButton;
	}

	public JTextField getSignInUser() {
		return signInUser;
	}
	
	public JList<String> getUserJList() {
		return userList;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof List) { //update userList
			
			List<String> clientModelUserList = (List<String>)arg;
			listModel.removeAllElements();
			for (String user : clientModelUserList) {
				listModel.addElement(user);
			}
			userList.setModel(listModel);
			
		} else if (arg instanceof ClientModel.Status) { //update status
			
			ClientModel.Status status = (ClientModel.Status)arg;
			switch(status) {
				case SIGNINGIN:
					signInUser.setEnabled(false);
					signInButton.setEnabled(false);
					signInLabel.setText(UIConstants.SIGNING_IN_LABEL);
					break;
				case SIGNEDIN:
					mainPanel.removeAll();
					loadUserListPanel();
					mainPanel.validate();
					mainPanel.repaint();
					break;
				case SIGNEDOUT:
					signInUser.setEnabled(true);
					signInButton.setEnabled(true);
					signInLabel.setText(UIConstants.SIGN_IN_LABEL);
					break;
			}
			
		} else if (arg instanceof String) { //update error msg
			String errorMsg = (String)arg;
			showError(errorMsg);
		}
	}
	
	private void loadSignInPanel() {	
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
	
	private void loadUserListPanel() {
		mainPanel.removeAll();
		
		//main panel scroll
		mainPanelScroll = new JScrollPane(userList);
		
		int onlineContacts = userList.getModel().getSize();
		usersSizeLabel = new JLabel(onlineContacts + UIConstants.CONTACTS_ONLINE, 
				JLabel.CENTER);
		mainPanel.add(mainPanelScroll);
		mainPanel.add(usersSizeLabel);
	}
	
	private void showError(String errorMsg) {
		JOptionPane.showMessageDialog(this, errorMsg,"An error ocorred" ,JOptionPane.ERROR_MESSAGE);
	}
}
