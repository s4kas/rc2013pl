package client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import common.protocol.Contact;

public class ClientModel extends Observable {

    public static enum Status {

        SIGNEDIN, SIGNEDOUT, SIGNINGIN
    };
    public static final String ADDED_USERS = "ADDED_USERS";
    public static final String REMOVED_USERS = "REMOVED_USERS";
    public static final String ALL_USERS = "ALL_USERS";
    public static final String FIRST_TIME = "FIRST_TIME";
    private Contact signedInUser;
    private List<String> userList;
    private Map<String, Contact> userListWithChat;
    private Status status;

    public ClientModel() {
        this.status = Status.SIGNEDOUT;
        this.userList = new ArrayList<String>();
        this.userListWithChat = new ConcurrentHashMap<String, Contact>();
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

    public void setUserList(List<String> userList, 
    		List<String> addedUsers, List<String> removedUsers) {
        Map<String, List<String>> usersListMap = new ConcurrentHashMap<String, List<String>>();
        usersListMap.put(ADDED_USERS, addedUsers);
        usersListMap.put(REMOVED_USERS, removedUsers);
        usersListMap.put(ALL_USERS, userList);
        this.userList = new ArrayList<String>(userList);

        setChanged();
        notifyObservers(usersListMap);
    }
    
    public void removeFromUserList(String user) {
    	userList.remove(user);
    	List<String> removedUsers = new ArrayList<String>();
    	removedUsers.add(user);
    	setUserList(userList, new ArrayList<String>(), removedUsers);
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
}
