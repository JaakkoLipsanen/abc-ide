/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model.codecompilers;

import com.flai.ide.MiscHelper;
import com.flai.ide.StringHelper;
import com.flai.ide.model.codecompilers.CompileResult.CompileStatus;
import com.flai.ide.model.codecompilers.ProgramInfo.InputStreamListener;
import com.flai.ide.model.codecompilers.ProgramInfo.OutputStreamBroadcaster;
import com.flai.ide.model.codecompilers.ProgramInfo.ProgramRunner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * CodeCompiler for Java
 * @author Jaakko
 */
class JavaCodeCompiler implements CodeCompiler {
	private static final String FILE_NAME = "Snippet";
	
	@Override
	public CompileResult compileCode(String code) {
		File codeFile = createTempFile(code);
	
		File javac = getJavaExecutable("javac");
		File java = getJavaExecutable("java");
		
		if(!javac.exists() || !java.exists()) {
			return new CompileResult(CompileStatus.WRONG_JAVA_SETUP, "Couldn't find java.exe or javac.exe");
		}
		
		try {	
			Process compileProcess = new ProcessBuilder(javac.getAbsolutePath(), codeFile.getAbsolutePath()).start();
			String compileErrorMessage = MiscHelper.convertStreamToString(compileProcess.getErrorStream());
			
			compileProcess.waitFor();
			
			if(!StringHelper.isEmptyOrWhitespace(compileErrorMessage)) {
				return new CompileResult(CompileStatus.COMPILE_ERROR, compileErrorMessage);
			}
						
			return new CompileResult(CompileStatus.OK, new ProgramInfo(new ProgramRunner() {

				@Override
				public int run(InputStreamListener input, InputStreamListener error, OutputStreamBroadcaster output) {
					try {
						ProcessBuilder processBuilder = new ProcessBuilder("java", FILE_NAME);
						processBuilder.directory(codeFile.getParentFile());
				
						Process process = processBuilder.start();
						
						input.setStream(process.getInputStream());
						error.setStream(process.getErrorStream());
						output.setStream(process.getOutputStream());
						
						process.waitFor();
						return process.exitValue();
						
					} 
					catch (Exception ex) { return -1; }
				}
			}));
		}
		catch(Exception e) {
			return new CompileResult(CompileStatus.COMPILE_ERROR, "Unknown error: " + MiscHelper.exceptionToString(e), null);
		}
	}
	
	private File getJavaExecutable(String executableName) {
		String extension = MiscHelper.isWindowsOS() ? ".exe" : ""; // on linux, don't add any extension
        String path = System.getenv("JAVA_HOME") + "/bin/" + executableName;
		File file = new File(path + extension);
		
		return file;
	}
	
	private File createTempFile(String content) {
		try {
			File tempDir = new File(System.getProperty("java.io.tmpdir"));
			tempDir.mkdirs(); // make sure it exists
			
			File file = new File(tempDir.getAbsolutePath() + "/" + FILE_NAME + ".java");
			
			file.delete();
			file.createNewFile();
			file.deleteOnExit();
			
			try(PrintWriter writer = new PrintWriter(file)) {
				writer.print(content);
				
				return file;
			}
		} 
		catch (IOException ex) {
			return null;
		}
	}
}
