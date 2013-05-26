package server.error;

import server.Server;
import common.error.IErrorTreatingStrategy;
import common.protocol.Message;
import common.protocol.SCUpdateInfo;

public class ServerErrorStrategy implements IErrorTreatingStrategy {

    @Override
    public void dealWithException(Message message, String exceptionType) {
        switch (exceptionType) {
            case "SCUpdateInfo":
                SCUpdateInfo SCmsg = (SCUpdateInfo) message;
                Server.processExceptionSCUpdateInfo(SCmsg);
                break;
        }
    }
}
