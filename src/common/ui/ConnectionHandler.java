/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Emanuel
 */
public class ConnectionHandler implements Runnable{
    private String host;
    private int port;
    private boolean isServer;
    private Object o;
    
    public ConnectionHandler(String host,int port,boolean isServer){
        this.host = host;
        this.port = port;
        this.isServer = isServer;
    }
    @Override
    public void run() {
        try {
            if (isServer) {
                startServerSocket();
            }
        else {
                startClientSocket();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.print(e.getMessage());
        }                    
    }    
    public void setObject(Object o){
        this.o = o;
    }
    private void startServerSocket() throws UnknownHostException, IOException, ClassNotFoundException{
        // Create server socket.
        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            // Wait for a client connection.
            Socket connectionSocket = serverSocket.accept();
            InputStream is = connectionSocket.getInputStream();  
            ObjectInputStream ois = new ObjectInputStream(is);  
            System.out.println((String)ois.readObject());            
            ois.close();
            is.close();  
            connectionSocket.close();  
        }
    }
    private void startClientSocket() throws UnknownHostException, IOException{
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
