package common.protocol;

public class SCStartMessage extends Message {

	private static final long serialVersionUID = 5831855164273982988L;
	private Contact user;

	public SCStartMessage (Contact c){
		super(MessageType.SCStartMessage);
		user = c;
	}
	public Contact getUser() {
        return user;
    }
}
