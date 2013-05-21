package server.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import common.ui.UIConstants;

public class ServerMainFrame extends JFrame {

	private static final long serialVersionUID = 6521432258304874142L;
	private static final int MAINPANEL_WIDTH = 200;
	private static final int MAINPANEL_HEIGHT = 200;
	
	private JPanel mainPanel;
	private JButton startButton, stopButton;
	private ServerActionListener serverActionListener;
	
	public JButton getStartButton() {
		return startButton;
	}

	public JButton getStopButton() {
		return stopButton;
	}
	
	public ServerMainFrame() {
		//start the action listener
		serverActionListener = new ServerActionListener(this);
		
		setTitle(UIConstants.SERVER_MAIN_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startMainPanel();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void startMainPanel() {	
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		mainPanel.setPreferredSize(new Dimension(MAINPANEL_WIDTH, MAINPANEL_HEIGHT));
		mainPanel.add(Box.createVerticalGlue());
		
		startButton = new JButton(UIConstants.START_BUTTON);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(serverActionListener);
		mainPanel.add(startButton);
		mainPanel.add(Box.createVerticalGlue());
		
		add(mainPanel);
	}
	
}
