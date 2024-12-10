package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken {

	 @Test
	    void testCostruttore() {
	        Token token1 = new Token(TokenType.SEMI, 1);
	        Token token2 = new Token(TokenType.PLUS, 5);

	        assertEquals(TokenType.SEMI, token1.getType());
	        assertEquals(1, token1.getRow());
	        assertEquals(TokenType.PLUS, token2.getType());
	        assertEquals(5, token2.getRow());
	    }

	    @Test
	    void testCostruttoreValore() {
	        Token token1 = new Token(TokenType.INT, 5, "21");
	        Token token2 = new Token(TokenType.ID, 3, "z");

	        assertEquals(TokenType.INT, token1.getType());
	        assertEquals(5, token1.getRow());
	        assertEquals("21", token1.getValue());
	        assertEquals(TokenType.ID, token2.getType());
	        assertEquals(3, token2.getRow());
	        assertEquals("z", token2.getValue());
	    }

	    @Test
	    void testSetType() {
	        Token tk = new Token(TokenType.MINUS, 4);
	        assertEquals(TokenType.MINUS, tk.getType());

	        tk.setType(TokenType.PLUS);
	        assertEquals(TokenType.PLUS, tk.getType());
	    }

	    @Test
	    void testGetValueNull() {
	        Token tk = new Token(TokenType.DIVIDE, 6);
	        assertNull(tk.getValue());
	    }

	    @Test
	    void testToString() {
	        Token token1 = new Token(TokenType.SEMI, 1);
	        Token token2 = new Token(TokenType.ID, 2, "b");

	        assertEquals("<SEMI,r:1>", token1.toString());
	        assertEquals("<ID,r:2,b>", token2.toString());
	    }


	    @Test
	    void testDifferentRows() {
	        Token token1 = new Token(TokenType.ID, 1, "a");
	        Token token2 = new Token(TokenType.ID, 2, "a");

	        assertNotEquals(token1, token2);
	    }

}
