package server;

import common.error.ThreadUncaughtExceptionHandler;
import common.protocol.*;

import java.util.Observable;

import server.error.ServerErrorStrategy;

public class Protocol extends IProtocol {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("obs " + arg);
        Message msg = (Message) arg;
        switch (msg.getMessageType()) {
            case CSRegister:
                Server.processRegister((CSRegister) arg);
                break;
            case CSStartMessage:
                Server.processStartMessage((CSStartMessage)arg);
                break;
            case CSUpdateInfo:
                Server.processUpdateInfo((CSUpdateInfo)arg);
                break;
            default:
                System.out.println("Nao sei quem es");
                break;
        }
    }
    
    @Override
	public ThreadUncaughtExceptionHandler getExceptionHandlerInstance() {
		ThreadUncaughtExceptionHandler exceptionHandler = new ThreadUncaughtExceptionHandler();
		exceptionHandler.setCurrentStrategy(new ServerErrorStrategy());
		return exceptionHandler;
	}

	@Override
	public ThreadUncaughtExceptionHandler getExceptionHandlerInstance(Message msg) {
		ThreadUncaughtExceptionHandler exceptionHandler = new ThreadUncaughtExceptionHandler(msg);
		exceptionHandler.setCurrentStrategy(new ServerErrorStrategy());
		return exceptionHandler;
	}
}
