package com.flai.ide;

import java.util.Arrays;

/**
 * A collection of static helper methods relating to Strings
 *
 * @author Jaakko
 */
public class StringHelper {

	/**
	 * Returns whether the given string is null, empty or contains just whitespaces ('\t' '\n' ' ' etc)
	 */
	public static boolean isEmptyOrWhitespace(String s) { 
		return (s == null) || s.trim().equals("");
	}
	
	/**
	 * Repeats a character for n times
	 * @param c character to repeat
	 * @param n amount of repeats
	 * @return string containing character c repeated n times 
	 */
	public static String repeat(char c, int n) {
		char[] chars = new char[n];
		Arrays.fill(chars, c);

		return new String(chars);
	}

	/**
	 * Inserts a string into another string
	 * @param original the string to which the other string will be inserted
	 * @param index index where the other string will be inserted at
	 * @param stringToInsert the other string to be inserted
	 * @return a string containing the inserted string
	 */
	public static String insert(String original, int index, String stringToInsert) {
		String start = original.substring(0, index);
		String end = original.substring(index);

		return start + stringToInsert + end;
	}
	
	/**
	 * Removes a substring from a string
	 * @param original the input string
	 * @param startIndex the index in which the substring starts
	 * @param count length of substring
	 * @return string with the substring removed
	 */
	public static String removeSubstring(String original, int startIndex, int count) {
		assert original.length() >= startIndex + count;
		return original.substring(0, startIndex) + original.substring(startIndex + count);
	}

	/**
	 * If the difference between the two strings is a simple insertion (aka if
	 * newValue can be constructed by inserting some text to the oldValue), then
	 * this method returns an TextInsert object that contains the start index
	 * and the inserted text
	 *
	 * @param oldValue the old string
	 * @param newValue the new string
	 * @return a TextInsert object (containing the start index and the inserted
	 * text), OR NULL if the difference is not an simple insertion
	 */
	public static TextInsert isStringDifferenceAnInsertion(String oldValue, String newValue) {
		if (newValue.length() <= oldValue.length()) {
			return null;
		}

		int firstDifferentIndex = -1;
		int lastDifferentIndex = -1;

		int differentCharacterCount = 0;
		// find the first index/value that is different
		for (int i = 0; i - differentCharacterCount < oldValue.length(); i++) {
			if (oldValue.charAt(i - differentCharacterCount) != newValue.charAt(i)) {
				if (firstDifferentIndex == -1) {
					firstDifferentIndex = i;
					lastDifferentIndex = i;
				} 
				else if (lastDifferentIndex == i - 1) {
					lastDifferentIndex = i;
				} 
				else { // there is a gap somewhere -> not an insert but multiple inserts
					return null;
				}

				differentCharacterCount++;
			}
		}

		// if first different value was not found, then it means that the difference was inserted to the end of the old value
		if (firstDifferentIndex == -1) {
			firstDifferentIndex = oldValue.length();
			lastDifferentIndex = newValue.length() - 1;
		} // if the first value wasn't at the end of the string, BUT the last character of strings is different, then incorrect
		else if (newValue.length() != oldValue.length() + (lastDifferentIndex - firstDifferentIndex + 1)) {
			return null;
		}

		return new TextInsert(firstDifferentIndex, newValue.substring(firstDifferentIndex, lastDifferentIndex + 1));
	}

	/**
	 * A class that holds information about an insertion of a string
	 */
	public static class TextInsert {

		public final int StartIndex;
		public final int EndIndex;
		public final String InsertedText;

		public TextInsert(int startIndex, String insertedText) {
			this.StartIndex = startIndex;
			this.InsertedText = insertedText;

			this.EndIndex = this.StartIndex + this.InsertedText.length();
		}
	}
}
