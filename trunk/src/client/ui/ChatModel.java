package client.ui;

import java.util.Observable;

public class ChatModel extends Observable {
	
	public void setMessage(String[] message) {
		setChanged();
		notifyObservers(message);
	}
	
	public void setFocus() {
		setChanged();
		notifyObservers();
	}
}
