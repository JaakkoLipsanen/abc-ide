package com.flai.ide;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.flai.ide.model.CodeSnippet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jaakko
 */
public class CodeSnippetTest {
    
    private CodeSnippet _snippet;
    public CodeSnippetTest() {
    }
    
    @Before
    public void setUp() {
		_snippet = new CodeSnippet("123");
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void initialValueCorrect() {
         assertEquals(_snippet.getText(), "123");
     }
	 
	  @Test
     public void setTextWorks() {
		 _snippet.setText("321");
         assertEquals(_snippet.getText(), "321");
     }
}
