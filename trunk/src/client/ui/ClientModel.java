package client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ClientModel extends Observable {
	
	private enum Status {SIGNEDIN, SIGNEDOUT, SIGNINGIN};
	
	private String signedInUser;
	private String errorMsg;
	private List<String> userList;
	private Status status;
	
	public ClientModel () {
		this.signedInUser = "";
		this.status = Status.SIGNEDOUT;
		this.errorMsg = "";
		this.userList = new ArrayList<String>();
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
			notifyObservers();
		}
	}
	
	public void setSigningIn() {
		if (!this.status.equals(Status.SIGNINGIN)) {
			this.status = Status.SIGNINGIN;
			setChanged();
			notifyObservers();
		}
	}
	
	public void setSignedOut() {
		if (!this.status.equals(Status.SIGNEDOUT)) {
			this.status = Status.SIGNEDOUT;
			setChanged();
			notifyObservers();
		}
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		if (!this.errorMsg.equals(errorMsg)) {
			this.errorMsg = errorMsg;
			setChanged();
			notifyObservers();
		}
	}
	
	public void clearErrorMsg() {
		this.errorMsg = "";
	}
	
	public boolean hasError() {
		return errorMsg == null || this.errorMsg.length() > 0;
	}
	
	public List<String> getUserList() {
		return userList;
	}
	
	public void setUserList(List<String> userList) {
		//TODO validate the list
		this.userList.addAll(userList);
		setChanged();
		notifyObservers();
	}

	public String getSignedInUser() {
		return signedInUser;
	}

	public void setSignedInUser(String signedInUser) {
		this.signedInUser = signedInUser;
	}
}
