package server;

import common.protocol.*;
import java.util.Observable;

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
}
