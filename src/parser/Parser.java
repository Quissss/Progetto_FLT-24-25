package parser;

import exception.LexicalException;
import exception.SyntacticException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {

	private Scanner scanner;

	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}

	private Token match(TokenType type) throws SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}
		if (type.equals(tk.getType()))
			try {
				return scanner.nextToken();
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
		else
			throw new SyntacticException(tk.getRow(), type, tk.getType());
	}

	public void parse() throws SyntacticException {
		this.parsePrg();
	}

	private void parsePrg() throws SyntacticException {
		Token tk = null;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Prg -> DSs $
		case TYFLOAT, TYINT, ID, PRINT, EOF -> { // Prg -> DSs $
			parseDSs();
			match(TokenType.EOF);
		}
		// default SyntacticException:
		// token tk alla riga tk.getRiga() non e’ inizio di programma
		}
	}

	private void parseDSs() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// DSs -> Dcl DSs
		case TYFLOAT, TYINT -> {

		}

		// DSs -> Stm DSs
		case ID, PRINT -> {

		}

		// DSs -> ϵ
		case EOF -> {

		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TYFLOAT, TYINT, ID, PRINT o EOF", tk.getType());
		}
		}

	}

	private void parseDcl() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Dcl -> Ty id DclP
		case TYFLOAT, TYINT -> {

		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TYFLOAT o TYINT", tk.getType());
		}
		}

	}

	private void parseTy() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Ty -> float
		case TYFLOAT -> {
			match(TokenType.TYFLOAT);

		}
		// Ty -> int
		case TYINT -> {
			match(TokenType.TYINT);

		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TYFLOAT o TYINT", tk.getType());
		}
		}

	}

	private void parseDclP() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// DclP -> ;
		case SEMI -> {
			match(TokenType.SEMI);

		}
		// DclP -> = Exp ;
		case ASSIGN -> {
			match(TokenType.ASSIGN);

			match(TokenType.SEMI);

		}
		default -> {
			throw new SyntacticException(tk.getRow(), "SEMI o OP_ASSIGN", tk.getType());
		}
		}

	}

	private void parseStm() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

	}

	private void parseExp() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

	}

	private void parseExpP() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

	}

	private void parseTr() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

	}

	private void parseTrP() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

	}

	private void parseVal() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

	}

	private void parseOp() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

	}
}
