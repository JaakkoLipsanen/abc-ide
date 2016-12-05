/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model.codecompilers;

import com.flai.ide.model.ProgrammingLanguage;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Jaakko
 */
public interface CodeCompiler {

	CompileResult compileCode(String code);

	public static CodeCompiler create(ProgrammingLanguage language) {
		switch (language) {
			case JAVA:
				return new JavaCodeCompiler();
		}

		throw new IllegalArgumentException("");
	}
}
