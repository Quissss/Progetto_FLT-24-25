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
		case TYFLOAT, TYINT, ID, PRINT, EOF -> {
			parseDSs();
			match(TokenType.EOF);
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TYFLOAT, TYINT, ID, PRINT o EOF", tk.getType());
		}
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
			parseDcl();
			parseDSs();
		}

		// DSs -> Stm DSs
		case ID, PRINT -> {
			parseStm();
			parseDSs();
		}

		// DSs -> ϵ
		case EOF -> {

		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TYFLOAT, TYINT o ID, PRINT o EOF", tk.getType());
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
			parseTy();
			match(TokenType.ID);
			parseDclP();
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TYFLOAT , TYINT", tk.getType());
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
			parseExp();
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

		switch (tk.getType()) {
		// Stm -> id Op Exp ;
		case ID -> {

			match(TokenType.ID);
			parseOp();
			parseExp();
			match(TokenType.SEMI);

		}
		// Stm -> print id ;
		case PRINT -> {
			match(TokenType.PRINT);
			match(TokenType.ID);
			match(TokenType.SEMI);

		}
		default -> {
			throw new SyntacticException(tk.getRow(), "ID o PRINT", tk.getType());
		}
		}

	}

	private void parseExp() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Exp -> Tr ExpP
		case ID, FLOAT, INT -> {
			parseTr();
			parseExpP();
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "ID, FLOAT , INT", tk.getType());
		}
		}

	}

	private void parseExpP() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Exp -> + Tr ExpP
		case PLUS -> {
			match(TokenType.PLUS);
			parseTr();
			parseExpP();
		}
		// Exp -> - Tr ExpP
		case MINUS -> {
			match(TokenType.MINUS);
			parseTr();
			parseExpP();
		}
		// Exp -> ϵ
		case SEMI -> {

		}
		default -> {
			throw new SyntacticException(tk.getRow(), "PLUS o MINUS o SEMI", tk.getType());
		}
		}

	}

	private void parseTr() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}
		switch (tk.getType()) {
		// Tr -> Val TrP
		case ID, FLOAT, INT -> {
			parseVal();
			parseTrP();
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "ID, FLOAT, INT", tk.getType());
		}
		}

	}

	private void parseTrP() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}
		switch (tk.getType()) {
		// TrP -> * Val TrP
		case TIMES -> {
			match(TokenType.TIMES);
			parseVal();
			parseTrP();
		}
		// TrP -> / Val TrP
		case DIVIDE -> {
			match(TokenType.DIVIDE);
			parseVal();
			parseTrP();
		}
		// TrP -> ϵ
		case MINUS, PLUS, SEMI -> {

		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TIMES o DIVIDE o MINUS, PLUS, SEMI", tk.getType());
		}
		}

	}

	private void parseVal() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Val -> intVal
		case INT -> {
			match(TokenType.INT);
		}
		// Val -> floatVal
		case FLOAT -> {
			match(TokenType.FLOAT);
		}
		// Val -> id
		case ID -> {
			match(TokenType.ID);
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "INT o FLOAT o ID", tk.getType());
		}
		}
	}

	private void parseOp() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Op → =
		case ASSIGN -> {
			match(TokenType.ASSIGN);
		}
		// Op → opAss
		case OP_ASSIGN -> {
			match(TokenType.OP_ASSIGN);
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "ASSIGN O OP_ASSIGN", tk.getType());
		}
		}

	}
}
