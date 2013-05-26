package client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import client.Client;

public class ChatActionListener implements ActionListener, HyperlinkListener {

    private ChatFrame chatFrame;

    public ChatActionListener(ChatFrame chatFrame) {
        this.chatFrame = chatFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(chatFrame.getSendText())) {
            String message = chatFrame.getSendTextArea().getText();
            if (!isBlank(message)) {
                Client.startMessage(chatFrame.getTitle(), message);
            }
        } else if (e.getSource().equals(chatFrame.getSendFoto())) {
            int option = chatFrame.getFileChooser().showOpenDialog(chatFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                Client.startMessage(chatFrame.getTitle(),
                        chatFrame.getFileChooser().getSelectedFile());
            }
        }
    }

    private static boolean isBlank(String s) {
        return (s == null) || (s.trim().length() == 0);
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            int index = Integer.parseInt(e.getDescription());
            int option = chatFrame.getFileChooser().showSaveDialog(chatFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = chatFrame.getFileChooser().getSelectedFile();
                Client.saveFile(chatFrame.getTitle(), index, file);
            }
        }
    }
}
