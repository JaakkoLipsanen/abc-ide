package com.flai.ide.model;

/**
 *
 * @author Jaakko
 */
public class CodeSnippet {
    private String _code;
	private ProgrammingLanguage _programmingLanguage;
	// todo: last modified date
	// todo: filepath?
    
    public CodeSnippet(String code, ProgrammingLanguage language) {
        _code = code;
		_programmingLanguage = language;
    }
    
    public String getText() { return _code; }
    public void setText(String value) { 
        _code = value;     
    }
	
	public ProgrammingLanguage getLanguage() {
		return _programmingLanguage;
	}
}
