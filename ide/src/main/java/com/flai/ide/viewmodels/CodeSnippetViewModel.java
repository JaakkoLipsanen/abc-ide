package com.flai.ide.viewmodels;

import com.flai.ide.model.CodeFormatter;
import com.flai.ide.model.CodeSnippet;
import com.flai.ide.model.syntaxhighlight.CodeSyntaxProcessor;
import com.flai.ide.model.syntaxhighlight.CodeSyntaxProcessor.CodeBlockContainer;

/**
 *
 * @author Jaakko
 */
public class CodeSnippetViewModel {

	private final CodeSnippet _snippet;
	private final CodeFormatter _codeFormatter;
	private final CodeSyntaxProcessor _codeSyntaxProcessor;

	public CodeSnippetViewModel(CodeSnippet snippet) {
		_snippet = snippet;
		_codeFormatter = CodeFormatter.create(_snippet.getLanguage());
		_codeSyntaxProcessor = CodeSyntaxProcessor.create(_snippet.getLanguage());
	}

	public String getCode() {
		return _snippet.getText();
	}

	/**
	 * This function sets the new code AND processes it if needed. For example,
	 * when "\n" (aka newline) is inputted, the proper indentation/tabs will be
	 * added here
	 *
	 * @param newValue the value that is to be setted
	 * @param caretPosition The position where the cursor/caret was after the
	 * new code was inputted. TODO: this sucks, a better alternative would be
	 * that I'd make some kind of "Diff" class which would hold the precise
	 * differences between the old and new code. Atm this is needed, because
	 * it's impossible to find out where the newline was inputted if there are
	 * multiple newlines in a row
	 * @return the processed (aka possibly modified) version of the newValue
	 * parameter
	 */
	public String setAndProcessNewCode(String newCode, int caretPosition) { // HMMM!! Should I make "insertCode" function? It'd maybe be clearer!!
		if (!newCode.equals(_snippet.getText())) {
			String processedCode = _codeFormatter.processNewCode(_snippet.getText(), newCode, caretPosition);
			_snippet.setText(processedCode);

			/* todo: syntax highlighting? */
			return processedCode;
		}

		return _snippet.getText();
	}
	
	public CodeBlockContainer createSyntaxHighlighting(String code) {
		return _codeSyntaxProcessor.processCode(code);
	}

	/* TODO: create insertAndProcessNewCode(..) ?? */
}
