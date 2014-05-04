package com.ereader.network;


/**
 * @(#)ServerClients.java
 *
 *
 * @author Mustafa Kamil Ýman
 * @version 1.00 2012/11/15
 */


import java.util.ArrayList;

import com.ereader.data.DataObject;

public class ServerClients {
	ArrayList<ServerClient> clients;//list of clients
	
    public ServerClients() {
    	clients = new ArrayList<ServerClient>();
    }
    public void add(ServerClient cl)
    {
    	clients.add(cl);//add to the list
    	
    	//start it as a standalone thread
    	Thread thread = new Thread(cl);
    	thread.start();
    }
    public ServerClient get(int i)
    {
    	return clients.get(getById(i));
    }
    public void sendDataToAll(DataObject obj)
    {
    	for(ServerClient cli:clients)
    		cli.sendData(obj);
    }
    public void sendDataToAll(DataObject obj,int except)
    {
    	for(ServerClient cli:clients)
    		if(!(cli.getId() == except))
    			cli.sendData(obj);
    }
    private int getById(int id)
    {
    	for(int i=0;i<clients.size();i++)
    		if(clients.get(i).getId() == id)
    			return i;
    	return -1;
    }
    public void removeClient(int i)
    {
    	clients.remove(getById(i));
    }
    public int getSize()
    {
    	return clients.size();
    } 
}