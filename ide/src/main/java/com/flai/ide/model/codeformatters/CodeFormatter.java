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

	public String formatCode(String oldCode, String newCode, int caretPosition);
	public static CodeFormatter create(ProgrammingLanguage language) {
		switch (language) {
			case JAVA:
				return new JavaCodeFormatter();
		}

		throw new IllegalArgumentException("");
	}
}
