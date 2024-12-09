package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.LexicalException;
import scanner.Scanner;
import token.TokenType;

class TestScanner {

	@Test
	void testSkipChars() throws IOException {
		Scanner scanner = new Scanner("src/test/data/TestScanner/caratteriSkip.txt");
		var token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(TokenType.EOF, token.getType());
		assertEquals(5, token.getRow());
	}

	@Test
	void testIds() throws IOException {
		Scanner scanner = new Scanner("src/test/data/TestScanner/testId.txt");

		var token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("jskjdsfhkjdshkf", token.getValue());
		assertEquals(1, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("printl", token.getValue());
		assertEquals(2, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("ffloat", token.getValue());
		assertEquals(4, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("hhhjj", token.getValue());
		assertEquals(6, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(7, token.getRow());
		assertEquals(TokenType.EOF, token.getType());
	}

	@Test
	void testKeywords() throws IOException {
		Scanner scanner = new Scanner("src/test/data/TestScanner/testIdKeyWords.txt");

		var token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(1, token.getRow());
		assertEquals(TokenType.TYINT, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("inta", token.getValue());
		assertEquals(1, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(2, token.getRow());
		assertEquals(TokenType.TYFLOAT, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(3, token.getRow());
		assertEquals(TokenType.PRINT, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("nome", token.getValue());
		assertEquals(4, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("intnome", token.getValue());
		assertEquals(5, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(6, token.getRow());
		assertEquals(TokenType.TYINT, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals("nome", token.getValue());
		assertEquals(6, token.getRow());
		assertEquals(TokenType.ID, token.getType());

		token = assertDoesNotThrow(scanner::nextToken);
		assertEquals(6, token.getRow());
		assertEquals(TokenType.EOF, token.getType());
	}
}
