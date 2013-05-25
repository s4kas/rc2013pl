package common.protocol;

import common.ConnectionHandler;
import java.io.File;

public class CCMessage extends Message {

    private static final long serialVersionUID = -2115471190718143744L;
    private String text;
    private File png;
    private String senderName;

    public CCMessage(String text, ConnectionHandler conn, String senderName) {
        super(MessageType.CCMessage, conn);
        this.text = text;
        this.senderName = senderName;
    }

    public CCMessage(File png, ConnectionHandler conn, String senderName) {
        super(MessageType.CCMessage, conn);
        this.png = png;
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public File getImage() {
        return png;
    }

    public String getSenderName() {
        return senderName;
    }
}
