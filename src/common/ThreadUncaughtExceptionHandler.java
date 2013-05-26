/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import common.protocol.CCMessage;
import common.protocol.CSUpdateInfo;
import common.protocol.Contact;
import common.protocol.Message;
import common.protocol.SCUpdateInfo;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 *
 * @author Emanuel
 */
public class ThreadUncaughtExceptionHandler implements UncaughtExceptionHandler {

    private Message message;

    public ThreadUncaughtExceptionHandler() {
    }

    public ThreadUncaughtExceptionHandler(Message msg) {
        this.message = msg;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Thread: " + t.getName() + " | Erro: " + e.getMessage());
        solveException(t.getName());
    }

    private void solveException(String type) {
        switch (type) {
            case "CCMessage":
                CCMessage CCmsg = (CCMessage) message;
                client.Client.sendStartMessage(CCmsg.getDestinationName());
                break;
            case "SCUpdadeInfo":
                SCUpdateInfo SCmsg = (SCUpdateInfo) message;
                CSUpdateInfo response = new CSUpdateInfo(null, server.Server.getConn());
                response.setRequester(SCmsg.getRequester());
                server.Server.processUpdateInfo(response);
                break;
        }
    }
}
