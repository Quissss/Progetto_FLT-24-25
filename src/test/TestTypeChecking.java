package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import exception.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import visitor.TypeCheckingVisitor;

class TestTypeChecking {

	@Test
	void testDicRipetute() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/1_dicRipetute.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		
		/* errore dichiarazione ripetuta */
		assertEquals(tcVisit.getLog(), "Errore semantico a riga 1: a già dichiarato.\n");
	}
	
	
	@Test
	void testIdNonDec() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/2_idNonDec.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		
		/* errore ID non dichiarato */
		assertEquals(tcVisit.getLog(), "Errore semantico a riga 2: b non dichiarato.\n");
	}
	
	@Test
	void testIdNonDec2() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/3_idNonDec.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		
		/* errore ID non dichiarato */
		assertEquals(tcVisit.getLog(), "Errore semantico a riga 2: c non dichiarato.\n");
	}
	
	@Test
	void testTipoNonCompatibile() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/4_tipoNonCompatibile.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		
		/* errore tipi non compatibili */
		assertEquals(tcVisit.getLog(), "Errore semantico a riga 1: b è di tipo INT, impossibile assegnargli un'espressione di tipo FLOAT.\n");
	}
	

	@Test
	void testCorretto1() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/5_corretto.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		
		/* il log è vuoto perchè non sono stati riscontrati errori */
		assertEquals(tcVisit.getLog(), "");
	}
	
	@Test
	void testCorretto2() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/6_corretto.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		
		/* il log è vuoto perchè non sono stati riscontrati errori */
		assertEquals(tcVisit.getLog(), "");
	}
	
	@Test
	void testCorretto3() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/testTypeChecking/7_corretto.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		
		/* il log è vuoto perchè non sono stati riscontrati errori */
		assertEquals(tcVisit.getLog(), "");
	}

}
