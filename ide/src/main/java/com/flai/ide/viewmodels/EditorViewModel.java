/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.viewmodels;

import com.flai.ide.MiscHelper;
import com.flai.ide.model.CodeSnippet;
import com.flai.ide.model.ProgrammingLanguage;
import java.io.File;

/**
 * The main/root ViewModel, holds all data about the entire app
 * @author Jaakko
 */
public class EditorViewModel {
	
	// todo: multiple CodeSnippets? I could have tabs in the editor to change between the open code snippets?

	/**
	 * The current code snippet file that is being edited/displayed in GUI.
	 */
	private CodeSnippetViewModel _currentSnippet = new CodeSnippetViewModel(createDefaultCodeSnippet());

	public EditorViewModel() {
	}

	public CodeSnippetViewModel getCurrentCodeSnippet() {
		return _currentSnippet;
	}

	/**
	 * Loads a code snippet from a file and sets it as a the current snippet
	 * @param file file to load snippet from
	 * @return whether load was succesful or not
	 */
	public boolean loadSnippetFromFile(File file) {
		String text = MiscHelper.readTextFromFile(file);
		if(text == null) {
			return false;
		}
		
		ProgrammingLanguage language = /* I only support JAVA, but I could check the file extension of the file here! */ ProgrammingLanguage.JAVA;
		_currentSnippet = new CodeSnippetViewModel(new CodeSnippet(text, language));
		
		return true;
	}

	/**
	 * Loads the default snippet and sets it as the current snippet
	 */
	public void loadDefaultSnippet() {
		_currentSnippet = new CodeSnippetViewModel(createDefaultCodeSnippet());
	}
	
	/**
	 * Creates the default code snippet (in Java: main class and main method)
	 * todo: add somekind of flag for what language is being asked (java or c#
	 * or python etc)
	 *
	 * @return the default CodeSnippet value
	 */
	private static CodeSnippet createDefaultCodeSnippet() {
		return new CodeSnippet(
			"import java.lang.*;" + "\n" +
			"import java.util.Scanner;" + "\n" + 
			"\n" +
			"public class Snippet {" + "\n"
			+ "	public static void main(String[] args) {" + "\n"
			+ "		/* Hello world! */ \n"
			+ "		System.out.println(\"Hello World!\");" + "\n"
			+ "	}" + "\n"
			+ "}", ProgrammingLanguage.JAVA);
	}
}
