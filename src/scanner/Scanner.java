package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.HashSet;

import exception.LexicalException;
import token.*;



/**
 * La classe Scanner si occupa di scansionare un file di input carattere per carattere
 * per generare i relativi token che saranno utilizzati da un parser. Gli operatori,
 * le parole chiave, gli identificatori e i numeri vengono riconosciuti e restituiti
 * come token corrispondenti.
 */
public class Scanner {

	final char EOF = (char) -1;
	private int riga;
	private PushbackReader buffer;
	private Token current;

	/** insieme di caratteri non considerati dallo scanner (incluso EOF) */
	public HashSet<Character> skpChars;
	/** insieme di lettere dell'alfabeto */
	public HashSet<Character> letters;
	/** insieme di cifre */
	public HashSet<Character> digits;

	/** mapping fra caratteri '+', '-', '*', '/' e il TokenType corrispondente */
	private HashMap<Character, TokenType> operTkType;
	/** mapping fra caratteri '=', ';' e il e il TokenType corrispondente */
	private HashMap<Character, TokenType> delimTkType;
	/**
	 * mapping fra le stringhe "print", "float", "int" e il TokenType corrispondente
	 */
	private HashMap<String, TokenType> keyWordsTkType;

	/**
     * Costruttore della classe Scanner. Inizializza il lettore del file e configura
     * i set e le mappe necessarie per il riconoscimento dei token.
     * 
     * @param fileName il nome del file da scansionare
     * @throws FileNotFoundException se il file non può essere trovato
     */
	public Scanner(String fileName) throws FileNotFoundException {

		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
		this.current = null;

		config();

	}

	/**
     * Inizializza i set e le mappe per lo scanner. Definisce i caratteri da saltare,
     * i caratteri alfabetici e numerici, i token per gli operatori, i delimitatori e 
     * le parole chiave.
     */
	private void config() {
		/* Inizializza il set skpChars */
		skpChars = new HashSet<Character>();
		skpChars.add(' ');
		skpChars.add('\n');
		skpChars.add('\t');
		skpChars.add('\r');
		skpChars.add(EOF);

		/* Inizializza il set letters */
		letters = new HashSet<Character>();
		for (char c = 'a'; c <= 'z'; c++) {
			letters.add(c);
		}

		/* Inizializza il set digits */
		digits = new HashSet<Character>();
		for (char c = '0'; c <= '9'; c++) {
			digits.add(c);
		}

		/* Inizializza l'hashmap operTkType */
		operTkType = new HashMap<Character, TokenType>();
		operTkType.put('+', TokenType.PLUS);
		operTkType.put('-', TokenType.MINUS);
		operTkType.put('*', TokenType.TIMES);
		operTkType.put('/', TokenType.DIVIDE);

		/* Inizializza l'hashmap delimTkType */
		delimTkType = new HashMap<Character, TokenType>();
		delimTkType.put('=', TokenType.ASSIGN);
		delimTkType.put(';', TokenType.SEMI);

		/* Inizializzazione dell'hashmap keyWordsTkType */
		keyWordsTkType = new HashMap<String, TokenType>();
		keyWordsTkType.put("print", TokenType.PRINT);
		keyWordsTkType.put("float", TokenType.TYFLOAT);
		keyWordsTkType.put("int", TokenType.TYINT);
	}

