/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 


/* A 1.4 class used by TextComponentDemo.java. */
package com.ereader.texteditor;

import javax.swing.text.*;

import com.ereader.data.DataObject;
import com.ereader.data.InsertData;
import com.ereader.data.RemoveData;
import com.ereader.data.ReplaceData;
import com.ereader.network.SendToAll;



public class DocumentNetworkFilter extends DocumentFilter {
	private SendToAll connector;
	private boolean mirroring=true;
	public DocumentNetworkFilter(SendToAll connector)
	{
		this.connector = connector;
	}
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
    	super.insertString(fb, offs, str, a);
    	sendChanges(new InsertData(offs,str,a));
    }
    
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
    	super.replace(fb, offs, length, str, a);
    	sendChanges(new ReplaceData(offs,length,str,a));
    }
    public void remove(FilterBypass fb,int offs,int length) throws BadLocationException {
    	super.remove(fb,offs,length);
    	sendChanges(new RemoveData(offs,length));
    }
    private void sendChanges(DataObject obj)
    {
    	if(mirroring)
    		connector.sendDataToAll(obj);
    	mirroring = true;
    }
    public void turnOffMirroring()
    {
    	mirroring = false;
    }

}