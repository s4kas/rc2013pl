package common.protocol;

import common.ConnectionHandler;
import java.util.Observer;

public abstract class IProtocol implements Observer {

    private final int SERVER_PORT = 6666;
    private final String SERVER_HOST = "localhost";

    public int getServerPort() {
        return SERVER_PORT;
    }    

    public String getServerHost() {
        return SERVER_HOST;
    }
    
    public void sendMessage(Message msg, String host, int port) {
        ConnectionHandler connection = new ConnectionHandler(host, port, false);
        connection.setObject(msg);
        new Thread(connection).start();
    }
}
