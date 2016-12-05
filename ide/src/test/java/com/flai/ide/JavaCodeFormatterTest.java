/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide;

import static org.junit.Assert.*;
import com.flai.ide.model.codeformatters.CodeFormatter;
import com.flai.ide.model.ProgrammingLanguage;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jaakko
 */
public class JavaCodeFormatterTest {
	
	private CodeFormatter _formatter;
	public JavaCodeFormatterTest() {
	}

	@Before
	public void setUp() {
		_formatter = CodeFormatter.create(ProgrammingLanguage.JAVA);
	}

	@Test
	public void testIndenting() {
		assertEquals(_formatter.processNewCode("void a(){}", "void a(){\n}", 10), "void a(){\n\t}");
	}
	
	@Test
	public void testIndenting2() {
		assertEquals(_formatter.processNewCode("void a(){\n\t}", "void a(){\n\tpublic}", 11), "void a(){\n\tpublic}");
	}
	
	
	@Test
	public void testIndenting3() {
		assertEquals(_formatter.processNewCode("public class Main {\n\tpublic static void main(){", "public class Main {\n\tpublic static void main(){\n", 48), "public class Main {\n\tpublic static void main(){\n\t\t");
	}
	
	@Test
	public void testIndenting4() {
		assertEquals(_formatter.processNewCode(
				"public class Main {\n\t String var = \"{{{{{{\";\n\tpublic static void main(){", 
				"public class Main {\n\t String var = \"{{{{{{\";\n\tpublic static void main(){\n", 73), 
				"public class Main {\n\t String var = \"{{{{{{\";\n\tpublic static void main(){\n\t\t");
	}
	
	@Test
	public void testIndenting5() {
		assertEquals(_formatter.processNewCode(
				"{", 
				"{\n", 2), 
				"{\n\t");
	}
	
	@Test
	public void testIndenting6() {
		assertEquals(_formatter.processNewCode(
				"char c = 'c';", 
				"char c = 'c';\n", 14), 
				"char c = 'c';\n");
	}
	
	@Test
	public void testIndenting7() {
		assertEquals(_formatter.processNewCode(
				"/*", 
				"/*\n", 3), 
				"/*\n");
	}
	
	@Test
	public void testIndenting8() {
		assertEquals(_formatter.processNewCode(
				"//{", 
				"//{\n", 4), 
				"//{\n");
	}
	
	@Test
	public void testIndenting9() {
		assertEquals(_formatter.processNewCode(
				"{//{\n}", 
				"{//{\n}\n", 7), 
				"{//{\n}\n");
	}
	
	@Test
	public void testIndenting10() {
		assertEquals(_formatter.processNewCode(
				"/", 
				"/\n", 2), 
				"/\n");
	}
	
	
	@Test
	public void testIndenting11() {
		System.out.println("result: " + _formatter.processNewCode(
				"/**/", 
				"/*\n*/", 3));
		assertEquals(_formatter.processNewCode(
				"/**/", 
				"/*\n*/", 3), 
				"/*\n*/");
	}
	
	@Test
	public void testIndenting12() {
		assertEquals(_formatter.processNewCode(
				"\"",
				"\"\n", 2),
				"\"\n");
	}
	
	@Test
	public void testIndenting13() {
		assertEquals(_formatter.processNewCode(
				"\'",
				"\'", 1),
				"\'");
	}
	
	@Test
	public void testIndenting14() {
		assertEquals(_formatter.processNewCode(
				"\"\n\"",
				"\"\n\"\n", 4),
				"\"\n\"\n");
	}
	
	@Test
	public void testIndenting15() {
		assertEquals(_formatter.processNewCode(
				"\"\\\\\"",
				"\"\\\\\"\n", 5),
				"\"\\\\\"\n");
	}
	
	@Test
	public void testIndenting16() {
		assertEquals(_formatter.processNewCode(
				"\'\\'\'",
				"\'\\'\'\n", 5),
				"\'\\'\'\n");
	}
}
