/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author João
 */
public class CSRegister extends Message {
    private Contact user;
    
    public CSRegister(int norder, Contact c){
            super(norder);
            user = c;
    }

    public Contact getUser() {
        return user;
    }
    
}
