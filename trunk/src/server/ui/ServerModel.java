package server.ui;

import java.util.Observable;

public class ServerModel extends Observable {
	
	private boolean isStarted;

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		if (this.isStarted != isStarted) {
			this.isStarted = isStarted;
			setChanged();
			notifyObservers();
		}
	}
}
