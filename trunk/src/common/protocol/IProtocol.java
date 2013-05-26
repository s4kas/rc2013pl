package common.protocol;

import common.ConnectionHandler;
import common.properties.CommonProps;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observer;

public abstract class IProtocol implements Observer {
	
    private int serverPort;
    private String serverHost;
    private String currentHost;

    public int getServerPort() {
    	if (serverPort <= 0) {
    		serverPort = CommonProps.getServerPort();
    	}
    	return serverPort;
    }    

    public String getServerHost() {
    	if (serverHost == null) {
    		serverHost = CommonProps.getServerHost();
    	}
    	return serverHost;
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
    
    public void sendMessage(Message msg, String host, int port) {
        ConnectionHandler connection = new ConnectionHandler(host, port, false);
        connection.setObject(msg);
        new Thread(connection).start();
    }
}