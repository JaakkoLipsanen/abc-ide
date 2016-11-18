/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model;

import com.flai.ide.StringHelper;
import com.flai.ide.StringHelper.TextInsert;

/**
 *
 * @author Jaakko
 */
class JavaCodeFormatter implements CodeFormatter {

	@Override
	public String processNewCode(String oldCode, String newCode, int caretPosition) {
		/* If the diff between oldCode and newCode is a newline ("\n"),
		   then add a proper indentatation
		*/
		
		TextInsert insert = StringHelper.isStringDifferenceAnInsertion(oldCode, newCode);
		if(insert != null && insert.InsertedText.equals("\n")) { // TODO: it could be "\n\r" or "   \n" or something maybe also?
			
			int indentationLevel = calculateIndentationAtIndex(newCode, insert.StartIndex - 1);
			return StringHelper.insert(newCode, 
					caretPosition, 
					StringHelper.repeat('\t', indentationLevel));
		}
		
		return newCode;
	}
	
	private int calculateIndentationAtIndex(String code, int index) {
		/* "{" adds +1 to indentation, "}" -1, as long as they are not inside string or comment */
		
		assert index < code.length();
		
		int indentationCount = 0;
		for(int i = 0; i <= index;) {
			char c = code.charAt(i);
			
			if(c == '\'') { // if c is ', then it is the start of char literal
				i = skipChar(code, i);
			}
			else if(c == '"') { // if c is ", then it is the start of string literal
				i = skipString(code, i);
			}
			else if(isStringStartingAt(code, i, "/*")) { // if /* starts at i, then it is start of multi-line comment
				i = skipMultilineComment(code, i); // -1 because i++ will be called
			}
			else if(isStringStartingAt(code, i, "//")) { // if // starts at i, then it is start of single-line comment
				i = skipSingleLineComment(code, i) - 1; // -1 because i++ will be called
			}
			else {
				if(c == '{') {
					indentationCount++;
				}
				else if(c == '}') {
					indentationCount--;
				}
				
				i++;
			}
		}
		
		return indentationCount;
	}
	
	private boolean isStringStartingAt(String originalCode, int index, String stringToSearch) {
		if(originalCode.length() < index + stringToSearch.length()) {
			return false;
		}
		
		return originalCode.substring(index, index + stringToSearch.length()).equals(stringToSearch);
	}

	private int skipMultilineComment(String code, int startIndex) {
		assert isStringStartingAt(code, startIndex, "/*");
		
		for(int i = startIndex + "/*".length(); i < code.length(); i++) {
			if(this.isStringStartingAt(code, i, "*/")) {
				return i + "*/".length();
			}
		}
		
		return code.length();
	}

	private int skipSingleLineComment(String code, int startIndex) {
		assert isStringStartingAt(code, startIndex, "//");
		
		for(int i = startIndex + "//".length(); i < code.length(); i++) {
			if(code.charAt(i) == '\n') {
				return i + 1;
			}
		}
		
		return code.length();
	}

	private int skipChar(String code, int startIndex) {
		assert code.charAt(startIndex) == '\'';
		
		if(code.length() == startIndex + 1) {
			return startIndex + 1;
		}
		
		if(code.charAt(startIndex + 1) == '\\') { // aka if the char is escaped, like \n, \' etc
			return startIndex + "'\\n'".length(); // i + length of char literal with escaped character
		}
		
		return startIndex + "'n'".length();
	}

	private int skipString(String code, int startIndex) {
		assert code.charAt(startIndex) == '"';
		
		boolean wasPreviousCharEscaped = false;
		int i = startIndex + 1; // +1 because " is one char
		for(; i < code.length(); i++) {
			char c = code.charAt(i);
			
			if(c == '"' && !wasPreviousCharEscaped) {
				return i + 1;
			}
			
			if(c == '\\') {
				if(wasPreviousCharEscaped) { // if prev char was escaped, then this char isn't "escape" but rather escaped \
					wasPreviousCharEscaped = false;
				}
				else {
					wasPreviousCharEscaped = true;
				}
			}
			else {
				wasPreviousCharEscaped = false;
			}
		}
		
		return i;
	}
	
}
