package common.protocol;

import common.ConnectionHandler;

public class CSUpdateInfo extends Message {

    private static final long serialVersionUID = 5655903916952837337L;
    private Contact user;
    private String requesterName;

    public CSUpdateInfo(Contact c, ConnectionHandler conn) {
        super(MessageType.CSUpdateInfo, conn);
        user = c;
    }

    public Contact getUser() {
        return user;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
}
