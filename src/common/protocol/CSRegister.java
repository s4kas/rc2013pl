/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.protocol;

import common.ConnectionHandler;

/**
 *
 * @author João
 */
public class CSRegister extends Message {

    private static final long serialVersionUID = -5785258486527568326L;
    private Contact user;

    public CSRegister(Contact c, ConnectionHandler conn) {
        super(MessageType.CSRegister, conn);
        user = c;
    }

    public Contact getUser() {
        return user;
    }
}
