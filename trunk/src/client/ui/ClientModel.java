package client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import common.protocol.Contact;

public class ClientModel extends Observable {
	
	public static enum Status {SIGNEDIN, SIGNEDOUT, SIGNINGIN};
	public static final String ADDED_USERS = "ADDED_USERS";
	public static final String REMOVED_USERS = "REMOVED_USERS";
	public static final String ALL_USERS = "ALL_USERS";
	
	private Contact signedInUser;
	private List<String> userList;
	private Map<String, Contact> userListWithChat;
	private Status status;
	
	public ClientModel () {
		this.status = Status.SIGNEDOUT;
		this.userList = new ArrayList<String>();
		this.userListWithChat = new HashMap<String, Contact>();
	}
	
	public boolean isSignedIn() {
		return status.equals(Status.SIGNEDIN);
	}
	
	public boolean isSigningIn() {
		return this.status.equals(Status.SIGNINGIN);
	}
	
	public boolean isSignedOut() {
		return this.status.equals(Status.SIGNEDOUT);
	}
	
	public void setSignedIn() {
		if (!this.status.equals(Status.SIGNEDIN)) {
			this.status = Status.SIGNEDIN;
			setChanged();
			notifyObservers(Status.SIGNEDIN);
		}
	}
	
	public void setSigningIn() {
		if (!this.status.equals(Status.SIGNINGIN)) {
			this.status = Status.SIGNINGIN;
			setChanged();
			notifyObservers(Status.SIGNINGIN);
		}
	}
	
	public void setSignedOut() {
		if (!this.status.equals(Status.SIGNEDOUT)) {
			this.status = Status.SIGNEDOUT;
			setChanged();
			notifyObservers(Status.SIGNEDOUT);
		}
	}

	public void setErrorMsg(String errorMsg) {
		setChanged();
		notifyObservers(errorMsg);
	}
	
	public List<String> getUserList() {
		return userList;
	}
	
	public void setUserList(List<String> userList) {	
		List<String> addedUsers = getAddedUsers(userList);
		List<String> removedUsers = getRemovedUsers(userList);
		Map<String, List<String>> usersListMap = new HashMap<String, List<String>>();
		usersListMap.put(ADDED_USERS, addedUsers);
		usersListMap.put(REMOVED_USERS, removedUsers);
		usersListMap.put(ALL_USERS, userList);
		this.userList = new ArrayList<String>(userList);
		
		setChanged();
		notifyObservers(usersListMap);
	}
	
	public Contact getUserWithChat(String userName) {
		return userListWithChat.get(userName);
	}

	public void addToUserListWithChat(Contact contact) {
		this.userListWithChat.put(contact.getName(), contact);
	}
	
	public void removeFromUserLisWithChat(String userName) {
		this.userListWithChat.remove(userName);
	}

	public Contact getSignedInUser() {
		return signedInUser;
	}

	public void setSignedInUser(Contact signedInUser) {
		this.signedInUser = signedInUser;
	}
	
	private List<String> getRemovedUsers(List<String> allUsers) {
		List<String> removedUsers = new ArrayList<String>();
		for (String userFromClient : this.userList) {
			boolean removedUser = true;
			for (String userFromServer : allUsers) {
				if (userFromServer.equals(userFromClient)) {
					removedUser = false;
				}
			}
			
			if (removedUser) {
				removedUsers.add(userFromClient);
			}
		}
		return removedUsers;
	}
	
	private List<String> getAddedUsers(List<String> allUsers) {
		List<String> addedUsers = new ArrayList<String>();
		for (String userFromServer : allUsers) {
			boolean newUser = true;
			for (String userFromClient : this.userList) {
				if (userFromServer.equals(userFromClient)) {
					newUser = false;
				}
			}
			
			if (newUser) {
				addedUsers.add(userFromServer);
			}
		}
		return addedUsers;
	}
}
