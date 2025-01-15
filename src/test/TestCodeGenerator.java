package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import exception.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

class TestCodeGenerator {
		
	
	@Test
	void testAssign1() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/TestCodeGenerator/1_assign.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		assertEquals(cgVisit.getLog(), "");
		assertEquals(cgVisit.getCodiceGenerato(), "1 6 / sa la p P");
	}
	
	@Test
	void testDivisioni() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/TestCodeGenerator/2_divsioni.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		
		assertEquals(cgVisit.getLog(), "");
		assertEquals(cgVisit.getCodiceGenerato(), "0 sa la 1 + sa 6 sb 1.0 6 5 k / 0 k la lb / + sc la p P lb p P lc p P");
	}
	
	@Test
	void testGenerale() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/TestCodeGenerator/3_generale.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		assertEquals(cgVisit.getLog(), "");
		assertEquals(cgVisit.getCodiceGenerato(), "5 3 + sa la 0.5 5 k / 0 k sb la p P lb 4 5 k / 0 k sb lb p P lb 1 - sc lc lb 5 k / 0 k sc lc p P");
	}
	
	@Test
	void testRegistriFiniti() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner("src/test/data/TestCodeGenerator/4_registriFiniti.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		/* il log se contiene un errore e non viene generato il codice */
		assertEquals(cgVisit.getLog(), "Numero massimo di registri superato.");
		assertEquals(cgVisit.getCodiceGenerato(), "");
	}

}