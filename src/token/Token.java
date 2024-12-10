package token;

import java.util.Objects;

/**
 * Classe che rappresenta un token nel processo di analisi lessicale.
 */
public class Token {

    private int row;
    private TokenType type;
    private String value;

    /**
     * Costruttore per un token con valore associato.
     *
     * @param type  il tipo di token
     * @param row   la riga in cui si trova il token
     * @param value il valore del token
     */
    public Token(TokenType type, int row, String value) {
        this.type = type;
        this.row = row;
        this.value = value;
    }

    /**
     * Costruttore per un token senza valore associato.
     *
     * @param type il tipo di token
     * @param row  la riga in cui si trova il token
     */
    public Token(TokenType type, int row) {
        this.type = type;
        this.row = row;
    }

    /**
     * Restituisce il tipo del token.
     *
     * @return il tipo di token
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Imposta il tipo del token.
     *
     * @param type il nuovo tipo di token
     */
    public void setType(TokenType type) {
        this.type = type;
    }

    /**
     * Restituisce la riga in cui si trova il token.
     *
     * @return la riga del token
     */
    public int getRow() {
        return row;
    }

    /**
     * Restituisce il valore associato al token.
     *
     * @return il valore del token, oppure {@code null} se non presente
     */
    public String getValue() {
        return value;
    }

    /**
     * Restituisce una rappresentazione testuale del token.
     *
     * @return una stringa che rappresenta il token nel formato:
     *         {@code <TIPO,r:RIGA,VALORE>} oppure {@code <TIPO,r:RIGA>} se il valore è assente.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<").append(type).append(",r:").append(row);
        if (value != null) {
            builder.append(",").append(value);
        }
        builder.append(">");
        return builder.toString();
    }

	@Override
	public int hashCode() {
		return Objects.hash(row, type, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Token))
			return false;
		Token other = (Token) obj;
		return row == other.row && type == other.type && Objects.equals(value, other.value);
	}
    
    
    
}
