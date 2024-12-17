package ast;

/**
 * Implementa il nodo NodePrint dell'AST.
 * @author Benetti Luca 20043903
 */
public class NodePrint extends NodeStm {

	NodeId id;
	
	/**
	 * Costruttore per NodePrint.
	 * 
	 * @param id NodeId assegnato al nodo
	 */
	public NodePrint(NodeId id) {
		this.id = id;
	}


	/**
	 * Restituiscce il NodeId assegnato a un NodePrint.
	 * 
	 * @return NodeId assegnato al nodo
	 */
	public NodeId getId() {
		return id;
	}


	@Override
	public String toString() {
		return "NodePrint [id=" + id + "]";
	} 
	
	
	
	
}
