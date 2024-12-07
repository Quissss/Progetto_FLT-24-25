package token;

/**
 * Rappresenta i tipi di token
 */
public enum TokenType {
    /**
     * Rappresenta un identificatore (nome di una variabile)
     */
    ID,
    
    /**
     * Valore di tipo intero
     */
    INT,
    
    /**
     * Valore di tipo float (massimo 5 decimali)
     */
    FLOAT,
    
    /**
     * Rappresenta la parola chiave {@code float}
     */
    TYFLOAT,
    
    /**
     * Rappresenta la parola chiave {@code int}
     */
    TYINT,
    
    /**
     * Rappresenta la parola chiave {@code print}
     */
    PRINT,
    
    /**
     * Token di assegnazione per il simbolo "="
     */
    ASSIGN,
    
    /**
     * Token di assegnazione per il simbolo "+=" "-=" "*=" "/="
     */
    OP_ASSIGN,
    
    /**
     * Token di assegnazione per il simbolo "+"
     */
    PLUS,
    
    /**
     * Token di assegnazione per il simbolo "-"
     */
    MINUS,
    
    /**
     * Token di assegnazione per il simbolo "*"
     */
    TIMES,
    
    /**
     * Token di assegnazione per il simbolo "/"
     */
    DIVIDE,
    
    /**
     * Token di assegnazione per il simbolo ";"
     */
    SEMI,
    
    /**
     * Token per rappresentare la fine del file
     */
    EOF;
}
