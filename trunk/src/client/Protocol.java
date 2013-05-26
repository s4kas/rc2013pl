package client;

import client.error.ClientErrorStrategy;
import client.properties.ClientProps;
import common.error.ThreadUncaughtExceptionHandler;
import common.protocol.*;
import java.util.List;
import java.util.Observable;

public class Protocol extends IProtocol {

    private String serverHost;
    private List<String> capabilitys;

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("obs " + arg);
        Message msg = (Message) arg;
        switch (msg.getMessageType()) {
            case SCRegister:
                Client.processRegister((SCRegister) arg);
                break;
            case SCStartMessage:
                Client.processStartMessage((SCStartMessage) arg);
                break;
            case SCUpdateInfo:
                Client.processUpdateInfo((SCUpdateInfo) arg);
                break;
            case CCMessage:
                Client.processMessage((CCMessage) arg);
                break;
            default:
                System.out.println("Nao sei quem es");
                break;
        }
    }

    public String getServerHost() {
        if (serverHost == null) {
            serverHost = ClientProps.getServerHost();
        }
        return serverHost;
    }

    public List<String> getClientCapabilitys() {
        if (capabilitys == null) {
            capabilitys = ClientProps.getCapabilitys();
        }
        return capabilitys;
    }

    @Override
    public ThreadUncaughtExceptionHandler getExceptionHandlerInstance() {
        ThreadUncaughtExceptionHandler exceptionHandler = new ThreadUncaughtExceptionHandler();
        exceptionHandler.setCurrentStrategy(new ClientErrorStrategy());
        return exceptionHandler;
    }

    @Override
    public ThreadUncaughtExceptionHandler getExceptionHandlerInstance(Message msg) {
        ThreadUncaughtExceptionHandler exceptionHandler = new ThreadUncaughtExceptionHandler(msg);
        exceptionHandler.setCurrentStrategy(new ClientErrorStrategy());
        return exceptionHandler;
    }
}
