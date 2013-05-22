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
                Server.processRegister((CSRegister) arg,"",6666 );
                break;
            case CSStartMessage:
                Server.processStartMessage(arg,"",6666);
                break;
            case CSUpdateInfo:
                Server.processUpdateInfo(arg,"",6666);
                break;
            default:
                System.out.println("Nao sei quem es");
                break;
        }
    }
}
