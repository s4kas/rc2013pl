/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author João
 */
public abstract class Message implements Serializable {
    private int code;
    private Date timestamp;

    public Message(int code) {
        this.code = code;
        timestamp = new Date();
    }
    
    
}
