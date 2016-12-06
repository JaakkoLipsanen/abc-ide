/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Scanner;

/**
 *
 * @author Jaakko
 */
public class MiscHelper {
	
	
		
	/**
	 * returns the stack trace of the parameter throwable as a string
	 * @param t the throwable from which to return the stack trace from
	 * @return string containing the stack trace
	 */
	public static String exceptionToString(Throwable t) {
		// from http://stackoverflow.com/a/1149712
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		sw.toString(); // stack trace as a string
		
		return sw.toString();
	}
	
	/**
	 * Reads an input stream and returns the contents of it as a string
	 * @param is 
	 * @return contents of the input stream
	 */
	public static String convertStreamToString(InputStream is) {
		Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	
	/**
	 * Reads all text from a file
	 * @param file the file from which to read
	 * @return the contents of the file as a string
	 */
	public static String readTextFromFile(File file) {
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Returns true if the current OS is windows
	 */
	public static boolean isWindowsOS() {
		return System.getProperty("os.name").contains("Windows");
	}
}
