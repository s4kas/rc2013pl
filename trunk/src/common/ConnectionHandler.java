/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

/**
 *
 * @author Emanuel
 */
public class ConnectionHandler extends Observable implements Runnable {

    private String host;
    private int port;
    private boolean isServer;
    private Object o;
    ServerSocket serverSocket;

    public ConnectionHandler(String host, int port, boolean isServer) {
        this.host = host;
        this.port = port;
        this.isServer = isServer;
    }

    @Override
    public void run() {
       try {
            if (isServer) {
                startServerSocket();
            } else {
                startClientSocket();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setObject(Object o) {
        this.o = o;
    }

    public int getPort() {
        return this.port;
    }

    public void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startServerSocket() throws UnknownHostException, IOException, ClassNotFoundException {
        // Create server socket.
        serverSocket = new ServerSocket(port);
        port = serverSocket.getLocalPort();
        while (true) {
            // Wait for a client connection.
            Socket connectionSocket = serverSocket.accept();
            InputStream is = connectionSocket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            //notify the protocol
            setChanged();
            notifyObservers(ois.readObject());

            //close this connection
            ois.close();
            is.close();
            connectionSocket.close();
        }
    }

    public String getHost() {
        return host;
    }

    private void startClientSocket() throws UnknownHostException, IOException {
        // Create socket connected to output stream.
        Socket socket = new Socket(host, port);
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(o);
        oos.close();
        os.close();
        socket.close();
    }
}
