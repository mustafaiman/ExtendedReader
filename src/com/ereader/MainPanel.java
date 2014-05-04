package com.ereader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import com.ereader.controller.ConnectionController;
import com.ereader.controller.FileHandler;

import java.io.File;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	JButton serverButton,clientButton;
	JTextField passwordServer;
	JTextField portServer;
	JTextField ip;
	JTextField portClient;
	JTextField passwordClient;
	JFileChooser fc;
	JButton openDialog;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JLabel lblEditAShared;
	private JLabel lblShareAText;
	private JPanel panel_7;
	private JPanel panel_8;
	private JTextField textField;
	private JPanel panel_9;
	private JLabel lblIncorrect;
    public MainPanel() {
    	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    	
    	
    	panel_7 = new JPanel();
    	add(panel_7);
    	
    	panel = new JPanel();
    	panel_7.add(panel);
    	serverButton = new JButton("Share a text file");
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	
    	lblShareAText = new JLabel("Share a Text File");
    	panel.add(lblShareAText);
    	
    	panel_1 = new JPanel();
    	panel.add(panel_1);
    	
    	JLabel label = new JLabel("Set Password: ");
    	panel_1.add(label);
    	passwordServer = new JTextField(20);
    	panel_1.add(passwordServer);
    	
    	fc = new JFileChooser();
    	
    	
    	panel_2 = new JPanel();
    	panel.add(panel_2);
    	JLabel label_1 = new JLabel("Port: ");
    	panel_2.add(label_1);
    	portServer = new JTextField(20);
    	panel_2.add(portServer);
    	portServer.setText(""+1775);
    	
    	panel_8 = new JPanel();
    	panel.add(panel_8);
    	openDialog = new JButton("Select File...");
    	panel_8.add(openDialog);
    	openDialog.addActionListener( new DButtonListener());
    	
    	panel_9 = new JPanel();
    	panel.add(panel_9);
    	
    	textField = new JTextField(30);
    	panel_9.add(textField);
    	textField.setEnabled(false);
    	panel.add(serverButton);
    	
    	panel_6 = new JPanel();
    	panel_7.add(panel_6);
    	panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
    	
    	lblEditAShared = new JLabel("Edit a Shared File");
    	panel_6.add(lblEditAShared);
    	
    	panel_3 = new JPanel();
    	panel_6.add(panel_3);
    	JLabel label_2 = new JLabel("Ip address of server: ");
    	panel_3.add(label_2);
    	ip = new JTextField(20);
    	panel_3.add(ip);
    	
    	panel_5 = new JPanel();
    	panel_6.add(panel_5);
    	JLabel label_4 = new JLabel("Password: ");
    	panel_5.add(label_4);
    	passwordClient = new JTextField(20);
    	panel_5.add(passwordClient);
    	
    	lblIncorrect = new JLabel("Incorrect");
    	lblIncorrect.setVisible(false);
    	panel_5.add(lblIncorrect);
    	
    	panel_4 = new JPanel();
    	panel_6.add(panel_4);
    	JLabel label_3 = new JLabel("Connection port: ");
    	panel_4.add(label_3);
    	portClient = new JTextField(20);
    	panel_4.add(portClient);
    	portClient.setText(""+1775);
    	clientButton = new JButton("Client");
    	panel_6.add(clientButton);
    	clientButton.addActionListener(new CButtonListener());
    	
    	serverButton.addActionListener(new SButtonListener());
    	
    }
	public class SButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			ConnectionController.create(passwordServer.getText(),Integer.parseInt(portServer.getText()));
		}
	}
	
	public class CButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(!ConnectionController.connect(ip.getText(),passwordClient.getText(),Integer.parseInt(portClient.getText())))
			{
				lblIncorrect.setVisible(true);
			}
		}
	}
	
	public class DButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int returnVal = fc.showOpenDialog(panel);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            textField.setText(file.getAbsolutePath());
	            FileHandler.setPath(file.getParent(), file.getName());
	        }
		}
	}
    
    
}