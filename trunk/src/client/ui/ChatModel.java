package client.ui;

import java.util.LinkedList;
import java.util.Observable;

public class ChatModel extends Observable {
	
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
	
	public int getLastAddedIndex() {
		return currentReceivedFile.size() - 1;
	}

	public byte[] getReceivedFile(int i) {
		return currentReceivedFile.get(i);
	}
}
