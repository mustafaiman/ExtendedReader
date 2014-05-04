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
 
/*
 * TextComponentDemo.java requires one additional file:
 *   DocumentSizeFilter.java
 */
 
package com.ereader.texteditor;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
 
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.undo.*;

import com.ereader.MainFrame;
import com.ereader.controller.FileHandler;
import com.ereader.network.SendToAll;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
 
@SuppressWarnings("serial")
public class TextComponentDemo extends JPanel {
	SendToAll connector;
	DocumentNetworkFilter filter;
	Path path;
	
    JTextPane textPane;
    AbstractDocument doc;
    static final int MAX_CHARACTERS = 300;
    String newline = "\n";
    HashMap<Object, Action> actions;
 
    protected UndoManager undo = new UndoManager();
 
    public TextComponentDemo(SendToAll connector) {
        //super("TextComponentDemo");
        this.connector = connector;
        filter = new DocumentNetworkFilter(connector);
 
        //Create the text pane and configure it.
        textPane = new JTextPane();
        textPane.setCaretPosition(0);
        textPane.setMargin(new Insets(5,5,5,5));
        StyledDocument styledDoc = textPane.getStyledDocument();
        if (styledDoc instanceof AbstractDocument) {
            doc = (AbstractDocument)styledDoc;
            doc.setDocumentFilter(filter);
        } else {
            System.err.println("Text pane's document isn't an AbstractDocument!");
            System.exit(-1);
        }
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(800, 600));
 
        
       	add(scrollPane);
 
        //Set up the menu bar.
        actions=createActionTable(textPane);
        JMenu fileMenu = createFileMenu();
        JMenu editMenu = createEditMenu();
        JMenu styleMenu = createStyleMenu();
        JMenuBar mb = new JMenuBar();
        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(styleMenu);
        MainFrame.mainFrame.setJMenuBar(mb);
        
        textPane.setCaretPosition(0);
 
    }
    public void insert(int offs,String str,AttributeSet a) throws BadLocationException
    {
    	filter.turnOffMirroring();
    	doc.insertString(offs,str,a);
    }
    public void replace(int offs,int length,String text,AttributeSet a) throws BadLocationException
    {
    	filter.turnOffMirroring();
    	doc.replace(offs,length,text,a);
    }
    public void remove(int offs,int length) throws BadLocationException
    {
    	filter.turnOffMirroring();
    	doc.remove(offs,length);
    }
 
 
    //This one listens for edits that can be undone.
    
    	
   
 
    //Create the edit menu.
    protected JMenu createEditMenu() {
        JMenu menu = new JMenu("Edit");
 

 
        //These actions come from the default editor kit.
        //Get the ones we want and stick them in the menu.
        menu.add(getActionByName(DefaultEditorKit.cutAction));
        menu.add(getActionByName(DefaultEditorKit.copyAction));
        menu.add(getActionByName(DefaultEditorKit.pasteAction));
 
        menu.addSeparator();
 
        menu.add(getActionByName(DefaultEditorKit.selectAllAction));
        return menu;
    }
    protected JMenu createFileMenu() {
        JMenu menu = new JMenu("File");
 
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		try{
        			FileHandler.save(""+doc.getText(0,doc.getLength()));
        		} catch(Exception ex)
        		{
        			ex.printStackTrace();
        		}
        	}
        });
 

        JMenuItem saveAs = new JMenuItem("Save As...");
        saveAs.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		JFileChooser fc = new JFileChooser();
        		int returnVal = fc.showSaveDialog(MainFrame.mainFrame);
    			if (returnVal == JFileChooser.APPROVE_OPTION) {
    	            File file = fc.getSelectedFile();
    	            String path = file.getAbsolutePath();
    	            try{
    	            	FileHandler.saveAs(""+doc.getText(0,doc.getLength()),path);
    	            } catch(Exception ex)
    	            {
    	            	ex.printStackTrace();
    	            }
    			}
        	}
        });

        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		MainFrame.quit();
        	}
        });
        
        if(MainFrame.isServer())
        {
        	menu.add(save);
        	menu.add(saveAs);
        }
        menu.addSeparator();
        menu.add(quit);
        return menu;
    }
 
    //Create the style menu.
    protected JMenu createStyleMenu() {
        JMenu menu = new JMenu("Style");
 
        Action action = new StyledEditorKit.BoldAction();
        action.putValue(Action.NAME, "Bold");
        menu.add(action);
 
        action = new StyledEditorKit.ItalicAction();
        action.putValue(Action.NAME, "Italic");
        menu.add(action);
 
        action = new StyledEditorKit.UnderlineAction();
        action.putValue(Action.NAME, "Underline");
        menu.add(action);
 
        menu.addSeparator();
 
        menu.add(new StyledEditorKit.FontSizeAction("12", 12));
        menu.add(new StyledEditorKit.FontSizeAction("14", 14));
        menu.add(new StyledEditorKit.FontSizeAction("18", 18));
 
        menu.addSeparator();
 
        menu.add(new StyledEditorKit.FontFamilyAction("Serif",
                                                      "Serif"));
        menu.add(new StyledEditorKit.FontFamilyAction("SansSerif",
                                                      "SansSerif"));
 
        menu.addSeparator();
 
        menu.add(new StyledEditorKit.ForegroundAction("Red",
                                                      Color.red));
        menu.add(new StyledEditorKit.ForegroundAction("Green",
                                                      Color.green));
        menu.add(new StyledEditorKit.ForegroundAction("Blue",
                                                      Color.blue));
        menu.add(new StyledEditorKit.ForegroundAction("Black",
                                                      Color.black));
 
        return menu;
    }
 
    public void initDocument(ArrayList<String> initString) {
       
        SimpleAttributeSet[] attrs = initAttributes(initString.size());
 
        try {
            for (int i = 0; i < initString.size(); i ++) {
            	filter.turnOffMirroring();
                doc.insertString(doc.getLength(), initString.get(i) + newline, attrs[i]);
            }
        } catch (BadLocationException ble) {
            System.err.println("Couldn't insert initial text.");
        }
    }
    
    public void updateAll(String text) throws BadLocationException
    {
    	filter.turnOffMirroring();
    	doc.remove(0,doc.getLength());
    	filter.turnOffMirroring();
    	doc.insertString(0,text,new SimpleAttributeSet());
    }
    public String getDoc() throws BadLocationException
    {
    	String text = ""+doc.getText(0,doc.getLength());
    	return text;
    }
 
    protected SimpleAttributeSet[] initAttributes(int length) {
        //Hard-code some attributes.
        SimpleAttributeSet[] attrs = new SimpleAttributeSet[length];
        return attrs;
    }
 
    //The following two methods allow us to find an
    //action provided by the editor kit by its name.
    private HashMap<Object, Action> createActionTable(JTextComponent textComponent) {
        HashMap<Object, Action> actions = new HashMap<Object, Action>();
        Action[] actionsArray = textComponent.getActions();
        for (int i = 0; i < actionsArray.length; i++) {
            Action a = actionsArray[i];
            actions.put(a.getValue(Action.NAME), a);
        }
    	return actions;
    }
 
    private Action getActionByName(String name) {
        return actions.get(name);
    }
 
   
 
}