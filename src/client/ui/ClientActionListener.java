package client.ui;

import client.Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientActionListener extends MouseAdapter implements ActionListener {

    private ClientMainFrame mainFrame;

    public ClientActionListener(ClientMainFrame mainFrame) {
        super();
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(mainFrame.getSignInButton())) {
            String username = mainFrame.getSignInUser().getText();
            Client.signIn(username);
        } else if (e.getSource().equals(mainFrame.getLogOutButton())) {
            Client.logOut();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getClickCount() == 2) {
            if (e.getSource().equals(mainFrame.getUserJList())) {
                int index = mainFrame.getUserJList().getSelectedIndex();
                if (index >= 0) {
                    Client.startChat(index);
                }
            }
        }
    }
}
