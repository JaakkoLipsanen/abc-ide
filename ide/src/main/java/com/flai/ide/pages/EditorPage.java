package com.flai.ide.pages;

import com.flai.ide.view.CodeEditorControl;
import com.flai.ide.viewmodels.EditorViewModel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

/**
 * EditorPage is the main (and possibly will be the only) page of this
 * application. It displays the code editor
 *
 * @author Jaakko
 */
public class EditorPage implements Page {

	private final EditorViewModel _editorViewModel = new EditorViewModel();

	@Override
	public Scene createScene() {
		// creates the main content holder, which will hold all the elements in the page
		final BorderPane mainContentHolder = new BorderPane();

		mainContentHolder.setTop(createToolBar()); // sets the toolbar to at the top of the window
		mainContentHolder.setCenter(createCodeEditor()); // sets the code editor to not be attached to any border (top/left/down/right) of the window

		Scene scene = new Scene(mainContentHolder, 600, 800);
		scene.getStylesheets().add(EditorPage.class.getResource("/ide-styles.css").toExternalForm());
		
		return scene;
	}

	/**
	 * Creates the top toolbar (containing new, save, load, run etc buttons)
	 *
	 * @return the javafx.Node that contains the toolbar
	 */
	private Node createToolBar() {
		final Button testButton = new Button("Run");
		testButton.setOnAction(event -> {
			/* tadaa */
		});

		final ToolBar toolBar = new ToolBar(testButton);
		return toolBar;
	}

	/**
	 * Creates the text are that contains the code
	 *
	 * @return the javafx.Node that contains the toolbar
	 */
	private Node createCodeEditor() {
		final CodeEditorControl control = new CodeEditorControl(_editorViewModel);
		return control.createNode();
	}
}
