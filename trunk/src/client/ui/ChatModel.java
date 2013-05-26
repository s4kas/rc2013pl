package client.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class ChatModel extends Observable {

    public static final String CCMESSAGE_ERROR = "CCMessageError";
    public static final String CSSTART_ERROR = "CSStartError";
    private LinkedList<byte[]> currentReceivedFile;

    public ChatModel() {
        this.currentReceivedFile = new LinkedList<byte[]>();
    }

    public void addContent(Object[] message) {
        if (message[1] instanceof byte[]) {
            currentReceivedFile.addLast((byte[]) message[1]);
        }
        setChanged();
        notifyObservers(message);
    }

    public void setFocus() {
        setChanged();
        notifyObservers();
    }

    public void setCCErrorMsg() {
        setChanged();
        notifyObservers(CCMESSAGE_ERROR);
    }

    public void setCSStartErrorMsg() {
        setChanged();
        notifyObservers(CSSTART_ERROR);
    }

    public int getLastAddedIndex() {
        return currentReceivedFile.size() - 1;
    }

    public byte[] getReceivedFile(int i) {
        return currentReceivedFile.get(i);
    }

    public void updateCapabilitys(List<String> capacity) {
        setChanged();
        notifyObservers(capacity);
    }

    public void close() {
        setChanged();
        notifyObservers();
    }
}
