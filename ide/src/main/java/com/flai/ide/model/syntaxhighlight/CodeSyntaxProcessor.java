/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flai.ide.model.syntaxhighlight;

import com.flai.ide.model.ProgrammingLanguage;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Jaakko
 */
public interface CodeSyntaxProcessor {
	CodeBlockContainer processCode(String code);
	
	public static CodeSyntaxProcessor create(ProgrammingLanguage language) {
		switch (language) {
			case JAVA:
				return new JavaSyntaxProcessor();
		}

		throw new IllegalArgumentException("");
	}
	
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
	
	public class CodeBlockContainer implements Iterable<CodeBlock> {
		private final CodeBlock[] _codeBlocks;
		public CodeBlockContainer(Collection<CodeBlock> blocks) {
			_codeBlocks = blocks.toArray(new CodeBlock[blocks.size()]);
		}
		
		public int getCount() {
			return _codeBlocks.length;
		}
		
		public CodeBlock get(int i) {
			assert i >= 0 && i < _codeBlocks.length;
			return _codeBlocks[i];
		}

		@Override
		public Iterator<CodeBlock> iterator() {
			return Arrays.stream(_codeBlocks).iterator();
		}
	}
	
	
	public class CodeBlock {
		public final int StartIndex;
		public final int EndIndex;
		
		public final CodeBlockType Type;
		public final Object Tag; // you can put some metadata here (like what specific keyword it is)
		
		public CodeBlock(int from, int to, CodeBlockType type) {
			this(from, to, type, null);
		}
		
		public CodeBlock(int from, int to, CodeBlockType type, Object tag) {
			this.StartIndex = from;
			this.EndIndex = to;
			
			this.Type = type;
			this.Tag = tag;
		}
	}
}
