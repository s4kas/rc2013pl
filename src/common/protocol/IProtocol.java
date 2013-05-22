package common.protocol;

import java.util.Observer;

public abstract class IProtocol implements Observer {

    private int SERVER_PORT = 6666;

    public int getServerPort() {
        return SERVER_PORT;
    }
}
