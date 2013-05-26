package common.protocol;


public class TextCapability implements ICapability {

	private static final long serialVersionUID = 6064477922914122766L;
	private String text;
	
	public TextCapability(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
