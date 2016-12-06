/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model.codecompilers;

import com.flai.ide.model.ProgrammingLanguage;

/**
 * An common interface for all CodeCompiler's
 * @author Jaakko
 */
public interface CodeCompiler {

	/**
	 * 
	 * @param compiles the code
	 * @return result of compilation
	 */
	CompileResult compileCode(String code);

	/**
	 * Creates an CodeCompiler for the given language
	 * @param language
	 * @return CodeCompiler
	 */
	public static CodeCompiler create(ProgrammingLanguage language) {
		switch (language) {
			case JAVA:
				return new JavaCodeCompiler();
		}

		throw new IllegalArgumentException("");
	}
}
