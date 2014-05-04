package com.ereader.data;
import javax.swing.text.*;



@SuppressWarnings("serial")
public class ReplaceData extends DataObject {
	public int offs;
	public int length;
	public String str;
	public AttributeSet a;
    public ReplaceData(int offs, int length, String str, AttributeSet a) {
    	super(0);
    	this.offs = offs;
    	this.length = length;
    	this.str = str;
    	this.a = a;
    }
    
    
}