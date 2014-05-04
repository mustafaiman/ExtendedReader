package com.ereader;

import java.awt.event.*;

import javax.swing.*;
/**
 * @(#)MainFrame.java
 *
 *
 * @author 
 * @version 1.00 2012/12/26
 */


@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private static boolean isServer = false;
	public static JFrame mainFrame;
	public static JPanel mainPanel;
    public static void main(String[] args) {
    	mainPanel = new MainPanel();
    	
    	JMenuBar menuBar = new JMenuBar();
    	JMenu menu = new JMenu("File");
    	JMenuItem quit = new JMenuItem("Quit");
    	quit.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e)
    		{
    			quit();
    		}
    	});
    	menu.add(quit);
    	menuBar.add(menu);
    	
    	mainFrame = new JFrame("Extended Reader");
    	mainFrame.setJMenuBar(menuBar);
    	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	mainFrame.getContentPane().add(mainPanel);
    	
    	
    	
    	mainFrame.pack();
    	mainFrame.setVisible(true);
    }
    public static boolean isServer()
    {
    	return isServer;
    }
    public static void setServer()
    {
    	isServer = true;
    }
    public static void quit()
    {
    	mainFrame.dispose();
    	System.exit(0);
    }
    
    
}