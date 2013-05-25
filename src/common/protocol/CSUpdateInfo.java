package common.protocol;

import common.ConnectionHandler;

public class CSUpdateInfo extends Message {

    private static final long serialVersionUID = 5655903916952837337L;
    private Contact user, requester;

    public CSUpdateInfo(Contact c, ConnectionHandler conn) {
        super(MessageType.CSUpdateInfo, conn);
        user = c;
    }

    public Contact getUser() {
        return user;
    }

    public Contact getRequester() {
        return requester;
    }

    public void setRequester(Contact requester) {
        this.requester = requester;
    }
}
