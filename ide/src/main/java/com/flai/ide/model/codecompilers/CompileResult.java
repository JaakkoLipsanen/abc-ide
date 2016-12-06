/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model.codecompilers;

/**
 * The result of of the compilation
 * @author Jaakko
 */
public final class CompileResult {

	public final CompileStatus CompileStatus;
	public final String Message; // contains errors, warnings, whatnot

	public final ProgramInfo ProgramInfo;

	public CompileResult(CompileStatus status, ProgramInfo programInfo) {
		this(status, "", programInfo);
	}

	public CompileResult(CompileStatus status, String message) {
		this(status, message, null);
	}

	public CompileResult(CompileStatus status, String message, ProgramInfo programInfo) {
		this.CompileStatus = status;
		this.Message = message;
		this.ProgramInfo = programInfo;
	}
	
	/**
	 * Enum holding the status of the compilation
	 */
	public static enum CompileStatus {
		OK,
		WRONG_JAVA_SETUP,
		COMPILE_ERROR,
	}
}
