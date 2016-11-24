/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.viewmodels;

import com.flai.ide.model.CodeSnippet;
import com.flai.ide.model.ProgrammingLanguage;

/**
 * The main/root ViewModel, holds all data about the entire app
 * @author Jaakko
 */
public class EditorViewModel {

	/**
	 * The current code snippet file that is being edited/displayed in GUI.
	 */
	private CodeSnippetViewModel _currentSnippet = new CodeSnippetViewModel(createDefaultCodeSnippet());

	public EditorViewModel() {
	}

	public CodeSnippetViewModel getCurrentCodeSnippet() {
		return _currentSnippet;
	}

	public void loadSnippet(String filePath) {
		// todo
	}

	public void saveSnippet(String filePath) {
		// todo
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
			"import java.lang.*;" + "\n" + "\n" +
			"public static class Snippet {" + "\n"
			+ "	public static void main(String[] args) {" + "\n"
			+ "		/* Hello world! */ \n"
			+ "		System.out.println(\"Hello World!\");" + "\n"
			+ "	}" + "\n"
			+ "}", ProgrammingLanguage.JAVA);
	}
}
