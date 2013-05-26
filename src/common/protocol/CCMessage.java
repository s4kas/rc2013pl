package common.protocol;

import common.ConnectionHandler;

public class CCMessage extends Message {

    private static final long serialVersionUID = -2115471190718143744L;
    private String senderName;
    private String destinationName;
    private ICapability capabilityContent;

    public CCMessage(ICapability capabilityContent,
            ConnectionHandler conn, String senderName, String destinationName) {
        super(MessageType.CCMessage, conn);
        this.destinationName = destinationName;
        this.senderName = senderName;
        this.capabilityContent = capabilityContent;
    }

    public String getSenderName() {
        return senderName;
    }

    public ICapability getCapabilityContent() {
        return capabilityContent;
    }

    public String getDestinationName() {
        return destinationName;
    }
}
