package exception;

import token.TokenType;

public class SyntacticException extends Exception {

	/**
	 * Costruttore per SyntacticException con riga, token atteso  e token ottenuto.
	 * 
	 * @param row   la riga dell'errore
	 * @param expected  il token atteso
	 * @param actual il token ottenuto
	 */
	public SyntacticException(int row, TokenType expected, TokenType actual) {
		super("Errore sintattico a riga " + row + ": atteso " + expected + ", ma è " + actual);
	}

	/**
	 * Costruttore per SyntacticException con messaggio.
	 * 
	 * @param msg
	 */
	public SyntacticException(String msg) {
		super(msg);
	}

}