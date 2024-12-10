package exception;

import token.TokenType;

public class SyntacticException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	
	/**
	 * Costruttore per SyntacticException con riga, string atteso  e token ottenuto.
	 * 
	 * @param row la riga dell'errore
	 * @param string il token atteso
	 * @param type il token 
	 */
	public SyntacticException(int row, String string, TokenType type) {
		super("Errore sintattico a riga " + row + ": atteso " + string + ", ma è " + type);
	}


}