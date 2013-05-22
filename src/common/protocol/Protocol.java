package common.protocol;

import java.util.Observable;
import java.util.Observer;

public class Protocol implements Observer {
	
	public static final int SERVER_PORT = 6666;
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("obs " + arg);
		Message msg = (Message)arg;
		
		switch (msg.getMessageType()) {
		case CSRegister:
                    server.Server.processRegister((CSRegister)arg);
			break;
		case CSStartMessage:
			break;
		case CSUpdateInfo:
			break;
		case SCRegister:
			break;
		case SCStartMessage:
			break;
		case SCUpdateInfo:
			break;
		case CCMessage:
			break;
		default:
			System.out.println("Nao sei quem es");
			break;
		}
	}
}
