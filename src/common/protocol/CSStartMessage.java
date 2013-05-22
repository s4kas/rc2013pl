package common.protocol;

public class CSStartMessage extends Message {

	private static final long serialVersionUID = 1L;
	 private Contact user;
	 private String user2Talk;


	public CSStartMessage(Contact c, String s) {
		super(MessageType.CSStartMessage);
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
