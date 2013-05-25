package client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import common.ui.UIConstants;

public class ChatFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -384762931445481245L;
	private static final int MAINPANEL_WIDTH = 350;
	private static final int MAINPANEL_HEIGHT = 350;
	private static final int CHAT_AREA_WIDTH = 330;
	private static final int CHAT_AREA_HEIGHT = 280;
	private static final int FOOTER_WIDTH = 330;
	private static final int FOOTER_HEIGHT = 50;
	
	private JPanel mainPanel, footerPanel;
	private JScrollPane mainPanelScroll;
	private JTextPane chatTextPane;
	private JTextArea sendTextArea;
	private JButton sendFoto, sendText;
	private ChatActionListener chatActionListener;
	private Style def = StyleContext.getDefaultStyleContext().
			getStyle(StyleContext.DEFAULT_STYLE);
	
	public JButton getSendFoto() {
		return sendFoto;
	}

	public JButton getSendText() {
		return sendText;
	}

	public JTextArea getSendTextArea() {
		return sendTextArea;
	}
	
	public ChatFrame(String user) {
		//start the action listener
		chatActionListener = new ChatActionListener(this);
		
		setTitle(user);
		loadMainPanel();
		loadChatArea();
		loadFooter();
		pack();
		sendTextArea.requestFocus();
		setLocationRelativeTo(null);
		setVisible(true);
		toFront();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String[]) { //log message to chatArea
			
			String[] message = (String[])arg;
			StyledDocument doc = chatTextPane.getStyledDocument();
			try {
				Style style = doc.addStyle("bold", def);
				StyleConstants.setBold(style, true);
				doc.insertString(doc.getLength(), message[0] + ": ", 
						doc.getStyle("bold"));

				doc.insertString(doc.getLength(), message[1] + "\n", def);
			} catch (BadLocationException e) {}
			sendTextArea.setText(null);
			sendTextArea.setCaretPosition(0);
			
		} else if (arg == null) { //send the chat window to front
			toFront();
		}
	}

	private void loadMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(new Dimension(MAINPANEL_WIDTH, MAINPANEL_HEIGHT));
		
		add(mainPanel);
	}
	
	private void loadChatArea() {
		//chat contents
		chatTextPane = new JTextPane();
		chatTextPane.setEditable(false);
		
		//main panel scroll
		mainPanelScroll = new JScrollPane(chatTextPane);
		mainPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mainPanelScroll.setPreferredSize(new Dimension(CHAT_AREA_WIDTH, CHAT_AREA_HEIGHT));
		
		mainPanel.add(mainPanelScroll);
	}
	
	private void loadFooter() {
		footerPanel = new JPanel();
		footerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.X_AXIS));
		footerPanel.setPreferredSize(new Dimension(FOOTER_WIDTH, FOOTER_HEIGHT));
		
		sendTextArea = new JTextArea();
		sendTextArea.setEditable(true);
		footerPanel.add(sendTextArea);
		
		sendText = new JButton(UIConstants.CHAT_SEND_TEXT);
		sendText.setMaximumSize(new Dimension(FOOTER_WIDTH /3, FOOTER_HEIGHT));
		sendText.addActionListener(chatActionListener);
		footerPanel.add(sendText);
		sendFoto = new JButton(UIConstants.CHAT_SEND_FOTO);
		sendFoto.setMaximumSize(new Dimension(FOOTER_WIDTH /3, FOOTER_HEIGHT));
		sendFoto.addActionListener(chatActionListener);
		footerPanel.add(sendFoto);
		
		mainPanel.add(footerPanel);
	}
}
