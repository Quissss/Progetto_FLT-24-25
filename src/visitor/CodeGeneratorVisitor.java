package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import exception.CodeGeneratorException;
import symbolTable.Registri;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;

public class CodeGeneratorVisitor implements IVisitor {

	private String codiceDc; // mantiene il codice della visita
	private String log; // per l’eventuale errore nella generazione del codice
	private String codiceGenerato = "";

	/**
	 * Costruttore per CodeGeneratorVisitor, inizializza Registri e campi della
	 * classe.
	 */
	public CodeGeneratorVisitor() {
		Registri.inizializza();
		codiceDc = "";
		log = "";
	}

	/**
	 * Restituisce il codice generato alla fine di una compilazione
	 * 
	 * @return il codice generato
	 */
	public String getCodiceGenerato() {
		return codiceGenerato.strip();
	}

	@Override
	public void visit(NodeProgram node) {
		for (NodeDecSt decSt : node.getDecSts()) {
			if (log == "") {
				decSt.accept(this);
				codiceGenerato += codiceDc + " ";
				codiceDc = "";
			}
			/* annulla il codice generato in caso di errore */
			else {
				codiceGenerato = "";
			}
		}
	}

	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		String leftCodice = codiceDc;
		node.getRight().accept(this);
		String rightCodice = codiceDc;

		switch (node.getOp()) {
		case DIV -> {
			codiceDc = leftCodice + " " + rightCodice + " /";
		}
		case MINUS -> {
			codiceDc = leftCodice + " " + rightCodice + " -";
		}
		case PLUS -> {
			codiceDc = leftCodice + " " + rightCodice + " +";
		}
		case TIMES -> {
			codiceDc = leftCodice + " " + rightCodice + " *";
		}
		case DIV_FLOAT ->{
			codiceDc = leftCodice + " " + rightCodice + " 5 k / 0 k";
		}
		}
	}

	@Override
	public void visit(NodeAssign node) {
		node.getExpr().accept(this);
		String exprCodice = codiceDc;

		node.getId().accept(this);
		String idCodice = codiceDc;

		codiceDc = exprCodice + " s" + idCodice;
		/* resetta la precisione se è stata modificata in precedenza */
		// if (codiceDc.contains("5 k"))
		// codiceDc = codiceDc.concat(" 0 k");
	}

	@Override
	public void visit(NodeCost node) {
		codiceDc = node.getValue();
	}

	@Override
	public void visit(NodeDecl node) {
		Attributes attr = SymbolTable.lookup(node.getId().getName());
		char registro = 0;
		try {
			registro = Registri.newRegister();
		} catch (CodeGeneratorException e) {
			log = e.getMessage();
			return;
		}

		attr.setRegister(registro);

		if (node.getInit() != null) {
			node.getInit().accept(this);
			String init = codiceDc;

			node.getId().accept(this);
			String id = codiceDc;

			codiceDc = init + " s" + id;
			/* resetta la precisione se è stata modificata in precedenza */
			// if (codiceDc.contains("5 k"))
			// codiceDc = codiceDc.concat(" 0 k");
		}
	}

	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);
		codiceDc = "l" + codiceDc;
	}

	@Override
	public void visit(NodeId node) {
		codiceDc = String.valueOf(SymbolTable.lookup(node.getName()).getRegister());
	}

	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		codiceDc = "l" + codiceDc + " p P";
	}

	/**
	 * Restituisce il log ottenuto dalla visita.
	 * 
	 * @return il log della visita
	 */
	public String getLog() {
		return log;
	}

}