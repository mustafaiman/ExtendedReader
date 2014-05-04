/****************************************************************************************************
 *allows a connection to ocucur by controlling the setted password. If the connection occurs than calls the text file to open.
 *@author Ýpek Süsoy
 */
package com.ereader.controller;

import java.net.*;
import com.ereader.*;
import com.ereader.data.*;
import com.ereader.network.*;
import com.ereader.texteditor.*;


public class ConnectionController {
	/*
	 *allows connection btween computers by taking the ip address, port name and password
	 *@param ip ip address of the computer
	 *@param pass password that has been set by the server
	 *@param port port number of the connector
	 */
    public static boolean connect(String ip, String pass,int port)
    {
    	ServerClient client = null;
    	ClientHandler handler = null;
    	try {
	    	client = new ServerClient(new Socket(ip,port),pass,null);//tries to connect a client	
    	} catch(Exception e){
    		return false;
    	};
    	TextComponentDemo panel = new TextComponentDemo(client);//for the text file from the directory
    	handler = new ClientHandler(client,panel);
    	client.setHandler(handler);
	    Thread thread = new Thread(client);//continue to run in the background
	    thread.start();
	    
	    MainFrame.mainFrame.getContentPane().removeAll();//removes everyting in the panel so that the text file can be shown
    	MainFrame.mainFrame.getContentPane().add(panel);//adds the text file in to the panel
    	MainFrame.mainFrame.setResizable(false);
	    client.sendData(new UpdateDataRequest());//calls update data request to send thedata to the clients
    	MainFrame.mainFrame.pack();
    	MainFrame.mainFrame.setVisible(true);
	    return true;
    }
    /*
     *
     *@para pass password that has been given by the server
     *@param port number whic the connector connects
     */
    public static void create(String pass, int port)
    {
    	Server server = null;
    	ServerHandler handler = null;
    	try {
	    	server = new Server(port);//creates a server from the given port	
    	} catch(Exception e){
    		e.printStackTrace();
    	};
	    MainFrame.setServer();
    	server.setPassword(pass);//sets up the connection password
    	TextComponentDemo panel = new TextComponentDemo(server);//takes the text file from the directory by text component demo
    	handler = new ServerHandler(server,panel);
    	server.setHandler(handler);
    	Thread thread = new Thread(server);//continue to runn in the background
	    thread.start();
	    MainFrame.mainFrame.getContentPane().removeAll();//first removes everyting
    	MainFrame.mainFrame.getContentPane().add(panel);//then adds the new content to the panel
    	try{
    		FileHandler.prepareFile();
    		panel.initDocument(FileHandler.getLines());//returns thhe arraylist which is held by the filehandler
    	} catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	MainFrame.mainFrame.setResizable(false);
    	MainFrame.mainFrame.pack();
    	MainFrame.mainFrame.setVisible(true);
    }    
}