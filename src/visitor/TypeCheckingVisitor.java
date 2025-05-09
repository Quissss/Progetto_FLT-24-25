package visitor;

import ast.LangOper;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import ast.TipoTD;
import ast.TypeDescriptor;
import symbolTable.SymbolTable;

public class TypeCheckingVisitor implements IVisitor {

	private TypeDescriptor resType; // mantiene il risultato della visita
	private String log;
	private int riga;

	/**
	 * Costruttore per TypeCheckingVisitor, inizializza la SymbolTable e i campi
	 * della classe. log e riga
	 */
	public TypeCheckingVisitor() {
		SymbolTable.init();
		log = "";
		riga = 0;
	}

	@Override
	public void visit(NodeProgram node) {
		for (NodeDecSt decSt : node.getDecSts()) {
			decSt.accept(this);
			riga++;
		}

	}

	@Override
	public void visit(NodeId node) {
		if (SymbolTable.lookup(node.getName()) == null) {
			resType = new TypeDescriptor(TipoTD.ERROR, node.getName() + " non dichiarato.\n", riga);
			log += "Errore semantico a riga " + resType.getRiga() + ": " + resType.getMsg();
		} else {
			switch (SymbolTable.lookup(node.getName()).getType()) {
			case INT -> {
				resType = new TypeDescriptor(TipoTD.INT);
			}
			case FLOAT -> {
				resType = new TypeDescriptor(TipoTD.FLOAT);
			}
			}
		}
	}

	@Override
	public void visit(NodeDecl node) {
		TypeDescriptor idTD;

		if (SymbolTable.enter(node.getId().getName(),
				new SymbolTable.Attributes(node.getType(), node.getId().getName()))) {
			switch (node.getType()) {
			case FLOAT -> {
				idTD = new TypeDescriptor(TipoTD.FLOAT);
			}
			case INT -> {
				idTD = new TypeDescriptor(TipoTD.INT);
			}
			default -> {
				idTD = new TypeDescriptor(TipoTD.ERROR);
			}
			}
		} else {
			resType = new TypeDescriptor(TipoTD.ERROR, node.getId().getName() + " già dichiarato.\n", riga);
			log += "Errore semantico a riga " + resType.getRiga() + ": " + resType.getMsg();
			return;
		}

		if (node.getInit() == null) {
			resType = new TypeDescriptor(TipoTD.OK);
			return;
		}

		node.getInit().accept(this);
		TypeDescriptor initTD = resType;

		if (idTD.getTipo() == TipoTD.INT) {
			if (initTD.getTipo() == TipoTD.FLOAT) {
				resType = new TypeDescriptor(TipoTD.ERROR, node.getId().getName()
						+ " è di tipo INT, impossibile assegnargli un'espressione di tipo FLOAT.\n", riga);
				log += "Errore semantico a riga " + resType.getRiga() + ": " + resType.getMsg();
			} else
				resType = new TypeDescriptor(TipoTD.OK);
		} else
			resType = new TypeDescriptor(TipoTD.OK);

	}

	@Override
	public void visit(NodeBinOp node) {
	    // Visita i nodi figli e ottieni i TypeDescriptor
	    node.getLeft().accept(this);
	    TypeDescriptor leftTD = resType;
	    node.getRight().accept(this);
	    TypeDescriptor rightTD = resType;

	    // Se uno dei due tipi è un errore, restituisci l'errore
	    if (leftTD.getTipo() == TipoTD.ERROR) {
	        resType = leftTD;
	        return;
	    } else if (rightTD.getTipo() == TipoTD.ERROR) {
	        resType = rightTD;
	        return;
	    }

	    // Controlla se l'operatore è una divisione e i tipi sono FLOAT
	    if (node.getOp() == LangOper.DIV &&
	        leftTD.getTipo() == TipoTD.FLOAT ||
	        rightTD.getTipo() == TipoTD.FLOAT) {
	        // Modifica l'operatore in DIV_FLOAT
	        node.setOp(LangOper.DIV_FLOAT);
	    }

	    // Determina il tipo del risultato
	    if (leftTD.getTipo() == TipoTD.FLOAT || rightTD.getTipo() == TipoTD.FLOAT) {
	        resType = new TypeDescriptor(TipoTD.FLOAT);
	    } else {
	        resType = new TypeDescriptor(TipoTD.INT);
	    }
	}

	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);

		if (resType.getTipo() != TipoTD.ERROR)
			resType = new TypeDescriptor(TipoTD.OK);

	}

	@Override
	public void visit(NodeCost node) {
		switch (node.getType()) {
		case FLOAT -> {
			resType = new TypeDescriptor(TipoTD.FLOAT);
		}
		case INT -> {
			resType = new TypeDescriptor(TipoTD.INT);
		}
		}
	}

	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);

	}

	@Override
	public void visit(NodeAssign node) {
		node.getId().accept(this);
		TypeDescriptor idTD = resType;
		node.getExpr().accept(this);
		TypeDescriptor exprTD = resType;

		if (idTD.getTipo() == TipoTD.ERROR)
			resType = idTD;
		else if (exprTD.getTipo() == TipoTD.ERROR)
			resType = exprTD;
		else if (!exprTD.compatibile(idTD)) {
			resType = new TypeDescriptor(TipoTD.ERROR,
					node.getId().getName() + " è di tipo INT, impossibile assegnargli un'espressione di tipo FLOAT.\n",
					riga);
			log += "Errore semantico a riga " + resType.getRiga() + ": " + resType.getMsg();
		} else
			resType = new TypeDescriptor(TipoTD.OK);

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