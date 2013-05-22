/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.protocol;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author João
 */
public abstract class Message implements Serializable {

    private static final long serialVersionUID = 7218276744535174659L;
    private MessageType messageType;
    private Date timestamp;

    public Message(MessageType messageType) {
        this.timestamp = new Date();
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    protected void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
