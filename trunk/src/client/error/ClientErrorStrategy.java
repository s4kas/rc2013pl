package client.error;

import client.Client;
import common.error.IErrorTreatingStrategy;
import common.protocol.CCMessage;
import common.protocol.Message;

public class ClientErrorStrategy implements IErrorTreatingStrategy {

    @Override
    public void dealWithException(Message message, String exceptionType) {
        switch (exceptionType) {
            case "CCMessage":
                CCMessage CCmsg = (CCMessage) message;
                Client.processExceptionCCMessage(CCmsg);
                break;
        }
    }
}
