package com.ereader.data;

@SuppressWarnings("serial")
public class AuthenticationData extends DataObject {
	public boolean authenticated;
    public AuthenticationData(boolean b) {
    	authenticated = b;
    }
    
    
}