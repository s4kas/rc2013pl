package common.protocol;

public class CSUpdateInfo extends Message {

	private static final long serialVersionUID = 5655903916952837337L;
	private Contact user;

	public CSUpdateInfo(Contact c) {
		super(MessageType.CSUpdateInfo);
		user = c;
	}
	public Contact getUser() {
        return user;
    }
}
