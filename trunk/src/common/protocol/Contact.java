/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author João
 */
public class Contact implements Serializable {

    private static final long serialVersionUID = -2918689307149980393L;
    private String name;
    private boolean isConnected;
    private String host;
    private int port;
    private List<String> capacity;
    
    public Contact(Contact c) {
    	this.name = c.name;
    	this.isConnected = c.isConnected;
    	this.host = c.host;
    	this.port = c.port;
    	this.capacity = new ArrayList<String>(c.getCapacity());
    }
    
    public Contact(String name, boolean isConnected,
            String host, int port, List<String> capacity) {
        this.name = name;
        this.isConnected = isConnected;
        this.host = host;
        this.port = port;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<String> getCapacity() {
        return capacity;
    }

    public void setCapacity(List<String> capacity) {
        this.capacity = capacity;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
