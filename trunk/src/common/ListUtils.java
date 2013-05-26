package common;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
	
	public static List<String> getRemovedUsers(List<String> allUsers, 
			List<String> currentUsers) {
        List<String> removedUsers = new ArrayList<String>();
        for (String userFromClient : currentUsers) {
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

    public static List<String> getAddedUsers(List<String> allUsers,
    		List<String> currentUsers) {
        List<String> addedUsers = new ArrayList<String>();
        for (String userFromServer : allUsers) {
            boolean newUser = true;
            for (String userFromClient : currentUsers) {
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
