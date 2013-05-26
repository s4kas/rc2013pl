package client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import common.io.FileUtils;
import common.protocol.ICapability;
import common.ui.UIConstants;

public class ChatFrame extends JFrame implements Observer {

    private static final long serialVersionUID = -384762931445481245L;
    private static final int MAINPANEL_WIDTH = 375;
    private static final int MAINPANEL_HEIGHT = 375;
    private static final int CHAT_AREA_WIDTH = 355;
    private static final int CHAT_AREA_HEIGHT = 305;
    private static final int FOOTER_WIDTH = 300;
    private static final int FOOTER_HEIGHT = 75;
    private JPanel mainPanel, footerPanel;
    private JScrollPane mainPanelScroll, sendTextScroll;
    private JTextPane chatTextPane;
    private JTextArea sendTextArea;
    private JButton sendFoto, sendText;
    private JFileChooser fileChooser;
    private HTMLEditorKit kit;
    private HTMLDocument doc;
    private ChatActionListener chatActionListener;
    
    public JButton getSendText() {
        return sendText;
    }
    
    public JButton getSendFoto() {
        return sendFoto;
    }

	public JTextArea getSendTextArea() {
		return sendTextArea;
	}
	
	public JFileChooser getFileChooser() {
		return fileChooser;
	}
	
	public ChatFrame(String user) {
		//start the action listener
		chatActionListener = new ChatActionListener(this);
		//start the filechooser
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "PNG Images", "png");
		fileChooser.setFileFilter(filter);
		
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
		if (arg instanceof Object[]) {
			
			Object[] message = (Object[])arg;
			try {
				if (message[1] instanceof String) {
					updateChatTextContents(message);
				} else if (message[1] instanceof byte[]) {
					updateChatImageContents(message, ((ChatModel)o).getLastAddedIndex());
				}
			} catch (BadLocationException | IOException e) {}
				
		} else if (arg instanceof List) {
			
			@SuppressWarnings("unchecked")
			List<String> capabilitys = (List<String>)arg;
			updateCapabilitys(capabilitys);
			
		} else if (arg instanceof String) {
			
			String msg = String.valueOf(arg);
			try {
				showMsg(msg);
			} catch (BadLocationException | IOException e) {}
			
		}
		
		setVisible(true);
		toFront();
		
		if (arg instanceof Boolean) {
			
			Boolean close = (Boolean)arg;
			if (close) {
				pullThePlug();
			}
		}
	}
	
	private void pullThePlug() {
		WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
	
	private void showMsg(String msg) throws BadLocationException, IOException {
		String partMsg = "";
		String fontColor = "red";
		if (msg.equals(ChatModel.CCMESSAGE_ERROR)) {
			partMsg = UIConstants.CCMESSAGE_ERROR;
		} else if (msg.equals(ChatModel.CSSTART_ERROR)) {
			partMsg = UIConstants.USER_OFFLINE;
		} else if (msg.equals(ChatModel.LOGIN_MSG)) {
			partMsg = UIConstants.USER_ONLINE;
			fontColor = "green";
		} else if (msg.equals(ChatModel.LOGOUT_MSG)) {
			partMsg = UIConstants.USER_OFFLINE;
		}
		kit.insertHTML(doc, doc.getLength(),"<font color=\""+
			fontColor+"\">"+ partMsg +
			"</font>", 0, 0, null);
	}
	
	private void updateCapabilitys(List<String> capabilitys) {
		boolean sendFotoInd = false;
		for (String cap : capabilitys) {
			if (cap.equals(ICapability.PNG)) {
				sendFotoInd = true;
			}
		}
		if (sendFotoInd) {
			sendFoto.setEnabled(true);
		} else {
			sendFoto.setEnabled(false);
		}
	}
	
	private void updateChatTextContents(Object[] message) throws BadLocationException, IOException {
		String text = "<b>"+message[0]+": </b>" + 
			String.valueOf(message[1]) + "</br>";
		kit.insertHTML(doc, doc.getLength(),text , 0, 0, null);
		sendTextArea.setText(null);
		sendTextArea.setCaretPosition(0);
	}
	
	private void updateChatImageContents(Object[] message, int fileIndex) throws BadLocationException, IOException {
		String htmlImage = "";
		if (message[0].equals(getTitle())) {
			htmlImage = "<a href=\"" +
					fileIndex + "\"><img src=\""+
					FileUtils.getDefaultIconURL().toString()
					+"\"/></a>";
		} else {
			htmlImage = "<img src=\""+
					FileUtils.getDefaultIconURL().toString()
					+"\"/>";
		}
		String image = "<b>"+message[0]+": </b>" + 
				htmlImage + "</br>";
		
		kit.insertHTML(doc, doc.getLength(),image , 0, 0, null);
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
		kit = new HTMLEditorKit();
	    doc = new HTMLDocument();
	    chatTextPane.setEditorKit(kit);
	    chatTextPane.setDocument(doc);
	    chatTextPane.addHyperlinkListener(chatActionListener);
		
		//main panel scroll
		mainPanelScroll = new JScrollPane(chatTextPane);
		mainPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mainPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanelScroll.setPreferredSize(new Dimension(CHAT_AREA_WIDTH, CHAT_AREA_HEIGHT));
		
		mainPanel.add(mainPanelScroll);
	}
	
	private void loadFooter() {
		footerPanel = new JPanel();
		footerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.X_AXIS));
		footerPanel.setPreferredSize(new Dimension(FOOTER_WIDTH, FOOTER_HEIGHT));
		footerPanel.add(Box.createHorizontalStrut(10));
		
		sendTextArea = new JTextArea();
		sendTextArea.setEditable(true);
		sendTextArea.setLineWrap(true);
		sendTextArea.setPreferredSize(new Dimension(FOOTER_WIDTH, (FOOTER_HEIGHT - 10)));
		sendTextScroll = new JScrollPane(sendTextArea);
		sendTextScroll.setMinimumSize(new Dimension((FOOTER_WIDTH / 2), FOOTER_HEIGHT));
		footerPanel.add(sendTextScroll);
		footerPanel.add(Box.createHorizontalStrut(10));
		
		sendText = new JButton(UIConstants.CHAT_SEND_TEXT);
		sendText.setMinimumSize(new Dimension((FOOTER_WIDTH / 4), FOOTER_HEIGHT));
		sendText.addActionListener(chatActionListener);
		footerPanel.add(sendText);
		footerPanel.add(Box.createHorizontalStrut(10));
		sendFoto = new JButton(UIConstants.CHAT_SEND_FOTO);
		sendFoto.setMinimumSize(new Dimension((FOOTER_WIDTH / 4), FOOTER_HEIGHT));
		sendFoto.addActionListener(chatActionListener);
		sendFoto.setEnabled(false);
		footerPanel.add(sendFoto);
		footerPanel.add(Box.createHorizontalStrut(10));
		
		mainPanel.add(footerPanel);
	}
}
