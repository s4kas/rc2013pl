/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.protocol;

import java.util.List;

/**
 *
 * @author João
 */
public class SCRegister extends Message{

	private static final long serialVersionUID = -5329869028768478742L;
	private List<String> usersList; 
    
    public SCRegister(List<String> usersList){
    	super(MessageType.SCRegister);
    	this.usersList = usersList;
    }

    public List<String> getUsersList() {
        return usersList;
    }
}
