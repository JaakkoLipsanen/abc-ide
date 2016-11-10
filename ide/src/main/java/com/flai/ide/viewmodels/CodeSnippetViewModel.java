package com.flai.ide.viewmodels;

import com.flai.ide.model.CodeSnippet;

/**
 *
 * @author Jaakko
 */
public class CodeSnippetViewModel {
    private final CodeSnippet _snippet;
    
    public CodeSnippetViewModel(CodeSnippet snippet) {
        _snippet = snippet;
    }
	
	public String getCode() { return _snippet.getText(); }
	
	/**
	 * This function sets the new code AND parses it if needed.
	 * For example, when "\n" (aka newline) is inputted, the
	 * proper indentation/tabs will be added here
	 * @param newValue the value that is to be setted
	 * @return the parsed (aka possibly modified) version of the newValue parameter
	 */
	public String setAndParseNewCode(String newValue) {
		if(!newValue.equals(_snippet.getText())) {
			_snippet.setText(newValue);
			/* todo: if new line ("\n"), then add proper indentation */
			/* todo: syntax highlighting? */
		}
		
		return _snippet.getText();
	}
}
