package com.flai.ide;

import com.flai.ide.pages.EditorPage;
import com.flai.ide.pages.Page;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private Page _currentPage = createDefaultPage();

	/**
	 * Entry point of the program.
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("abc-ide");

		final Scene scene = _currentPage.createScene(primaryStage);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Page createDefaultPage() {
		return new EditorPage();
	}
}
