package common.protocol;

import java.io.File;

public class CCMessage extends Message {
	

	private static final long serialVersionUID = -2115471190718143744L;
	private String text;
	private File png;

	public CCMessage (String text){
		super(MessageType.CCMessage);
		this.text = text;
	}
	public CCMessage (File png){
		super(MessageType.CCMessage);
		this.png = png;  
	}
	public String getText() {
        return text;
    }
	public File getImage() {
        return png;
    }
}
