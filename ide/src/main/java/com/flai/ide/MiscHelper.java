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
	
	// from http://stackoverflow.com/a/1149712
	public static String exceptionToString(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		sw.toString(); // stack trace as a string
		
		return sw.toString();
	}
	
	// reads InputStream to a String
	public static String convertStreamToString(InputStream is) {
		Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	
	public static String readTextFromFile(File file) {
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			return null;
		}
	}
	
	public static boolean isWindowsOS() {
		return System.getProperty("os.name").contains("Windows");
	}
}
