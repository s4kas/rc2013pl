package common.protocol;

import common.ConnectionHandler;

public class SCStartMessage extends Message {

    private static final long serialVersionUID = 5831855164273982988L;
    private Contact user;

    public SCStartMessage(Contact c, ConnectionHandler conn) {
        super(MessageType.SCStartMessage, conn);
        user = c;
    }

    public Contact getUser() {
        return user;
    }
}
