package com.ereader.controller;
/**
 * @(#)ServerHandler.java
 *Decides what to do with the request that is coming from the user about the text file.
 * Writes, replaces,erases and updates contents from the text file according to the instances of the object
 *
 *
 * @author Ýpek Süsoy 
 * @version 1.00 2012/12/2
 */



import com.ereader.data.DataObject;
import com.ereader.data.DocData;
import com.ereader.data.InsertData;
import com.ereader.data.RemoveData;
import com.ereader.data.ReplaceData;
import com.ereader.data.UpdateDataRequest;
import com.ereader.network.Collaborative;
import com.ereader.network.Server;
import com.ereader.texteditor.TextComponentDemo;


public class ServerHandler implements Collaborative {

    TextComponentDemo editorPanel;
	Server server;
    public ServerHandler(Server serverinstance,TextComponentDemo panel) {
 		server = serverinstance;
 		editorPanel = panel;
    }
    /*
     *Action method of collaborative, decides what to do with the taken text file
     *@param obj some data object that comes from a cilent or a server
     *@param from address of the sender
     */
    public void action(DataObject obj,int from)
    {
    	try{
    		if(obj instanceof ReplaceData)//if the object is a replace data type than replaces the some content in the text file
	    	{
	    		((TextComponentDemo)editorPanel).replace(((ReplaceData)obj).offs,((ReplaceData)obj).length,((ReplaceData)obj).str,((ReplaceData)obj).a);
	    		server.sendDataToAll(obj,from);//sends the changed data 
	    	} else if(obj instanceof RemoveData)//if the type is remove data than erases some of the contents in the text
	    	{
	    		((TextComponentDemo)editorPanel).remove(((RemoveData)obj).offs,((RemoveData)obj).length);
	    		server.sendDataToAll(obj,from);//send changed data to everyone
	    	} else if(obj instanceof InsertData)//if the type is insert data then writes something in the text file 
	    	{
	    		((TextComponentDemo)editorPanel).insert(((InsertData)obj).offs,((InsertData)obj).str,((InsertData)obj).a);
	    		server.sendDataToAll(obj,from);//sends changed data to everyone
	    	} else if(obj instanceof UpdateDataRequest)//when there is newly added client send everyting to that client
	    	{
	    		server.sendData(new DocData(editorPanel.getDoc()),from);
	    	}
    	} catch(Exception e){
    		System.out.println(e.getMessage());
    	};
    }
    
}