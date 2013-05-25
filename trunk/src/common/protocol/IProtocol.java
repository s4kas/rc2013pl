package common.protocol;

import common.ConnectionHandler;
import java.util.Observer;

public abstract class IProtocol implements Observer {

    private int SERVER_PORT = 6666;

    public int getServerPort() {
        return SERVER_PORT;
    }    
    public void sendMessage(Message msg, String host, int port) {
        ConnectionHandler connection = new ConnectionHandler(host, port, false);
        connection.setObject(msg);
        new Thread(connection).start();
    }
}
