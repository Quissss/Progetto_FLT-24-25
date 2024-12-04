package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.HashSet;

import exception.LexicalException;
import token.*;

public class Scanner {

	final char EOF = (char) -1;
	private int riga;
	private PushbackReader buffer;
	private String log;
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
	/** mapping fra le stringhe "print", "float", "int" e il TokenType corrispondente */
	private HashMap<String, TokenType> keyWordsTkType; 

	/**
	 * Inizializza il costruttore
	 */
	public Scanner(String fileName) throws FileNotFoundException {

		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
		this.current = null;

		config();

	}

	/**
	 * Inizializza i campi dello scanner.
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
		operTkType.put('/', TokenType.DIV);

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

	public Token nextToken() throws LexicalException, IOException {

		if (current != null) {
			Token token = current;
			current = null;
			return token;
		}
		char nextChar;
		nextChar = peekChar();

		// Avanza nel buffer leggendo i carattere in skipChars
		// incrementando riga se leggi '\n'.
		// Se raggiungi la fine del file ritorna il Token EOF
		// Salta i caratteri di spazio e di fine linea
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

		// Se nextChar e' in letters
		// return scanId()
		// che deve generare o un Token ID o parola chiave
		// Se il carattere è una lettera (ID o parola chiave)
		if (letters.contains(nextChar)) {
			return scanId(nextChar);
		}

		// Se nextChar e' o in operators oppure delimitatore
		// ritorna il Token associato con l'operatore o il delimitatore
		// Attenzione agli operatori di assegnamento!
		// Se il carattere è un operatore o delimitatore
		// Se nextChar è un operatore o un delimitatore
		// Se nextChar è un operatore o un delimitatore
	    if (operTkType.containsKey(nextChar)) {
	        return new Token(operTkType.get(nextChar), riga); // Restituisce il token associato all'operatore
	    }

	    if (delimTkType.containsKey(nextChar)) {
	        return new Token(delimTkType.get(nextChar), riga); // Restituisce il token associato al delimitatore
	    }

		// Se nextChar e' in numbers
		// return scanNumber()
		// che legge sia un intero che un float e ritorna il Token INUM o FNUM
		// i caratteri che leggete devono essere accumulati in una stringa
		// che verra' assegnata al campo valore del Token
		// Se il carattere è un numero (intero o float)
		if (digits.contains(nextChar)) {
			return scanNumber(nextChar);
		}

		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
		// eccezione lessicale dicendo la riga e il carattere che la hanno
		// provocata.

		throw new LexicalException(riga, nextChar);
	}

	private Token scanId(char firstChar) throws IOException {
	    StringBuilder idValue = new StringBuilder();
	    idValue.append(firstChar);

	    char nextChar = readChar();
	    while (letters.contains(nextChar) || digits.contains(nextChar)) {
	        idValue.append(nextChar);
	        nextChar = readChar();
	    }

	    // Se la stringa è una parola chiave, ritorna il tipo corrispondente
	    String id = idValue.toString();
	    if (keyWordsTkType.containsKey(id)) {
	        return new Token(keyWordsTkType.get(id), riga, id);
	    } else {
	        return new Token(TokenType.ID, riga, id);
	    }
	}

	private Token scanOperator(char nextChar) throws LexicalException {
	    // Se il carattere è un operatore
	    
	    throw new LexicalException(riga, nextChar);
	}

	private Token scanNumber(char firstChar) throws IOException {
	    StringBuilder numberValue = new StringBuilder();
	    numberValue.append(firstChar);

	    char nextChar = readChar();
	    boolean isFloat = false;

	    // Legge i numeri interi o i numeri con decimali
	    while (digits.contains(nextChar) || (nextChar == '.' && !isFloat)) {
	        if (nextChar == '.') {
	            isFloat = true;
	        }
	        numberValue.append(nextChar);
	        nextChar = readChar();
	    }

	    // Restituisci un token di tipo appropriato (INT o FLOAT)
	    if (isFloat) {
	        return new Token(TokenType.FLOAT, riga, numberValue.toString());
	    } else {
	        return new Token(TokenType.INT, riga, numberValue.toString());
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
