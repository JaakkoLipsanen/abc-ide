package com.flai.ide.pages;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A common interface for all the page's of the application
 *
 * @author Jaakko
 */
public interface Page {

	Scene createScene(Stage stage);
}
