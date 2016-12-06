package com.flai.ide;

import com.flai.ide.model.CodeSnippet;
import com.flai.ide.model.ProgrammingLanguage;
import com.flai.ide.model.codeparsers.CodeParser;
import com.flai.ide.viewmodels.CodeSnippetViewModel;
import java.io.File;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jaakko
 */
public class CodeSnippetViewModelTest {
	
	private CodeSnippetViewModel _vm;
	public CodeSnippetViewModelTest() {
	}

	@Before
	public void setUp() {
		_vm = new CodeSnippetViewModel(new CodeSnippet("test", ProgrammingLanguage.JAVA));
	}
	

	 @Test
	 public void initWorks() {
		 assertEquals(_vm.getCode(), "test");
	 }
	 
	 @Test
	 public void setCodeWorks() {
		 _vm.setAndFormatNewCode("test2", 0);
		 assertEquals(_vm.getCode(), "test2");
	 }
	 
	 @Test
	 public void setCodeWorks2() {
		 assertEquals(_vm.setAndFormatNewCode("test2", 0), "test2");
	 }
	 
	 @Test
	 public void setCodeWorks3() {
		 assertEquals(_vm.setAndFormatNewCode("test", 0), "test");
	 }
	 
	 @Test
	 public void parseCode1() {
		 assertTrue(_vm.parseCode("{").get(0).Type == CodeParser.CodeBlockType.OpeningBrace);
	 }
	 
	  @Test
	 public void saveToFile() {
		 assertTrue(_vm.saveToFile(new File("K://")) == false);
	 }
}
