/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model.codeformatters;

import com.flai.ide.StringHelper;
import com.flai.ide.StringHelper.TextInsert;
import com.flai.ide.model.ProgrammingLanguage;
import com.flai.ide.model.codeparsers.CodeParser;
import com.flai.ide.model.codeparsers.CodeParser.CodeBlock;
import com.flai.ide.model.codeparsers.CodeParser.CodeBlockContainer;
import com.flai.ide.model.codeparsers.CodeParser.CodeBlockType;

/**
 * A code formatters that can format Java code. Adds indentation when enter is pressed etc
 * @author Jaakko
 */
class JavaCodeFormatter implements CodeFormatter {

	private final CodeParser _codeParser = CodeParser.create(ProgrammingLanguage.JAVA);
	// TODO: when closing brace is inputted, format that also properly
	
	@Override
	public String formatCode(String oldCode, String newCode, int caretPosition) {
		/* If the diff between oldCode and newCode is a newline ("\n"),
		 then add a proper indentatation  */

		TextInsert insert = StringHelper.isStringDifferenceAnInsertion(oldCode, newCode);
		if(insert != null) {
			if(insert.InsertedText.equals("\n")) {
				int indentationLevel = calculateIndentationAtIndex(newCode, caretPosition - 1);		
				newCode = setLineIndentation(newCode, caretPosition, indentationLevel);
				return newCode; 
			}
			else if(insert.InsertedText.equals("}")) {
				int indentationLevel = calculateIndentationAtIndex(newCode, insert.StartIndex);
				newCode = setLineIndentation(newCode, insert.StartIndex, indentationLevel);

				return newCode;
			}
		}

		return newCode;
	}

	/* "{" adds +1 to indentation, "}" -1, as long as they are not inside string or comment */
	private int calculateIndentationAtIndex(String code, int index) {
		assert index < code.length();

		// find amount of "OpeningBrace" code blocks substracted by "ClosingBrace" code blocks at 'index'
		CodeBlockContainer codeBlocks = _codeParser.parseCode(code);
		
		int indentationLevel = 0;
		for (CodeBlock block : codeBlocks) {
			if(block.StartIndex > index) {
				break;
			}
			
			if(block.Type == CodeBlockType.OpeningBrace) {
				indentationLevel++;
			}
			else if(block.Type == CodeBlockType.ClosingBrace) {
				indentationLevel--;
			}
		}
		
		return indentationLevel >= 0 ? indentationLevel : 0;
	}

	private String setLineIndentation(String code, int index, int indentationLevel) {
		// find which line 'index' is on
		int lineStartIndex = 0;
		int lineFirstNonWhitespaceIndex = 0;
		boolean hasFoundNonWhitespace = false;
		for(int i = 0; i < index; i++) {

			char c = code.charAt(i);
			if(c == '\n') {
				lineStartIndex = i + 1;
				lineFirstNonWhitespaceIndex = i + 1;
				hasFoundNonWhitespace = false;
			}
			else if(!hasFoundNonWhitespace && Character.isWhitespace(c)) {
				lineFirstNonWhitespaceIndex = i + 1;
			}
			else {
				hasFoundNonWhitespace = true;
			}
		}
		
		code = StringHelper.removeSubstring(code, lineStartIndex, lineFirstNonWhitespaceIndex - lineStartIndex);
		code = StringHelper.insert(code, lineStartIndex, StringHelper.repeat('\t', indentationLevel));
		return code;
	}
}
