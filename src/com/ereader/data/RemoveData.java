package com.ereader.data;

@SuppressWarnings("serial")
public class RemoveData extends DataObject {
	public int offs;
	public int length;
    public RemoveData( int offs, int length) {
    	super(2);
    	this.offs = offs;
    	this.length = length;
    }
    
    
}