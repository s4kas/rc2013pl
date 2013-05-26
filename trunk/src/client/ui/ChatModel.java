package client.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class ChatModel extends Observable {
	
	public static final String CCMESSAGE_ERROR = "CCMessageError";
	public static final String CSSTART_ERROR = "CSStartError";
	public static final String LOGIN_MSG = "LoginMsg";
	public static final String LOGOUT_MSG = "LogoutMsg";
	public static final Boolean CLOSE_CHAT = Boolean.TRUE;
	
	private LinkedList<byte[]> currentReceivedFile;
	
	public ChatModel() {
		this.currentReceivedFile = new LinkedList<byte[]>();
	}
	
	public void addContent(Object[] message) {
		if (message[1] instanceof byte[]) {
			currentReceivedFile.addLast((byte[])message[1]);
		}
		setChanged();
		notifyObservers(message);
	}
	
	public void setFocus() {
		setChanged();
		notifyObservers();
	}
	
	public void setCCErrorMsg() {
		setChanged();
		notifyObservers(CCMESSAGE_ERROR);
	}
	
	public void setCSStartErrorMsg() {
		setChanged();
		notifyObservers(CSSTART_ERROR);
	}
	
	public void setLoginMsg() {
		setChanged();
		notifyObservers(LOGIN_MSG);
	}
	
	public void setLogoutMsg() {
		setChanged();
		notifyObservers(LOGOUT_MSG);
	}
	
	public int getLastAddedIndex() {
		return currentReceivedFile.size() - 1;
	}
	
	public byte[] getReceivedFile(int fileIndex) {
		return currentReceivedFile.get(fileIndex);
	}

	public void updateCapabilitys(List<String> capacity) {
		setChanged();
		notifyObservers(capacity);
	}
	
	public void close() {
		setChanged();
		notifyObservers(CLOSE_CHAT);
	}
}
