/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.view;

import com.flai.ide.StringHelper;
import com.flai.ide.model.codecompilers.ProgramInfo;
import com.flai.ide.model.codecompilers.ProgramInfo.InputStreamListener;
import com.flai.ide.model.codecompilers.ProgramInfo.OutputStreamBroadcaster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

/**
 * An GUI control that contains the output and input of the running program
 * @author Jaakko
 */
public class Console implements Control {

	private TextArea _outputTextArea;
	private TextField _inputTextField;
	
	private ConsoleOutputStreamBroadcaster _programInput;
	private ConsoleInputStreamListener _programError;
	private ConsoleInputStreamListener _programOutput;
	
	private Timer _checkInputStreamDataTimer;
	
	@Override
	public Node createNode() {
		
		_outputTextArea = new TextArea();
		_outputTextArea.setEditable(false);
		
		_inputTextField = new TextField();	
		_inputTextField.setOnKeyPressed((keyEvent) -> {
			if(keyEvent.getCode().equals(KeyCode.ENTER)) {
				this.flushInput();
			}
		});
		
		_outputTextArea.getStyleClass().add("console-output");
		_inputTextField.getStyleClass().add("console-input");
		
		VBox box = new VBox(); // VBox == "vertical layout container"
		box.getChildren().addAll(_outputTextArea, _inputTextField);
		return box;
	}
	
	/**
	 * Sets the output of the console to the given error message
	 * @param errorMessage message to show
	 */
	public void setError(String errorMessage) {
		// todo: display in red or something?
		this.clearOutput();
		this.writeToOutput(errorMessage);
	}

	/**
	 * Attaches the stdin/stderr/stdout listeners to the given program
	 * @param programInfo program to attach listeners to
	 */
	public void attachIOStreamsToProgram(ProgramInfo programInfo) {
		this.clearOutput();
		this.detachIOStreams();

		_programOutput = new ConsoleInputStreamListener();
		_programError = new ConsoleInputStreamListener();
		_programInput = new ConsoleOutputStreamBroadcaster();
			
		programInfo.setOutputListener(_programOutput);
		programInfo.setErrorListener(_programError);
		programInfo.setInputBroadcaster(_programInput);

		final int TimerFrequency = 100; // in milliseconds, how often the code will be repeated
		_checkInputStreamDataTimer = new Timer();
		_checkInputStreamDataTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Console.this.getOutputFromStreams();
			}
		}, 0, TimerFrequency);
	}
	
	/**
	 * detaches the stdin/stdout/stderr listeners
	 */
	public void detachIOStreams() {
		getOutputFromStreams();
		
		if(_programOutput != null) {
			_programOutput.stopListening();
		}
		
		if(_programError != null) {
			_programError.stopListening();
		}
		
		if(_checkInputStreamDataTimer != null) {
			_checkInputStreamDataTimer.cancel();
			_checkInputStreamDataTimer = null;
		}
		
		_programInput = null;
	}
	
	private void getOutputFromStreams() {
		 Platform.runLater(() -> { // Platform.runLater == execute in the main/UI thread asap		
			 if(_programOutput != null) {
				String outputText = _programOutput.getNewText();
				if(!outputText.isEmpty()) {
					writeToOutput(outputText);
				}
			}

			if(_programError != null) {
				String errorText = _programError.getNewText();
				if(!errorText.isEmpty()) {
					writeToOutput(errorText); // writeToOutputError(..) ?
				}
			}
		 });
	}
	
	/**
	 * Clears the output console
	 */
	public void clearOutput() {
		_outputTextArea.clear();
	}
	
	private void writeToOutput(String text) {
		_outputTextArea.appendText(text);
	}
	
	private void writeLineToOutput(String text) {
		this.writeToOutput(text + "\n");
	}

	private void flushInput() {
		String inputText = _inputTextField.getText();
		if(!StringHelper.isEmptyOrWhitespace(inputText)) {
			this.writeLineToOutput("> " + inputText); // input is displayed as "> text" in the output
			_inputTextField.clear();

			if(_programInput != null) {
				_programInput.writeLine(inputText );
			}
		}
	}
	
	// from http://stackoverflow.com/a/22719262
	private static class ConsoleInputStreamListener extends Thread implements InputStreamListener {

		private StringBuffer _text = new StringBuffer();
		private BufferedReader _reader;
		
		private boolean _isRunning = false;
		
		@Override
		public void run() {
			try {
				int value = -1;
				while (_isRunning && (value = _reader.read()) != -1) {		
					synchronized(_text) {
						_text.append((char)value);
					}
				}
			} catch (IOException e) { }
		}

		@Override
		public void setStream(InputStream stream) {
			_reader = new BufferedReader(new InputStreamReader(stream));
			_isRunning = true;
			
			this.start();
		}
		
		public String getNewText() {
			synchronized(_text) {
				String text = _text.toString();
				_text.setLength(0); // clear _text
							
				return text;
			}
		}
		
		public void stopListening() {
			_isRunning = false;
		}
	}
	
	private static class ConsoleOutputStreamBroadcaster implements OutputStreamBroadcaster {
		private BufferedWriter _outputWriter;
		
		@Override
		public void setStream(OutputStream stream) {
			_outputWriter = new BufferedWriter(new OutputStreamWriter(stream));
		}
		
		public void write(String text) {
			try {
				_outputWriter.write(text);
				_outputWriter.flush();
			} catch (IOException e) { }
		}
		
		public void writeLine(String text) {
			this.write(text + "\n");
		}
	} 
}
