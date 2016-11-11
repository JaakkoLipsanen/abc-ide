package com.flai.ide;

import com.flai.ide.StringHelper.TextInsert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jaakko
 */
public class StringHelperTest {
	
	public StringHelperTest() {
	}
	
	@Test
	public void textInsertConstructorWorks() {
		TextInsert insert = new TextInsert(-1, "123");
		assertTrue(insert.StartIndex == -1);
		assertTrue(insert.InsertedText == "123");
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest1() {
		String a = "123";
		String b = "12";
		
		assertTrue(StringHelper.isStringDifferenceAnInsertion(a, b) == null);
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest2() {
		String a = "123";
		String b = "123";
		
		assertTrue(StringHelper.isStringDifferenceAnInsertion(a, b) == null);
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest3() {
		String a = "1";
		String b = "12";
		
		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), new TextInsert(1, "2"));
	}

	@Test
	public void isStringDifferenceAnInsertionTest4() {
		String a = "123";
		String b = "1234567";
		
		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), new TextInsert(3, "4567"));
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest5() {
		String a = "123";
		String b = "1abc23";
		
		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), new TextInsert(1, "abc"));
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest6() {
		String a = "123";
		String b = "1abc2345";
		
		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), null);
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest7() {
		String a = "123";
		String b = "abc123";
		
		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), new TextInsert(0, "abc"));
	}
	
	
	@Test
	public void isStringDifferenceAnInsertionTest8() {
		String a = "123";
		String b = "abc1234";

		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), null);	
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest9() {
		String a = "123";
		String b = "12";

		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), null);	
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest10() {
		String a = "123";
		String b = "1a2b3c";

		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), null);	
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest11() {
		String a = "123";
		String b = "b123a";

		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), null);	
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest12() {
		String a = "123";
		String b = "123a";

		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), new TextInsert(3, "a"));	
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest13() {
		String a = "123";
		String b = "123ab";

		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), new TextInsert(3, "ab"));	
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest14() {
		String a = "123";
		String b = "c123ab";

		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), null);	
	}
	
	@Test
	public void isStringDifferenceAnInsertionTest15() {
		String a = "123";
		String b = "a123ab3";

		assertTextInsertsEquals(StringHelper.isStringDifferenceAnInsertion(a, b), null);	
	}
	
	private void assertTextInsertsEquals(TextInsert a, TextInsert b) {
		assertFalse((a == null && b != null) || (a != null && b == null) );
		assertTrue((a == b) || (a.StartIndex == b.StartIndex && a.InsertedText.equals(b.InsertedText)));
	}
}
