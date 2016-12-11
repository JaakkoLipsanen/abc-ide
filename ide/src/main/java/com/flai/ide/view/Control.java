package com.flai.ide.view;

import javafx.scene.Node;

/**
 * Base class for all custom GUI controls.
 *
 * @author Jaakko
 */
public interface Control {

	/**
	 * Creates the javafx.Node of the Control.
	 * @return javafx.Node
	 */
	Node createNode();
}
