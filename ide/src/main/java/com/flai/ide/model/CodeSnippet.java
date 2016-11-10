package com.flai.ide.model;

/**
 *
 * @author Jaakko
 */
public class CodeSnippet {
    private String _code;
	// todo: last modified date
	// todo: filepath?
	// todo: programming language?
    
    public CodeSnippet(String code) {
        _code = code;
    }
    
    public String getText() { return _code; }
    public void setText(String value) { 
        _code = value;     
    }
}
