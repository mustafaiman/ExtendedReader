package com.ereader.network;

import com.ereader.controller.*;
import com.ereader.data.*;
import java.net.*;
import java.io.*;

public class ServerClient implements Runnable,SendToAll {
	int id;
	Collaborative mainObj;
	boolean changed = false;
	DataObject received;
	ObjectInputStream dataInput;
	ObjectOutputStream dataOutput;
    Socket socket;
    
    public ServerClient(Socket socket,Collaborative mainObj) {
    	this.socket = socket;
    	this.mainObj = mainObj;
    	try
    	{
	    	dataOutput = new ObjectOutputStream(this.socket.getOutputStream());
    		dataInput = new ObjectInputStream(this.socket.getInputStream());
    	} catch(Exception e){
    		System.out.println("Streams could not be created\n" +e.getMessage()+e.getCause());
    	}
    }
    /**
     * This constructor is to be used by ServerClients. ServerClient may give id number to instance
     * @param socket 
     * @param id      An id number to be recognized with
     * @param mainObj Reference to object that will handle the requests
     */
    public ServerClient(Socket socket, int id,Collaborative mainObj) {
    	this(socket,mainObj);
    	this.id = id;
    }

    /**
     * This constructor is to be used by ServerClients. ServerClient may give id number to instance
     * @param socket 
     * @param password Server password
     * @param mainObj  Reference to object that will handle the requests
     */
    public ServerClient(Socket socket, String password,Collaborative mainObj) throws Exception {
    	this(socket,mainObj);
    	sendData(new PasswordData(password));
    	if(!((AuthenticationData)dataInput.readObject()).authenticated)
    		throw new AuthenticationError();
    }
    public void setHandler(ClientHandler handler)
    {
    	mainObj = handler;
    }
    public InetAddress getInetAddress()
    {
    	return socket.getInetAddress();
    }
    public DataObject getInput()
    {
    	changed = false;
    	DataObject t = received;
    	return t;
    }
    public boolean isThereSomeNew()
    {
    	return changed;
    }
    public void sendData(DataObject obj)
    {
    	try
    	{
    		dataOutput.writeObject(obj);
    	} catch(Exception e){
    		System.out.println(e.getMessage());
    	};
    }
    public void sendDataToAll(DataObject obj)
    {
    	sendData(obj);
    }
    public void sendDataToAll(DataObject obj,int except)
    {
    	sendData(obj);
    }
    public int getId()
    {
    	return id;
    }
    
    //listens for new input deivered via network conenction
    public void run()
    {
    	while(true)
	    {
	    	try {
	    		received = (DataObject)dataInput.readObject();
	    		changed = true;
	    		mainObj.action(getInput(),getId());//run handler's action method
	    	} catch(Exception e)
	    	{
	    	};
    	}
    }
    
    
    
}