/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model.codeparsers;

import com.flai.ide.model.ProgrammingLanguage;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * An common interface for all code parsers.
 * parseCode method parses the code and returns a collection of CodeBlock's
 * @author Jaakko
 */
public interface CodeParser {
	
	/**
	 * Parses the code.
	 * @param code code to parse
	 * @return parsed code
	 */
	CodeBlockContainer parseCode(String code);
	
	/**
	 * Creates CodeParser for the given language.
	 * @param language language from which to create the CodeParsers
	 * @return created CodeParser
	 */
	public static CodeParser create(ProgrammingLanguage language) {
		switch (language) {
			case JAVA:
				return new JavaCodeParser();
		}

		throw new IllegalArgumentException("");
	}
	
	/**
	 * Type of code block.
	 */
	public enum CodeBlockType {
		Keyword,
		
		SingleLineComment,
		MultiLineComment,
		
		NumberLiteral,
		CharLiteral,
		StringLiteral,
		
		OpeningBrace, 
		ClosingBrace, 
		
		Other, // everything else
		
		// local variable
		// parameter variable
		// type
		// member variable
		// method name
		// class name
		// ^^ these require actual parsing etc and that too hc for this project
	}
	
	/**
	 * An collection of CodeBlocks.
	 */
	public class CodeBlockContainer implements Iterable<CodeBlock> {
		private final CodeBlock[] _codeBlocks;
		
		/**
		 * Constructor.
		 * @param blocks CodeBlock collection 
		 */
		public CodeBlockContainer(Collection<CodeBlock> blocks) {
			_codeBlocks = blocks.toArray(new CodeBlock[blocks.size()]);
		}
		
		/**
		 * Returns count of the code blocks in the container.
		 * @return Returns count of the code blocks in the container
		 */
		public int getCount() {
			return _codeBlocks.length;
		}
		
		/**
		 * Gets CodeBlock from the given index.
		 * @param i the index
		 * @return CodeBlock from index i
		 */
		public CodeBlock get(int i) {
			assert i >= 0 && i < _codeBlocks.length;
			return _codeBlocks[i];
		}

		@Override
		public Iterator<CodeBlock> iterator() {
			return Arrays.stream(_codeBlocks).iterator();
		}
	}
		
	/**
	 * Class representing an code block. Has the type and start & end indices.
	 */
	public class CodeBlock {
		public final int StartIndex;
		public final int EndIndex;
		
		public final CodeBlockType Type;
		public final Object Tag; // you can put some metadata here (like what specific keyword it is)
		
		/**
		 * Constructor.
		 * @param from start index of the CodeBlock
		 * @param to end index of the block
		 * @param type type of the block
		 */
		public CodeBlock(int from, int to, CodeBlockType type) {
			this(from, to, type, null);
		}
		
		/**
		 * Constructor.
		 * @param from start index of the CodeBlock
		 * @param to end index of the block
		 * @param type type of the block
		 * @param tag tag for the block, can contain some extra data
		 */
		public CodeBlock(int from, int to, CodeBlockType type, Object tag) {
			this.StartIndex = from;
			this.EndIndex = to;
			
			this.Type = type;
			this.Tag = tag;
		}
	}
}
