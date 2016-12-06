/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jaakko
 */
public class MiscHelperTest {
	
	public MiscHelperTest() {
	}
	

    // TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	 @Test
	 public void testStreamToString() {
		 String input = "hjsakfjhljasfas";
		 InputStream is = new ByteArrayInputStream( input.getBytes() );
		 
		 assertEquals(input, MiscHelper.convertStreamToString(is));
	 }
}
