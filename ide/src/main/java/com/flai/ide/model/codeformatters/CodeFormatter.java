/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model.codeformatters;

import com.flai.ide.model.ProgrammingLanguage;

/**
 * An interface that all code formatters implements.
 * 
 * @author Jaakko
 */
public interface CodeFormatter {

	/**
	 * Formats the code.
	 * @param oldCode the old code
	 * @param newCode the new code with inserted/added/removed text
	 * @param caretPosition the position where text was inserted to
	 * @return the formatted code
	 */
	public String formatCode(String oldCode, String newCode, int caretPosition);
	
	/**
	 * Creates a CodeFormatter for the given language.
	 * @param language ProgrammingLanguage from which to create the CodeFormatter
	 * @return the created CodeFormatter
	 */
	public static CodeFormatter create(ProgrammingLanguage language) {
		switch (language) {
			case JAVA:
				return new JavaCodeFormatter();
		}

		throw new IllegalArgumentException("");
	}
}
