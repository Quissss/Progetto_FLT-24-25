package ast;

import visitor.IVisitor;

/**
 * Implementa il nodo NodeId dell'AST.
 * @author Benetti Luca 20043903
 */
public class NodeId extends NodeAST {

	private String name;

	
	/**
	 * Costruttore per NodeId.
	 * 
	 * @param name il nome assegnato al nodo
	 */
	public NodeId(String name) {
		this.name = name;
	}



	/**
	 * Restituisce il nome dell'id assegnato a un NodeId.
	 * 
	 * @return name il nome dell'id assegnato al nodo
	 */
	public String getName() {
		return name;
	}



	@Override
	public String toString() {
		return "NodeId [name=" + name + "]";
	}



	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
		
	}

	
	

	
}
