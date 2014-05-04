package com.ereader.network;

/**
 * @(#)Server.java
 *
 *
 * @author Mustafa Kamil Ýman
 * @version 1.00 2012/11/9
 */

import java.net.*;

import com.ereader.controller.ServerHandler;
import com.ereader.data.AuthenticationData;
import com.ereader.data.DataObject;
import com.ereader.data.PasswordData;

public class Server implements Runnable,Collaborative,SendToAll {
	int serverPort;//which port to be used by server
	Collaborative mainObj=null;//this will reference to the instance action method of which will be run when a new data is up
	ServerSocket serverSocket;//client's socket
	ServerClients clients;//list of clients
	String password = null;
	static int idcount;

    public Server(int port, Collaborative mainObj)
    {
    	this(port);
    	this.mainObj = mainObj;
    }
    public Server(int port)
    {
    	clients = new ServerClients();
    	
    	try
    	{
    		serverPort = port;
    		serverSocket = new ServerSocket(serverPort);//set up the server
    	} catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
    public void setPassword(String pass)
    {
    	password = pass;
    }
    public void removePassword()
    {
    	password = null;
    }
    public void setHandler(ServerHandler handler)
    {
    	mainObj = handler;
    }
    
    public void accept()//this method accept a new client each time it is called
    {
    	Socket co = null;
    	try
    	{
    		ServerClient temp = null;
    		try{
    			co = serverSocket.accept();//accepting the new client
    			temp = new ServerClient(co,idcount++,this);
    		} catch(Exception e)
    		{
    			System.exit(1);
    		}
    		
    		/* when a new client is connected it sends a PasswordData and this method
    		 * checks whether the password is correct and responds 
    		 */
    		
    		if(((PasswordData)temp.dataInput.readObject()).password.equals(this.password))
    		{
    			clients.add(temp);//adds the client to the list of active clients
    			temp.sendData(new AuthenticationData(true));
    		} else {
    			temp.sendData(new AuthenticationData(false));
    		}
    	} catch(Exception e){
    		System.exit(1);
    	}
    }
    public void sendData(DataObject obj,int id)
    {
    	clients.get(id).sendData(obj);
    }
    public void sendDataToAll(DataObject obj)
    {
    	clients.sendDataToAll(obj);
    }
    public void sendDataToAll(DataObject obj,int except)
    {
    	clients.sendDataToAll(obj,except);
    }
    public ServerClient listenClient(int id)
    {
    	return clients.get(id);
    }
    
    /*
     * run handler's action method
     */
    public void action(DataObject obj,int from)
    {
    	if(mainObj == null)
    		return;
    	mainObj.action(obj,from);
    }
    public void run()
    {
    	while(true) {
    		accept();
    	}
    }
    
    
    
}