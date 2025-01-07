package exception;

public class CodeGeneratorException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore per CodeGeneratorException con messaggio.
	 * 
	 * @param msg il messaggio dell'eccezione
	 */
	public CodeGeneratorException(String msg) {
		super(msg);
	}
	
}