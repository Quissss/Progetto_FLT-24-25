package ast;

import visitor.IVisitor;

/**
 * Implementa il nodo NodeConst dell'AST.
 * @author Benetti Luca 20043903
 */
public class NodeCost extends NodeExpr{

	private String value;
	private LangType type;
	
	/**
	 * Costruttore per NodeConst
	 * 
	 * @param value il valore della costante
	 * @param type  il tipo della costante
	 */
	public NodeCost(String value, LangType type) {
		this.value = value;
		this.type = type;
	}

	/**
	 * Ritorna il valore assegnato a un NodeConst.
	 * 
	 * @return il valore della costante rappresentata da un NodeConst
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Ritorna il tipo del valore assegnato a un NodeConst.
	 * 
	 * @return il tipo del valore della costante rappresentata da un NodeConst
	 */
	public LangType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "NodeCost [value=" + value + ", type=" + type + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
		
	}
	
	
}
