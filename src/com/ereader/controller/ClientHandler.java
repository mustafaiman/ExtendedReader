package com.ereader.controller;
/**
 * @(#)ClientHandler.java
 *Decides what to do with the request that is coming from the user about the text file. Writes,
 * replaces,erases and updates contents from the text file according to the instances of the object
 *
 *
 * @author 
 * @version 1.00 2012/12/2
 */



import com.ereader.data.*;
import com.ereader.network.*;
import com.ereader.texteditor.TextComponentDemo;


public class ClientHandler implements Collaborative {

    TextComponentDemo editorPanel;
	ServerClient server;
    public ClientHandler(ServerClient cli,TextComponentDemo panel) {
    	server = cli;
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
	    	} else if(obj instanceof RemoveData)//if the type is remove data than erases some of the contents in the text
	    	{
	    		((TextComponentDemo)editorPanel).remove(((RemoveData)obj).offs,((RemoveData)obj).length);
	    	} else if(obj instanceof InsertData)//if the type is insert data then writes something in the text file 
	    	{
	    		((TextComponentDemo)editorPanel).insert(((InsertData)obj).offs,((InsertData)obj).str,((InsertData)obj).a);
	    	} else if(obj instanceof DocData)// the content
	    	{
	    		editorPanel.updateAll(((DocData)obj).text);
	    	}
    	} catch(Exception e){
    		e.printStackTrace();
    	};
    	
    	
    }
    
}