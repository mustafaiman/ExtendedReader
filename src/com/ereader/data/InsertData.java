package com.ereader.data;
import javax.swing.text.*;
@SuppressWarnings("serial")
public class InsertData extends DataObject {
	public int offs;
	public String str;
	public AttributeSet a;
    public InsertData(int offs, String str, AttributeSet a) {
    	super(1);
    	this.offs = offs;
    	this.str = str;
    	this.a = a;
    }
    
    
}