package common.protocol;

import common.ConnectionHandler;

public class SCUpdateInfo extends Message {

    private static final long serialVersionUID = -6625202764248396981L;
    private Contact requester;

    public SCUpdateInfo(ConnectionHandler conn) {
        super(MessageType.SCUpdateInfo, conn);
    }

    public Contact getRequester() {
        return requester;
    }

    public void setRequester(Contact requester) {
        this.requester = requester;
    }
}
