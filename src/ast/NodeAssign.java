package ast;

import visitor.IVisitor;

/**
 * Implementa il nodo NodeAssign dell'AST.
 * 
 * @author Benetti Luca 20043903
 */
public class NodeAssign extends NodeStm {

	NodeId id;
	NodeExpr expr;

	/**
	 * Costruttore per NodeAssign
	 * 
	 * @param id   il nome dell'id
	 * @param expr l'espressione assegnata all'id
	 */
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}

	/**
	 * Restituisce l'id assegnato al NodeId.
	 * 
	 * @return NodeId assegnato al nodo
	 */
	public NodeId getId() {
		return id;
	}

	/**
	 * Restituisce l'espressione assegnata al NodeId.
	 * 
	 * @return NodeExpr assegnato al nodo
	 */
	public NodeExpr getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "NodeAssign [id=" + id + ", expr=" + expr + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
