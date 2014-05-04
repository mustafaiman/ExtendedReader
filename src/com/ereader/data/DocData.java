package com.ereader.data;


@SuppressWarnings("serial")
public class DocData extends DataObject {
	public String text;
    public DocData(String text) {
    	super(10);
    	this.text = text;
    }
    
    
}