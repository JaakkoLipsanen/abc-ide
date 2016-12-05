/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model.codecompilers;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Jaakko
 */

public final class ProgramInfo {

	private final ProgramRunner _runner;

	private InputStreamListener _inputListener;
	private InputStreamListener _errorListener;
	private OutputStreamBroadcaster _outputBroadcaster;

	public ProgramInfo(ProgramRunner runner) {
		_runner = runner;
	}

	public void setOutputListener(InputStreamListener listener) {
		_inputListener = listener;
	}

	public void setErrorListener(InputStreamListener listener) {
		_errorListener = listener;
	}

	public void setInputBroadcaster(OutputStreamBroadcaster broadcaster) {
		_outputBroadcaster = broadcaster;
	}

	public void run() {
		new Thread() { // run the app in different thread so that the app doesn't freeze/get blocked

			@Override
			public void run() {
				_runner.run(_inputListener, _errorListener, _outputBroadcaster);
			}
		}.run();
	}
	
	public interface InputStreamListener {
		void setStream(InputStream stream);
	}

	public interface OutputStreamBroadcaster {
		void setStream(OutputStream stream);
	}

	public interface ProgramRunner {
		boolean run(InputStreamListener input, InputStreamListener error, OutputStreamBroadcaster output);
	}

}