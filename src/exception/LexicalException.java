package exception;

public class LexicalException extends Exception {

    /**
     * Costruttore per creare un'eccezione lessicale con la sola riga.
     * 
     * @param row la riga in cui si è verificato l'errore
     */
    public LexicalException(int row) {
        super("Errore lessicale alla riga " + row + ": impossibile leggere il carattere");
    }

    /**
     * Costruttore per creare un'eccezione lessicale con la riga e il carattere
     * non valido.
     * 
     * @param row la riga in cui si è verificato l'errore
     * @param c il carattere che ha causato l'errore
     */
    public LexicalException(int row, char c) {
        super("Errore lessicale alla riga " + row + ": carattere non valido '" + c + "'");
    }

    /**
     * Costruttore per creare un'eccezione lessicale con la riga e l'identificatore
     * del token malformato.
     * 
     * @param row la riga in cui si è verificato l'errore
     * @param id la sequenza di caratteri che rappresenta il token non riconosciuto
     */
    public LexicalException(int row, String id) {
        super("Errore lessicale alla riga " + row + ": sequenza di caratteri '" + id + "' non valida");
    }

}
