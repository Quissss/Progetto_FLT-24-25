package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.LexicalException;
import scanner.Scanner;
import token.Token;
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
	void testErroriNumbers() throws LexicalException, IOException {
		LexicalException e;
		Scanner scanner = new Scanner("src/test/data/TestScanner/erroriNumbers.txt");
		
		assertEquals("0", scanner.nextToken().getValue());
		assertEquals("33", scanner.nextToken().getValue());

		e = assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale alla riga 3: sequenza di caratteri '123.121212' non valida", e.getMessage());
		e = assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale alla riga 5: sequenza di caratteri '123.123' non valida", e.getMessage());
		assertEquals(TokenType.EOF, scanner.nextToken().getValue());
		
	}

	@Test
	void testId() throws IOException {
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
	
	@Test
	void testINT() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/data/TestScanner/testint.txt");

		assertEquals("0050", scanner.nextToken().getValue());
		assertEquals("698", scanner.nextToken().getValue());
		assertEquals("560099", scanner.nextToken().getValue());
		assertEquals("1234", scanner.nextToken().getValue());
		assertEquals(TokenType.EOF, scanner.nextToken().getType());		
	}
	
	@Test
	void testINTToken() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/data/TestScanner/testint.txt");
		Token t = new Token(TokenType.INT, 1 , "0050");
		Token t2 = new Token(TokenType.INT, 2 , "698");
		Token t3 = new Token(TokenType.INT, 4 , "560099");
		Token t4 = new Token(TokenType.INT, 5 , "1234");
		Token t5 = new Token(TokenType.EOF, 5 );
		
		assertEquals(t , scanner.nextToken());
		assertEquals(t2 , scanner.nextToken());
		assertEquals(t3 , scanner.nextToken());
		assertEquals(t4 , scanner.nextToken());
		assertEquals(t5 , scanner.nextToken());
	
	}
	
	@Test
	void testFLOAT() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/data/TestScanner/testFloat.txt");

		assertEquals("098.8095", scanner.nextToken().getValue());
		assertEquals("0.", scanner.nextToken().getValue());
		assertEquals("98.", scanner.nextToken().getValue());
		assertEquals("89.99999", scanner.nextToken().getValue());
		assertEquals(TokenType.EOF, scanner.nextToken().getType());		
	}
	
	@Test
	void testFLOATToken() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/data/TestScanner/testFloat.txt");
		Token t = new Token(TokenType.FLOAT, 1 , "098.8095");
		Token t2 = new Token(TokenType.FLOAT, 2 , "0.");
		Token t3 = new Token(TokenType.FLOAT, 3 , "98.");
		Token t4 = new Token(TokenType.FLOAT, 5 , "89.99999");
		Token t5 = new Token(TokenType.EOF, 5 );
		
		assertEquals(t , scanner.nextToken());
		assertEquals(t2 , scanner.nextToken());
		assertEquals(t3 , scanner.nextToken());
		assertEquals(t4 , scanner.nextToken());
		assertEquals(t5 , scanner.nextToken());
	}
	
	
	@Test
	void testOpsDels() throws LexicalException, IOException {
		Scanner scanner = new Scanner("src/test/data/TestScanner/testOpsDels.txt");
		
		assertEquals("<PLUS,r:1>", scanner.nextToken().toString());
		assertEquals("<OP_ASSIGN,r:1,/=>", scanner.nextToken().toString());
		
		assertEquals("<MINUS,r:2>", scanner.nextToken().toString());
		assertEquals("<TIMES,r:2>", scanner.nextToken().toString());
		
		assertEquals("<DIVIDE,r:3>", scanner.nextToken().toString());
		
		assertEquals("<OP_ASSIGN,r:5,+=>", scanner.nextToken().toString());
		
		assertEquals("<ASSIGN,r:6>", scanner.nextToken().toString());
		assertEquals("<OP_ASSIGN,r:6,-=>", scanner.nextToken().toString());
		
		assertEquals("<MINUS,r:8>", scanner.nextToken().toString());
		assertEquals("<ASSIGN,r:8>", scanner.nextToken().toString());
		assertEquals("<OP_ASSIGN,r:8,*=>", scanner.nextToken().toString());
		
		assertEquals("<SEMI,r:10>", scanner.nextToken().toString());
		assertEquals("<EOF,r:10>", scanner.nextToken().toString());
		
	}
	
}
