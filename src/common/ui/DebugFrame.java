package common.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DebugFrame extends JFrame {

    private static final long serialVersionUID = -3011360747293896589L;
    private static final int DEBUGPANEL_WIDTH = 200;
    private static final int DEBUGPANEL_HEIGHT = 500;
    private JPanel debugPanel;
    private JScrollPane debugConsoleScroll;
    private JTextArea consoleTextArea;

    public DebugFrame(int locX, int locY) {
        super();
        setTitle(UIConstants.DEBUG_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startDebugPanel();
        pack();
        setLocationRelativeTo(null);
        setLocation(locX, locY);
        setVisible(true);
    }

    private void startDebugPanel() {
        //text area for showing the log
        consoleTextArea = new JTextArea(UIConstants.DEBUG_START);
        consoleTextArea.setLineWrap(true);
        consoleTextArea.setWrapStyleWord(true);

        //debugConsole panel scroll
        debugConsoleScroll = new JScrollPane(consoleTextArea);

        //debugConsole panel
        debugPanel = new JPanel();
        debugPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        debugPanel.setLayout(new BorderLayout(5, 5));
        debugPanel.add(new JLabel(UIConstants.DEBUG_TITLE, JLabel.CENTER), BorderLayout.PAGE_START);
        debugPanel.setPreferredSize(new Dimension(DEBUGPANEL_WIDTH, DEBUGPANEL_HEIGHT));
        debugPanel.add(debugConsoleScroll, BorderLayout.CENTER);

        add(debugPanel);
    }

    public void log(String msg) {
        consoleTextArea.append("\n" + msg);
    }
}
