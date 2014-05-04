package com.ereader.network;
import com.ereader.data.DataObject;

/**
 * @(#)SendToAll.java
 *
 *
 * @author Mustafa Kamil Ýman
 * @version 1.00 2012/12/2
 */


public interface SendToAll {

	/**
	 * Sends a given data to all clients connected
	 *
	 * @param  obj  The object to be sent
	 */
    public void sendDataToAll(DataObject obj);
    
    /**
	 * Sends a given data to all clients connected but the specified one
	 *
	 * @param  obj    The object to be sent
	 * @param  except Id of the client to be kept apart
	 */
    public void sendDataToAll(DataObject obj,int except);
    
    
}