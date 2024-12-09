package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken {

	@Test
	void testCostruttore() {
		Token tk1 = new Token(TokenType.SEMI, 1);
		Token tk2 = new Token(TokenType.PLUS, 5);
		
		assertEquals(tk1.getType(), TokenType.SEMI);
		assertEquals(tk1.getRow(), 1);
		assertEquals(tk2.getType(), TokenType.PLUS);
		assertEquals(tk2.getRow(), 5);
	}
	
	@Test
	void testCostruttoreValore() {
		Token tk1 = new Token(TokenType.INT, 4, "19");
		Token tk2 = new Token(TokenType.ID, 2, "b");
		
		assertEquals(tk1.getType(), TokenType.INT);
		assertEquals(tk1.getRow(), 4);
		assertEquals(tk1.getValue(), "19");
		assertEquals(tk2.getType(), TokenType.ID);
		assertEquals(tk2.getRow(), 2);
		assertEquals(tk2.getValue(), "b");
	}
	
	@Test
	void testToString() {
		Token tk1 = new Token(TokenType.SEMI, 1);
		Token tk2 = new Token(TokenType.ID, 2, "b");
		
		assertEquals(tk1.toString(), "SEMI, riga: 1");
		assertEquals(tk2.toString(), "ID, riga: 2, b");
	}

}
