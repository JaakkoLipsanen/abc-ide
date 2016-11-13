/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model;

/**
 *
 * @author Jaakko
 */
public interface CodeFormatter {
	public String processNewCode(String oldCode, String newCode, int caretPosition);	
// public SyntaxHighlight calculateSyntaxHighlighting(String code);
	
	public static CodeFormatter create(ProgrammingLanguage language) {
		switch(language) {
			case JAVA:
				return new JavaCodeFormatter();
		}
		
		throw new IllegalArgumentException("");
	}
}
