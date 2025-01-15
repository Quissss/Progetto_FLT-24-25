package test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import exception.SyntacticException;
import parser.Parser;
import scanner.Scanner;

class TestParser {

	@Test
	void testParserCorretto1() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserCorretto1.txt");
		Parser parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}

	@Test
	void testParserCorretto2() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserCorretto2.txt");
		Parser parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testParserEcc0() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserEcc_0.txt");
		Parser parser = new Parser(scanner);

		Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 1: atteso ASSIGN O OP_ASSIGN, ma è SEMI", e.getMessage());
	}

	@Test
	void testParserEcc1() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserEcc_1.txt");
		Parser parser = new Parser(scanner);

		Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso ID, FLOAT, INT, ma è TIMES", e.getMessage());
	}

	@Test
	void testParserEcc2() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserEcc_2.txt");
		Parser parser = new Parser(scanner);

		Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 3: atteso TYFLOAT, TYINT o ID, PRINT o EOF, ma è INT",
				e.getMessage());
	}

	@Test
	void testParserEcc3() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserEcc_3.txt");
		Parser parser = new Parser(scanner);

		Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso ASSIGN O OP_ASSIGN, ma è PLUS", e.getMessage());
	}

	@Test
	void testParserEcc4() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserEcc_4.txt");
		Parser parser = new Parser(scanner);

		Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso ID, ma è INT", e.getMessage());
	}

	@Test
	void testParserEcc5() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserEcc_5.txt");
		Parser parser = new Parser(scanner);

		Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 3: atteso ID, ma è INT", e.getMessage());
	}

	@Test
	void testParserEcc6() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserEcc_6.txt");
		Parser parser = new Parser(scanner);

		Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 3: atteso ID, ma è TYFLOAT", e.getMessage());
	}

	@Test
	void testParserEcc7() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserEcc_7.txt");
		Parser parser = new Parser(scanner);

		Exception e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso ID, ma è ASSIGN", e.getMessage());
	}
	
	@Test
	void testSoloDich() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testSoloDich.txt");
		Parser parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testSoloDichPrint() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testSoloDichPrint.txt");
		Parser parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testParserSenzaInit() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParserSenzaInit.txt");
		Parser parser = new Parser(scanner);

		var program=Assertions.assertDoesNotThrow(() -> parser.parse());
		
		assertEquals(program.toString(),"[Program:NodeDecl [id=NodeId [name=temp], type=INT, init=null]  NodePrint [id=NodeId [name=temp]]  NodeDecl [id=NodeId [name=temp1], type=FLOAT, init=null]  NodePrint [id=NodeId [name=temp1]]]");
		
	}
	
	@Test
	void testParser() throws FileNotFoundException, SyntacticException {
		Scanner scanner = new Scanner("src/test/data/TestParser/testParser.txt");
		Parser parser = new Parser(scanner);

		var program=Assertions.assertDoesNotThrow(() -> parser.parse());
		
		assertEquals(program.toString(),"[Program:NodeDecl [id=NodeId [name=temp], type=INT, init=null]  NodeAssign [id=NodeId [name=temp], expr=NodeBinOp [op=PLUS, left=NodeDeref [id=NodeId [name=temp]], right=NodeCost [value=7, type=INT]]]  NodeDecl [id=NodeId [name=temp1], type=FLOAT, init=null]  NodeAssign [id=NodeId [name=temp1], expr=NodeBinOp [op=PLUS, left=NodeCost [value=3, type=INT], right=NodeBinOp [op=MINUS, left=NodeBinOp [op=TIMES, left=NodeCost [value=7, type=INT], right=NodeCost [value=5, type=INT]], right=NodeBinOp [op=DIV, left=NodeCost [value=6, type=INT], right=NodeCost [value=4, type=INT]]]]]]");
		
	}
	
}
