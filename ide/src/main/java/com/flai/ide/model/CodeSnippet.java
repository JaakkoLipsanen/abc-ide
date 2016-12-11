package com.flai.ide.model;

/**
 * A class that holds information about a code snippet/file.
 * @author Jaakko
 */
public class CodeSnippet {

	private String _code;
	private ProgrammingLanguage _programmingLanguage;
	// todo: file path?
	// todo: last modified date

	/**
	 * Constructor of CodeSnippet.
	 * @param code the code in the snippet
	 * @param language language of the snippet
	 */
	public CodeSnippet(String code, ProgrammingLanguage language) {
		_code = code;
		_programmingLanguage = language;
	}

	public String getText() {
		return _code;
	}

	public void setText(String value) {
		_code = value;
	}

	public ProgrammingLanguage getLanguage() {
		return _programmingLanguage;
	}
}
