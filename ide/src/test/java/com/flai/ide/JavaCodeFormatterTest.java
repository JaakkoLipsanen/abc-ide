/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide;

import static org.junit.Assert.*;
import com.flai.ide.model.CodeFormatter;
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
}
