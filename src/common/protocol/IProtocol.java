package common.protocol;

import common.ConnectionHandler;
import common.ThreadUncaughtExceptionHandler;
import common.properties.CommonProps;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observer;

public abstract class IProtocol implements Observer {

    private int serverPort;
    private String currentHost;
    private ThreadUncaughtExceptionHandler exceptionHandler;

    public int getServerPort() {
    	if (serverPort <= 0) {
    		serverPort = CommonProps.getServerPort();
    	}
    	return serverPort;
    }

    public String getCurrentHost() {
        if (currentHost == null) {
            try {
                currentHost = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return currentHost;
    }

    public ThreadUncaughtExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(ThreadUncaughtExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void sendMessage(Message msg, String host, int port) {
        ConnectionHandler connection = new ConnectionHandler(host, port, false);
        connection.setObject(msg);
        //new Thread(connection).start();
        Thread thread = new Thread(connection);
        thread.setName(msg.getMessageType().name());
        thread.setUncaughtExceptionHandler(exceptionHandler);
        thread.start();       
    }
}
