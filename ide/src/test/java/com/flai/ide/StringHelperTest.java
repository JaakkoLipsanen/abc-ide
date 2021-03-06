package com.flai.ide;

import com.flai.ide.StringHelper.TextInsert;
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
		TextInsert insert = new TextInsert(0, "123");
		assertTrue(insert.StartIndex == 0);
		assertTrue(insert.InsertedText == "123");
		assertTrue(insert.EndIndex == 0 + "123".length());
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
	
	
	@Test
	public void repeatWorks() {
		assertEquals(StringHelper.repeat('a', 0), "");	
	}
	
	@Test
	public void repeatWorks2() {
		assertEquals(StringHelper.repeat('a', 1), "a");	
	}
	
	@Test
	public void repeatWorks3() {
		assertEquals(StringHelper.repeat('a', 10), "aaaaaaaaaa");	
	}
	
	@Test
	public void repeatWorks4() {
		assertEquals(StringHelper.repeat('\t', 3), "\t\t\t");	
	}
	
	
	@Test
	public void insertWorks1() {
		assertEquals(StringHelper.insert("a", 1, "b"), "ab");	
	}
	
	@Test
	public void insertWorks2() {
		assertEquals(StringHelper.insert("a", 0, "b"), "ba");	
	}
	
	@Test
	public void insertWorks3() {
		assertEquals(StringHelper.insert("abc", 2, "123"), "ab123c");	
	}
}
