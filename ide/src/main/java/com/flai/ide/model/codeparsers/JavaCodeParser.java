/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model.codeparsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Class that can parse Java code
 * @author Jaakko
 */
class JavaCodeParser implements CodeParser {

	private static final String KEYWORDS[] = {
		"public", "private", "protected",
		"class", "interface", "enum",
		"abstract", "final", "static",
		"break", "continue", "return", "assert",
		"try", "catch", "throw", "throws", "finally",
		"while", "for", "switch", "case",
		"volatile", "synchronized", "transient",
		"byte", "char", "short", "int", "long", "void",	
		"float", "double", "do", "default",
		"this", "super", "new",
		"native", "package", "import",
		"instanceof", "implements", "extends",
		"if", "else", "goto",
		"true", "false",
	};
	
	@Override
	public CodeBlockContainer parseCode(String code) {
		ArrayList<CodeBlock> codeBlocks = new ArrayList<CodeBlock>();

		int currentBlockStartIndex = 0;
		for (int index = 0; index < code.length();) {
			final char c = code.charAt(index);
			final int currentIndex = index;
			
			Supplier<Integer> createBlockFunction = null; // variable holding a function that returns Integer and takes no parameters ("Integer function() { .. }")
			if (c == '\'') { // if c is ', then it is the start of char literal
				createBlockFunction = () -> createCharLiteral(codeBlocks, code, currentIndex);
			} 
			else if (c == '"') { // if c is ", then it is the start of string literal
				createBlockFunction = () -> createStringLiteral(codeBlocks, code, currentIndex);
			}
			else if(isSubstringStartingAt(code, currentIndex, "//")) { // start of single line comment
				createBlockFunction = () -> createSingleLineComment(codeBlocks, code, currentIndex);
			}
			else if(isSubstringStartingAt(code, currentIndex, "/*")) { // start of multi line comment
				createBlockFunction = () -> createMultiLineComment(codeBlocks, code, currentIndex);
			}
			else if(c == '{') { // opening brace
				createBlockFunction = () -> createCodeBlock(codeBlocks, CodeBlockType.OpeningBrace, currentIndex, 1);
			}
			else if(c == '}') { // closing brace
				createBlockFunction = () -> createCodeBlock(codeBlocks, CodeBlockType.ClosingBrace, currentIndex, 1);
			}
			else if(isKeywordStartingAt(currentIndex, code)) { // keyword ("public", "float" etc)
				createBlockFunction = () -> createKeyword(codeBlocks, code, currentIndex);
			}
			else { // a non-special character, continue on
				index++;
				continue;
			}
			
			// flush the CodeBlock from currentBlockStart...i (type == "Other)
			flushCurrentBlock(codeBlocks, code, currentBlockStartIndex, index);	
			index = createBlockFunction.get(); // call the function created above
			
			currentBlockStartIndex = index;
		}
		
		flushCurrentBlock(codeBlocks, code, currentBlockStartIndex, code.length()); // last type == "Other" block at the end of file
		return new CodeBlockContainer(codeBlocks);	
	}

	private int createCharLiteral(Collection<CodeBlock> blocks, String code, int startIndex) {
		assert code.charAt(startIndex) == '\'';

		int endIndex;
		if(code.length() == startIndex + 1) { // just ' in the end of file
			endIndex = startIndex + 1;
		}
		else if(code.charAt(startIndex + 1) == '\\') { // aka if the char is escaped, like \n, \' etc	
			endIndex = startIndex + 4;
		}
		else { // normal char
			endIndex = startIndex + 3;
		}
		
		blocks.add(new CodeBlock(startIndex, endIndex, CodeBlockType.CharLiteral));
		return endIndex;
	}

	private int createStringLiteral(Collection<CodeBlock> blocks, String code, int startIndex) {
		assert code.charAt(startIndex) == '"';

		boolean wasPreviousCharEscaped = false;
		int endIndex = startIndex + 1; // +1 because " is one char
		for (; endIndex < code.length(); endIndex++) {
			char c = code.charAt(endIndex);

			if (c == '"' && !wasPreviousCharEscaped) {
				endIndex++;
				break;
			}

			if (c == '\\') {
				if (wasPreviousCharEscaped) { // if prev char was escaped, then this char isn't "escape" but rather escaped \ character
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

		blocks.add(new CodeBlock(startIndex, endIndex, CodeBlockType.StringLiteral, null));
		return endIndex;
	}
	
	private int createSingleLineComment(ArrayList<CodeBlock> blocks, String code, int startIndex) {
		
		int endIndex = startIndex + "//".length();
		for (; endIndex < code.length(); endIndex++) {
			if (code.charAt(endIndex) == '\n') {
				break;
			}
		}

		blocks.add(new CodeBlock(startIndex, endIndex, CodeBlockType.SingleLineComment));
		return endIndex;
	}
	
	private int createMultiLineComment(ArrayList<CodeBlock> blocks, String code, int startIndex) {
		
		int endIndex = startIndex + "//".length();
		for (; endIndex < code.length(); endIndex++) {
			if (this.isSubstringStartingAt(code, endIndex, "*/")) {
				endIndex += "*/".length();
				break;
			}
		}

		blocks.add(new CodeBlock(startIndex, endIndex, CodeBlockType.MultiLineComment));
		return endIndex;
	}
	
	private boolean isKeywordStartingAt(int startIndex, String code) {
		if(startIndex != 0 && Character.isAlphabetic(code.charAt(startIndex - 1))) {
			return false;
		}
		
		for(String keyword : JavaCodeParser.KEYWORDS) {
			if(isSubstringStartingAt(code, startIndex, keyword)) {
				
				int endIndex = startIndex + keyword.length();
				if(code.length() > endIndex && Character.isAlphabetic(code.charAt(endIndex)))
				{
					continue;
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	private int createKeyword(ArrayList<CodeBlock> codeBlocks, String code, int startIndex) {
		for(String keyword : JavaCodeParser.KEYWORDS) {
			if(isSubstringStartingAt(code, startIndex, keyword)) {
				codeBlocks.add(new CodeBlock(startIndex, startIndex + keyword.length(), CodeBlockType.Keyword));
				return startIndex + keyword.length();
			}
		}
		
		throw new IllegalArgumentException("createKeyword requires calling isKeywordStartingAt before");
	}
	
	private int createCodeBlock(ArrayList<CodeBlock> blocks, CodeBlockType type, int startIndex, int length) {
		blocks.add(new CodeBlock(startIndex, startIndex + length, type));
		return startIndex + length;
	}
	
	private boolean isSubstringStartingAt(String originalCode, int index, String stringToSearch) {
		if (originalCode.length() < index + stringToSearch.length()) {
			return false;
		}

		return originalCode.substring(index, index + stringToSearch.length()).equals(stringToSearch);
	}
	
	private void flushCurrentBlock(Collection<CodeBlock> blocks, String code, int startIndex, int endIndex) {
		if(startIndex == endIndex) {
			return;
		}
		
		blocks.add(new CodeBlock(startIndex, endIndex, CodeBlockType.Other));
	}
}
