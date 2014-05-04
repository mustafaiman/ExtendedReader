package com.ereader.controller;
/**
 * @(#)FileHandler.java
 *convert text into an arraylist nd consist method to save and savesas
 *
 * @author  Ýpek Süsoy
 * @version 1.00 2012/12/9
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileHandler {
	static BufferedReader reader;
	private static Path path;//to locate a file
	private static String directory,fileName;
	
    public FileHandler(String d, String f) throws Exception {
    	directory = d;
    	fileName = f;
    	path = FileSystems.getDefault().getPath(directory,fileName);
    	reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
    }
   
    public static void prepareFile() throws Exception
    {
    	path = FileSystems.getDefault().getPath(directory, fileName);//takes a directory and a file name and returns the content
    	reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
    }
    /*
     *@param dir directory
     *@param name file name
     */
    public static void setPath(String dir, String name)
    {
    	directory = dir;
    	fileName = name;
    }
    /*
     *takes the contents in the text file and converts it into a string arraylist
     */
    public static ArrayList<String> getLines() throws Exception
    {
    	ArrayList<String> lines = new ArrayList<String>();
    	while(reader.ready())
       	{
       		lines.add(reader.readLine());//reads the line and adds them into the arraylist
       	}
       	return lines;
    }
    /*
     *saves the lines in the arrraylist in the same file that has been opened
     *@param line string which will be saved
     */
    public static void save(String line)
    {
    	try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
    		writer.write(line,0,line.length());//takes the lines stars from the index 0 and saves till the end
    	} catch (Exception x) {
    		x.printStackTrace();//prints stack if something is worng
    	}
    }
    /*
     *saves the string in to the another directory
     *@param line text to be saved
     *@param path director's location
     */
    public static void saveAs(String line,String path)
    {
    	try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"))) {//goes to the directory
    	
    		writer.write(line,0,line.length());//saves the lines starting from 0 till the length of the lines
    	} catch (Exception x) {
    		x.printStackTrace();
    	}
    }
    
    
}