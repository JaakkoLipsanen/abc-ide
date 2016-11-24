/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model;

import com.flai.ide.StringHelper;
import com.flai.ide.StringHelper.TextInsert;
import com.flai.ide.model.syntaxhighlight.CodeSyntaxProcessor;
import com.flai.ide.model.syntaxhighlight.CodeSyntaxProcessor.CodeBlock;
import com.flai.ide.model.syntaxhighlight.CodeSyntaxProcessor.CodeBlockContainer;
import com.flai.ide.model.syntaxhighlight.CodeSyntaxProcessor.CodeBlockType;

/**
 *
 * @author Jaakko
 */
class JavaCodeFormatter implements CodeFormatter {

	private final CodeSyntaxProcessor _syntaxProcessor = CodeSyntaxProcessor.create(ProgrammingLanguage.JAVA);
	// TODO: when closing brace is inputted, format that also properly
	
	@Override
	public String processNewCode(String oldCode, String newCode, int caretPosition) {
		/* If the diff between oldCode and newCode is a newline ("\n"),
		 then add a proper indentatation  */

		TextInsert insert = StringHelper.isStringDifferenceAnInsertion(oldCode, newCode);
		if (insert != null && insert.InsertedText.equals("\n")) { // TODO: it could be "\n\r" or "   \n" or something maybe also?

			int indentationLevel = calculateIndentationAtIndex(newCode, insert.StartIndex - 1);
			return StringHelper.insert(newCode,
					caretPosition,
					StringHelper.repeat('\t', indentationLevel));
		}

		return newCode;
	}

	/* "{" adds +1 to indentation, "}" -1, as long as they are not inside string or comment */
	private int calculateIndentationAtIndex(String code, int index) {
		assert index < code.length();

		// find amount of "OpeningBrace" code blocks substracted by "ClosingBrace" code blocks at 'index'
		CodeBlockContainer codeBlocks = _syntaxProcessor.processCode(code);
		
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
}
