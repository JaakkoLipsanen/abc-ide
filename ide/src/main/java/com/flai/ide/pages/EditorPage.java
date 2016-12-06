package com.flai.ide.pages;

import com.flai.ide.model.codecompilers.CompileResult;
import com.flai.ide.model.codecompilers.CompileResult.CompileStatus;
import com.flai.ide.view.CodeEditorControl;
import com.flai.ide.view.Console;
import com.flai.ide.viewmodels.EditorViewModel;
import java.io.File;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * EditorPage is the main (and possibly will be the only) page of this
 * application. It displays the code editor
 *
 * @author Jaakko
 */
public class EditorPage implements Page {
	
	private final EditorViewModel _editorViewModel = new EditorViewModel();
	private Console _console;
	private CodeEditorControl _codeEditor;

	@Override
	public Scene createScene(Stage stage) {
		// creates the main content holder, which will hold all the elements in the page
		final BorderPane mainContentHolder = new BorderPane();

		mainContentHolder.setCenter(createCodeEditor()); // sets the code editor to not be attached to any border (top/left/down/right) of the window
		mainContentHolder.setBottom(createConsole()); // sets the console to the bottom of the window
		mainContentHolder.setTop(createToolBar(stage)); // sets the toolbar to at the top of the window

		Scene scene = new Scene(mainContentHolder, 600, 800);
		scene.getStylesheets().add(EditorPage.class.getResource("/ide-styles.css").toExternalForm());
		
		return scene;
	}

	/**
	 * Creates the top toolbar (containing new, save, load, run etc buttons)
	 *
	 * @return the javafx.Node that contains the toolbar
	 */
	private Node createToolBar(Stage stage) {
		final Button runButton = new Button("Run");
		runButton.setOnAction(e -> {
			this.onRunButtonClicked();
		});
		
		final Button saveButton = new Button("Save");
		saveButton.setOnAction(e -> {
			this.onSaveButtonClicked(stage);
		});
		
		final Button loadButton = new Button("Load");
		loadButton.setOnAction(e -> {
			this.onLoadButtonClicked(stage);
		});
		
		final Button newButton = new Button("New");
		newButton.setOnAction(e -> {
			_editorViewModel.loadDefaultSnippet();
			_codeEditor.resetFromViewModel();
			
			_console.clearOutput();
		});
		
		// just an empty control that takes some space in the toolbar to create a gap
		HBox spacer = new HBox();
		spacer.setMinWidth(24);
		
		return new ToolBar(runButton, spacer, saveButton, loadButton, newButton);
	}

	/**
	 * Creates the text are that contains the code
	 *
	 * @return the javafx.Node that contains the toolbar
	 */
	private Node createCodeEditor() {
		_codeEditor = new CodeEditorControl(_editorViewModel);
		return _codeEditor.createNode();
	}
	
	private Node createConsole() {
		_console = new Console();
		return _console.createNode();
	}

	private void onRunButtonClicked() {
		CompileResult compileResult = _editorViewModel.getCurrentCodeSnippet().compileCode();
		if(compileResult.CompileStatus == CompileStatus.OK) {
			_console.attachIOStreamsToProgram(compileResult.ProgramInfo);

			compileResult.ProgramInfo.setProgramFinishedListener(exitCode -> {
				_console.detachIOStreams();
			});
			compileResult.ProgramInfo.run();
		}
		else {
			_console.setError(compileResult.Message);
		}
	}

	private void onSaveButtonClicked(Stage stage) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save code file");
		chooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Java files", "*.java")); // only .java files supported atm
		//	new FileChooser.ExtensionFilter("All files", "*.*"));

		File selectedFile = chooser.showSaveDialog(stage);
		if(selectedFile != null) {
			if(_editorViewModel.getCurrentCodeSnippet().saveToFile(selectedFile)) {
				// save succesful, yay
			}
			else {
				_console.setError("Error saving file");
			}
		}
	}

	private void onLoadButtonClicked(Stage stage) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open code file");
		chooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Java files", "*.java")); // only .java files supported atm
		//	new FileChooser.ExtensionFilter("All files", "*.*"));

		File selectedFile = chooser.showOpenDialog(stage);
		if(selectedFile != null && selectedFile.exists()) {
			if(_editorViewModel.loadSnippetFromFile(selectedFile)) {
				_codeEditor.resetFromViewModel();
				_console.clearOutput();
			}
			else {
				_console.setError("Error loading file");
			}
		}
	}
}
