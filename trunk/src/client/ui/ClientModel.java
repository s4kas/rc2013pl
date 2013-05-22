package client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ClientModel extends Observable {
	
	private boolean isSignedIn;
	private boolean isSigningIn;
	private String errorMsg;
	private List<String> userList;
	
	public ClientModel () {
		this.isSignedIn = false;
		this.isSigningIn = false;
		this.errorMsg = "";
		this.userList = new ArrayList<String>();
	}
	
	public boolean isSignedIn() {
		return isSignedIn;
	}
	
	public void setSignedIn(boolean isSignedIn) {
		if (this.isSignedIn != isSignedIn) {
			this.isSignedIn = isSignedIn;
			setChanged();
			notifyObservers();
		}
	}
	
	public boolean isSigningIn() {
		return isSigningIn;
	}
	
	public void setSigningIn(boolean isSigningIn) {
		if (this.isSigningIn != isSigningIn) {
			this.isSigningIn = isSigningIn;
			setChanged();
			notifyObservers();
		}
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public List<String> getUserList() {
		return userList;
	}
	public void setUserList(List<String> userList) {
		this.userList = userList;
	}
}
