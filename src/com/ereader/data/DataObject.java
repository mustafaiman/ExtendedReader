package com.ereader.data;
import java.io.Serializable;
@SuppressWarnings("serial")
public class DataObject implements Serializable {
	private int type;
    public DataObject(int type) {
    	this.type = type;
    }
    public DataObject()
    {
    }
    public int getType()
    {
    	return type;
    }
    public void setType(int t)
    {
    	type = t;
    }
    public String toString()
    {
    	return ""+type;
    }
    
    
}