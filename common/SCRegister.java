/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author João
 */
public class SCRegister extends Message{
    private Contact [] usersList; 
    private String state;
    
    public SCRegister(int norder, Contact [] c, String state){
            super(norder);
            usersList = c;
            this.state = state;
    }

    public Contact[] getUsersList() {
        return usersList;
    }

    public String getState() {
        return state;
    }
    
}
