/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.view;

import com.flai.ide.StringHelper;
import com.flai.ide.model.codeparsers.CodeParser.CodeBlock;
import com.flai.ide.model.codeparsers.CodeParser.CodeBlockContainer;
import com.flai.ide.viewmodels.EditorViewModel;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import org.fxmisc.richtext.StyleClassedTextArea;

/**
 * This is the GUI control that modifies and displays the code/text (aka 95% of
 * the app)
 *
 * @author Jaakko
 */
public class CodeEditorControl implements Control {

	private final EditorViewModel _editor;

	/**
	 * StyleClassedTextArea is a text control that supports rich-text editing
	 * (needed for syntax coloring), from
	 * https://github.com/TomasMikula/RichTextFX
	 */
	private StyleClassedTextArea _codeTextControl;

	public CodeEditorControl(EditorViewModel editor) {
		_editor = editor;
	}

	@Override
	public Node createNode() {
		_codeTextControl = new StyleClassedTextArea();
		_codeTextControl.getStyleClass().add("code-area");
		
		// set the initial text to CodeSnippet.text (that is loaded from file/the default text that is generated)
		String initialText = _editor.getCurrentCodeSnippet().getCode();
		_codeTextControl.replaceText(initialText);
		updateSyntaxHighlighting();
		
		_codeTextControl.textProperty().addListener(this::onControlTextChanged);
		return _codeTextControl;
	}

	private boolean _isUpdatingProcessedCode = false;

	private void onControlTextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		if (_isUpdatingProcessedCode) {
			// okay.. if _isUpdatingParsedCode is true, then this call is not coming
			// because the user wrote new code, but rather because the user wrote new code AND THEN
			// the user written code was changed further by _editor.getCurrentCodeSnippet().setAndParseNewCode(newValue) call. So in this case,
			// lets not call the setAndParseNewCode again, which could possibly cause an infinite loop
			return;
		}

		String processedCode = _editor.getCurrentCodeSnippet().setAndFormatNewCode(newValue, _codeTextControl.getCaretPosition());
		if (!processedCode.equals(newValue)) {
			_isUpdatingProcessedCode = true;

			StringHelper.TextInsert insert = StringHelper.isStringDifferenceAnInsertion(newValue, processedCode);
			if (insert != null) { // if insert is null, then the modification was not a simple insert, so we cant use the insertText call
				// insertText call keeps the caret/cursor position, where as replaceText does not
				_codeTextControl.insertText(insert.StartIndex, insert.InsertedText);
			} 
			else {
				_codeTextControl.replaceText(newValue);
			}

			_isUpdatingProcessedCode = false;
		}
		
		updateSyntaxHighlighting();
	}
	
	private void updateSyntaxHighlighting() {
		String text = _codeTextControl.getText();
		CodeBlockContainer codeBlocks = _editor.getCurrentCodeSnippet().parseCode(text);
		
		_codeTextControl.clearStyle(0, text.length());
		for(CodeBlock block : codeBlocks) {
			_codeTextControl.setStyleClass(block.StartIndex, block.EndIndex, block.Type.name());
		}
	}
}
