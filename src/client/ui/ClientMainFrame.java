package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;
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
import net.sf.jcarrierpigeon.Notification;
import net.sf.jcarrierpigeon.NotificationQueue;
import net.sf.jcarrierpigeon.WindowPosition;

import common.ui.UIConstants;

public class ClientMainFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -5271727290864197949L;
	private static final int MAINPANEL_WIDTH = 320;
	private static final int MAINPANEL_HEIGHT = 500;
	
	private JPanel mainPanel;
	private JScrollPane mainPanelScroll;
	private JButton signInButton, logOutButton;
	private JTextField signInUser;
	private JLabel signInLabel, usersSizeLabel;
	private JList<String> userList;
	private DefaultListModel<String> listModel;
	private ClientActionListener clientActionListener;
	private NotificationQueue notifQueue;
	
	public ClientMainFrame() {
		//start the action listener
		clientActionListener = new ClientActionListener(this);
		
		//start the lists
		listModel = new DefaultListModel<String>();
		userList = new JList<String>(listModel);
		userList.addMouseListener(clientActionListener);
		
		//Notifications
		notifQueue = new NotificationQueue();
		
		setTitle(UIConstants.CLIENT_MAIN_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		loadSignInPanel();
		add(mainPanel);
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
	
	public JButton getLogOutButton() {
		return logOutButton;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof Map) { //update userList
			
			//Update list of users
			@SuppressWarnings("unchecked")
			Map<String, List<String>> usersMap = (Map<String, List<String>>)arg;
			List<String> allUsers = usersMap.get(ClientModel.ALL_USERS);
			updateListOfUsers(allUsers);
			
			List<String> addedUsers = usersMap.get(ClientModel.ADDED_USERS);
			List<String> removedUsers = usersMap.get(ClientModel.REMOVED_USERS);
			if (((ClientModel)o).isSignedIn()) {
				showNotification(addedUsers, removedUsers);
			}

		} else if (arg instanceof ClientModel.Status) { //update status
			
			ClientModel.Status status = (ClientModel.Status)arg;
			updateClientStatus(status, ((ClientModel)o).getSignedInUser().getName());
			
		} else if (arg instanceof String) { //update error msg
			
			String errorMsg = (String)arg;
			showError(errorMsg);
			
		}
		
	}
	
	private void loadSignInPanel() {	
		//main panel
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
	}
	
	private void loadUserListPanel(String userName) {
		mainPanel.removeAll();
		mainPanel.add(Box.createVerticalStrut(10));
		JLabel loggedUserLabel = new JLabel("Logged user: " + userName);
		loggedUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(loggedUserLabel);
		mainPanel.add(Box.createVerticalStrut(20));
		
		//main panel scroll
		JLabel userListLabel = new JLabel("User List");
		userListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(userListLabel);
		mainPanelScroll = new JScrollPane(userList);
		
		int onlineContacts = userList.getModel().getSize();
		usersSizeLabel = new JLabel(onlineContacts + UIConstants.CONTACTS_ONLINE, 
				JLabel.CENTER);
		usersSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(mainPanelScroll);
		mainPanel.add(usersSizeLabel);
		mainPanel.add(Box.createVerticalStrut(20));
		
		logOutButton = new JButton("Log Out");
		logOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		logOutButton.addActionListener(clientActionListener);
		mainPanel.add(logOutButton);
		mainPanel.add(Box.createVerticalStrut(10));
	}
	
	private void updateListOfUsers(List<String> allUsers) {
		listModel.removeAllElements();
		for (String user : allUsers) {
			listModel.addElement(user);
		}
		
		if (usersSizeLabel != null) {
			int onlineContacts = userList.getModel().getSize();
			usersSizeLabel.setText(onlineContacts + UIConstants.CONTACTS_ONLINE);
		}
	}
	
	private void updateClientStatus(ClientModel.Status status, String userName) {
		
		switch(status) {
			case SIGNINGIN:
				signInUser.setEnabled(false);
				signInButton.setEnabled(false);
				signInLabel.setText(UIConstants.SIGNING_IN_LABEL);
				break;
			case SIGNEDIN:
				mainPanel.removeAll();
				loadUserListPanel(userName);
				mainPanel.validate();
				mainPanel.repaint();
				break;
			case SIGNEDOUT:
				mainPanel.removeAll();
				loadSignInPanel();
				mainPanel.validate();
				mainPanel.repaint();
				break;
		}
	}
	
	private void showNotification(List<String> addedUsers, List<String> removedUsers) {
		//Added Users notification
		if (!addedUsers.isEmpty()) {	
			for (String addedUser : addedUsers) {
				Notification note = new Notification(getNotificationWindow(UIConstants.USERS_IN, 
						addedUser + " is signing in."), 
						WindowPosition.BOTTOMRIGHT, 100, 75, 3000);
				notifQueue.add(note);
			}
		}
		
		//Removed Users notification
		if (!removedUsers.isEmpty()) {
			for (String removedUser : removedUsers) {
				Notification note = new Notification(getNotificationWindow(UIConstants.USERS_OUT, 
						removedUser + " as signed out."), 
						WindowPosition.BOTTOMRIGHT, 100, 75, 3000);
				notifQueue.add(note);
			}
		}
	}
	
	private JFrame getNotificationWindow(String title, String message) {
		JFrame jw = new JFrame(title);
		jw.setLayout(new BorderLayout());
		jw.add(new JLabel(message), BorderLayout.CENTER);
		jw.setSize(75,75);
		
		return jw;
	}
	
	private void showError(String errorMsg) {
		JOptionPane.showMessageDialog(this, errorMsg,"An error ocorred" ,JOptionPane.ERROR_MESSAGE);
	}
}
