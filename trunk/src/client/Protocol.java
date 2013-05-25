package client;

import common.protocol.*;
import java.util.Observable;

public class Protocol extends IProtocol {

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
}
