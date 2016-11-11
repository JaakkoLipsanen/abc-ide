package com.flai.ide;

import com.flai.ide.viewmodels.EditorViewModel;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jaakko
 */
public class EditorViewModelTest {
	
	private EditorViewModel _vm;
	public EditorViewModelTest() {
	}

	@Before
	public void setUp() {
		_vm = new EditorViewModel();
	}
	
	 @Test
	 public void initWorks() {
		 assertNotEquals(_vm.getCurrentCodeSnippet(), null);
	 }
}
