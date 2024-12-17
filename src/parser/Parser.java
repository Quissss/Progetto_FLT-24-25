package parser;

import java.util.ArrayList;

import ast.LangOper;
import ast.LangType;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import ast.NodeStm;
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

	public NodeProgram parse() throws SyntacticException {
		return this.parsePrg();
	}

	private NodeProgram  parsePrg() throws SyntacticException {
		Token tk = null;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Prg -> DSs $
		case TYFLOAT, TYINT, ID, PRINT, EOF -> {
			ArrayList<NodeDecSt> prg = parseDSs();
			parseDSs();
			match(TokenType.EOF);
			return new NodeProgram(prg);
		}
		default -> {
			match(TokenType.SEMI);
			parse();
			throw new SyntacticException(tk.getRow(), "TYFLOAT, TYINT, ID, PRINT o EOF", tk.getType());
		}
		}
	}

	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// DSs -> Dcl DSs
		case TYFLOAT, TYINT -> {
			NodeDecl decl= parseDcl();
			ArrayList<NodeDecSt> decSts = parseDSs();
			decSts.add(0, decl);
			return decSts;
		}

		// DSs -> Stm DSs
		case ID, PRINT -> {
			NodeStm stm = parseStm();
			ArrayList<NodeDecSt> decSts = parseDSs();
			decSts.add(0, stm);
			return decSts;
		}

		// DSs -> ϵ
		case EOF -> {
			return new ArrayList<NodeDecSt>();
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TYFLOAT, TYINT o ID, PRINT o EOF", tk.getType());
		}
		}

	}

	private NodeDecl  parseDcl() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Dcl -> Ty id DclP
		case TYFLOAT, TYINT -> {
			LangType ty = parseTy();
			NodeId id = new NodeId(match(TokenType.ID).getValue());
			NodeExpr dclP = parseDclP();
			return new NodeDecl(id, ty, dclP);
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TYFLOAT , TYINT", tk.getType());
		}
		}

	}

	private LangType  parseTy() throws SyntacticException {
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
			return LangType.FLOAT;
		}
		// Ty -> int
		case TYINT -> {
			match(TokenType.TYINT);
			return LangType.INT;
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TYFLOAT o TYINT", tk.getType());
		}
		}

	}

	private NodeExpr parseDclP() throws SyntacticException {
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
			return null;
		}
		// DclP -> = Exp ;
		case ASSIGN -> {
			match(TokenType.ASSIGN);
			NodeExpr exp = parseExp();
			match(TokenType.SEMI);
			return exp;
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "SEMI o OP_ASSIGN", tk.getType());
		}
		}

	}

	private NodeStm parseStm() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Stm -> id Op Exp ;
		case ID -> {

			NodeId id = new NodeId(match(TokenType.ID).getValue());
			parseOp();
			NodeExpr exp = parseExp();   //da finire
			match(TokenType.SEMI);
			return new NodeAssign(id, exp);
		}
		// Stm -> print id ;
		case PRINT -> {
			match(TokenType.PRINT);
			NodeId id = new NodeId(match(TokenType.ID).getValue());
			match(TokenType.SEMI);
			return new NodePrint(id);
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "ID o PRINT", tk.getType());
		}
		}

	}

	private NodeExpr parseExp() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Exp -> Tr ExpP
		case ID, FLOAT, INT -> {
			NodeExpr tr = parseTr();
			NodeExpr expP = parseExpP(tr);
			return expP;
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "ID, FLOAT , INT", tk.getType());
		}
		}

	}

	private NodeExpr parseExpP(NodeExpr left) throws SyntacticException {
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
			NodeExpr tr = parseTr();
			NodeExpr expP = parseExpP(tr);
			return new NodeBinOp(LangOper.PLUS, left, expP);
		}
		// Exp -> - Tr ExpP
		case MINUS -> {
			match(TokenType.MINUS);
			NodeExpr tr = parseTr();
			NodeExpr expP = parseExpP(tr);
			return new NodeBinOp(LangOper.MINUS, left, expP);
		}
		// Exp -> ϵ
		case SEMI -> {
			return left;
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "PLUS o MINUS o SEMI", tk.getType());
		}
		}

	}

	private NodeExpr parseTr() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}
		switch (tk.getType()) {
		// Tr -> Val TrP
		case ID, FLOAT, INT -> {
			NodeExpr val = parseVal();
			NodeExpr trP = parseTrP(val);
			return trP;
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "ID, FLOAT, INT", tk.getType());
		}
		}

	}

	private NodeExpr  parseTrP(NodeExpr left) throws SyntacticException {
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
			NodeExpr val = parseVal();
			NodeExpr trP = parseTrP(val);
			return new NodeBinOp(LangOper.TIMES, left, trP);
		}
		// TrP -> / Val TrP
		case DIVIDE -> {
			match(TokenType.DIVIDE);
			NodeExpr val = parseVal();
			NodeExpr trP = parseTrP(val);
			return new NodeBinOp(LangOper.DIV, left, trP);
		}
		// TrP -> ϵ
		case MINUS, PLUS, SEMI -> {
			return left;
		}
		default -> {
			throw new SyntacticException(tk.getRow(), "TIMES o DIVIDE o MINUS, PLUS, SEMI", tk.getType());
		}
		}

	}

	private NodeExpr  parseVal() throws SyntacticException {
		Token tk;

		try {
			tk = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (tk.getType()) {
		// Val -> intVal
		case INT -> {
			String intVal = match(TokenType.INT).getValue();
			return new NodeCost(intVal, LangType.INT);
		}
		// Val -> floatVal
		case FLOAT -> {
			String floatVal = match(TokenType.FLOAT).getValue();
			return new NodeCost(floatVal, LangType.FLOAT);
		}
		// Val -> id
		case ID -> {
			String id = match(TokenType.ID).getValue();
			return new NodeDeref(new NodeId(id));
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