	/**
     * Ritorna il prossimo token nel file di input, scansionando il file carattere per carattere.
     * Gestisce gli spazi, le parole chiave, gli identificatori, gli operatori, i numeri
     * e i delimitatori.
     * 
     * @return il prossimo token trovato
     * @throws LexicalException se il token non è valido
     * @throws IOException se si verifica un errore di I/O durante la lettura del file
     */
	public Token nextToken() throws LexicalException, IOException {

		if (current != null) {
			Token token = current;
			current = null;
			return token;
		}
		char nextChar = peekChar();

		 // Salta i caratteri non rilevanti (spazi, tabulazioni, ritorni a capo)
		while (skpChars.contains(nextChar)) {
			if (nextChar == '\n') {
				riga++; // Incrementa la riga se trovi un ritorno a capo
			}
			nextChar = readChar(); // Leggi il prossimo carattere
		}
		// Se raggiungi la fine del file, ritorna un Token EOF
		if (nextChar == EOF) {
			return new Token(TokenType.EOF, riga);
		}

		// Scansiona gli identificatori o le parole chiave
		if (letters.contains(nextChar)) {
			return scanId();
		}
		
		// Scansiona gli operatori
		if (operTkType.containsKey(nextChar)) {
			return scanOperator();
		}

		if (delimTkType.containsKey(nextChar)) {
			return new Token(delimTkType.get(nextChar), riga); // Restituisce il token associato al delimitatore
		}

		// Scansiona i numeri (interi o decimali)
		if (digits.contains(nextChar)) {
			return scanNumber();
		}

		throw new LexicalException(riga, nextChar);
	}

	
	/**
     * Scansiona un identificatore o una parola chiave.
     * Un identificatore può essere una parola chiave (come "int", "float", "print")
     * o un identificatore generico.
     * 
     * @return il token identificatore o parola chiave
     * @throws IOException se si verifica un errore di lettura
     */
	private Token scanId() throws IOException {
		StringBuilder idValue = new StringBuilder();
		char nextChar = readChar();
		idValue.append(nextChar);

		nextChar = readChar();
		while (letters.contains(nextChar) || digits.contains(nextChar)) {
			idValue.append(nextChar);
			nextChar = readChar();
		}

		buffer.unread(nextChar);
		String id = idValue.toString();
		if (keyWordsTkType.containsKey(id)) {
			return new Token(keyWordsTkType.get(id), riga, id); // Parola chiave
		} else {
			return new Token(TokenType.ID, riga, id); // Identificatore generico
		}
	}
	
	/**
     * Scansiona un operatore. Gli operatori riconosciuti includono +, -, *, / e ":=".
     * 
     * @return il token dell'operatore trovato
     * @throws IOException se si verifica un errore di lettura
     * @throws LexicalException se l'operatore non è valido
     */
	private Token scanOperator() throws IOException, LexicalException {
	    char opChar = readChar(); // Leggi il carattere dell'operatore

	    // Se è un operatore base come +, -, *, /, ritorna il relativo token
	    TokenType type = operTkType.get(opChar);
	    if (type != null) {
	        return new Token(type, riga);
	    }

	    // Gestione dell'operatore di assegnamento ":=" (esempio di operatore composto)
	    if (opChar == ':' && peekChar() == '=') {
	        readChar(); // Leggi il secondo carattere '='
	        return new Token(TokenType.OP_ASSIGN, riga);
	    }

	    // Se il carattere non è un operatore valido, lancia un'eccezione
	    throw new LexicalException(riga, opChar);
	}


	/**
     * Scansiona un numero intero o decimale.
     * I numeri possono essere interi (es. 123) o decimali (es. 12.34).
     * 
     * @return il token del numero trovato
     * @throws IOException se si verifica un errore di lettura
     * @throws LexicalException se il numero non è valido
     */
	private Token scanNumber() throws IOException, LexicalException {
	    StringBuilder numberValue = new StringBuilder();
	    char nextChar = readChar();
	    numberValue.append(nextChar);

	    boolean isFloat = false;
	    nextChar = readChar();

	    // Continua a leggere caratteri numerici o un punto per decimali
	    while (digits.contains(nextChar) || (nextChar == '.' && !isFloat)) {
	        if (nextChar == '.') {
	            isFloat = true; // Flag per numeri decimali
	        }
	        numberValue.append(nextChar);
	        nextChar = readChar();
	    }

	    buffer.unread(nextChar);

	    String numberStr = numberValue.toString();
	    try {
	        if (isFloat) {
	            Float.parseFloat(numberStr);
	            return new Token(TokenType.FLOAT, riga, numberStr); // TokenType.FLOAT per numeri decimali
	        } else {
	            Integer.parseInt(numberStr);
	            return new Token(TokenType.INT, riga, numberStr); // TokenType.INT per numeri interi
	        }
	    } catch (NumberFormatException e) {
	        throw new LexicalException(riga, numberStr); // Se la conversione fallisce
	    }
	}


	/**
	 * Legge il prossimo carattere dal buffer di input.
	 * 
	 * @return il carattere successivo presente nel buffer
	 * @throws IOException se si verifica un errore durante la lettura del buffer
	 */
	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	/**
	 * Legge il prossimo carattere dal buffer di input senza rimuoverlo dal buffer.
	 * Questo consente di guardare il carattere successivo mantenendo il buffer
	 * invariato.
	 * 
	 * @return il carattere successivo presente nel buffer
	 * @throws IOException se si verifica un errore durante la lettura o
	 *                     l'annullamento della lettura
	 */
	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}

}
