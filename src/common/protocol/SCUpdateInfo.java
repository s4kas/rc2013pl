package common.protocol;

public class SCUpdateInfo extends Message {

    private static final long serialVersionUID = -6625202764248396981L;
    private String requesterName;

    public SCUpdateInfo() {
        super(MessageType.SCUpdateInfo);
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    
}
