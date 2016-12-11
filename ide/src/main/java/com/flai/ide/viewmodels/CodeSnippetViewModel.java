package com.flai.ide.viewmodels;

import com.flai.ide.model.codeformatters.CodeFormatter;
import com.flai.ide.model.CodeSnippet;
import com.flai.ide.model.codecompilers.CodeCompiler;
import com.flai.ide.model.codecompilers.CompileResult;
import com.flai.ide.model.codeparsers.CodeParser;
import com.flai.ide.model.codeparsers.CodeParser.CodeBlockContainer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * ViewModel for a CodeSnippet.
 * @author Jaakko
 */
public class CodeSnippetViewModel {

	private final CodeSnippet _snippet;
	
	private final CodeFormatter _codeFormatter;
	private final CodeParser _codeParser;
	private final CodeCompiler _codeCompiler;

	/**
	 * Constructor.
	 * @param snippet CodeSnippet from which to create the VM 
	 */
	public CodeSnippetViewModel(CodeSnippet snippet) {
		_snippet = snippet;
		
		_codeFormatter = CodeFormatter.create(_snippet.getLanguage());
		_codeParser = CodeParser.create(_snippet.getLanguage());
		_codeCompiler = CodeCompiler.create(_snippet.getLanguage());
	}

	public String getCode() {
		return _snippet.getText();
	}

	/**
	 * This function sets the new code AND formats/processes it if needed. For example,
	 * when "\n" (aka newline) is inputted, the proper indentation/tabs will be
	 * added here
	 *
	 * @param newCode the value that is to be setted
	 * @param caretPosition The position where the cursor/caret was after the
	 * new code was inputted. TODO: this sucks, a better alternative would be
	 * that I'd make some kind of "Diff" class which would hold the precise
	 * differences between the old and new code. Atm this is needed, because
	 * it's impossible to find out where the newline was inputted if there are
	 * multiple newlines in a row
	 * @return the processed (aka possibly modified) version of the newValue
	 * parameter
	 */
	public String setAndFormatNewCode(String newCode, int caretPosition) { // HMMM!! Should I make "insertCode" function? It'd maybe be clearer!!
		if (!newCode.equals(_snippet.getText())) {
			String processedCode = _codeFormatter.formatCode(_snippet.getText(), newCode, caretPosition);
			_snippet.setText(processedCode);

			return processedCode;
		}

		return _snippet.getText();
	}
	
	/**
	 * Parses the code into code blocks.
	 * @param code given code
	 * @return collection of code blocks
	 */
	public CodeBlockContainer parseCode(String code) { // meh that it takes the code as a parameter..
		return _codeParser.parseCode(code);
	}
	
	/**
	 * compiles the code.
	 * @return the result of the compilation
	 */
	public CompileResult compileCode() {
		return _codeCompiler.compileCode(_snippet.getText());
	}
	
	/**
	 * saves the code snippet to a file.
	 * @param file file to save the snippet into
	 * @return whether save was succesful or not
	 */
	public boolean saveToFile(File file) {
		try {
			Files.write(file.toPath(), _snippet.getText().getBytes());
			return true;
		} 
		catch (IOException ex) {
			return false;
		}
		
	}
}
