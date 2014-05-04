package com.ereader.data;

/**
 * @(#)PasswordData.java
 *
 *
 * @author 
 * @version 1.00 2012/12/26
 */


@SuppressWarnings("serial")
public class PasswordData extends DataObject {
	public String password;
    public PasswordData(String pass) {
    	password = pass;
    }
    
    
}