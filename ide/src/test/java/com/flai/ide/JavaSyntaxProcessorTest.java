/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide;

import com.flai.ide.model.ProgrammingLanguage;
import com.flai.ide.model.codeparsers.CodeParser;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jaakko
 */
public class JavaSyntaxProcessorTest {
	
	private CodeParser _processor;
	public JavaSyntaxProcessorTest() {
	}
	
	@Before
	public void setUp() {
		_processor = CodeParser.create(ProgrammingLanguage.JAVA);
	}

	@Test
	public void processingCodeWorks1() {
		assertTrue(_processor.parseCode("int").getCount() == 1);
	}
	
	@Test
	public void processingCodeWorks2() {
		assertTrue(_processor.parseCode("int").get(0).Type == CodeParser.CodeBlockType.Keyword);
	}
	
	@Test
	public void processingCodeWorks3() {
		assertTrue(_processor.parseCode("/*    */short").getCount() == 2);
	}
	
	@Test
	public void processingCodeWorks4() {
		assertTrue(_processor.parseCode("/*    */short").get(0).Type == CodeParser.CodeBlockType.MultiLineComment);
	}
	
	@Test
	public void processingCodeWorks5() {
		assertTrue(_processor.parseCode("/*    */short").get(1).Type == CodeParser.CodeBlockType.Keyword);
	}
}
