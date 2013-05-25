package common.protocol;

import common.ConnectionHandler;

public class CSStartMessage extends Message {

    private static final long serialVersionUID = 677768784712348566L;
    private Contact user;
    private String user2Talk;

    public CSStartMessage(Contact c, String s, ConnectionHandler conn) {
        super(MessageType.CSStartMessage, conn);
        user = c;
        user2Talk = s;
    }

    public Contact getUser() {
        return user;
    }

    public String getUser2Talk() {
        return user2Talk;
    }
}
