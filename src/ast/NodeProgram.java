package ast;

import java.util.ArrayList;
import java.util.Iterator;

import visitor.IVisitor;

/**
 * Implementa il nodo NodeProgram dell'AST.
 * @author Benetti Luca 20043903
 */
public class NodeProgram extends NodeAST {

	private  ArrayList<NodeDecSt> decSts;

	
	/**
	 * Costruttore per NodeProgram.
	 * 
	 * @param decSts la lista di dichiarazioni
	 */
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}


	/**
	 * Restituisce la lista di dichiarazioni
	 * 
	 * @return la lista di dichiarazioni
	 */
	public ArrayList<NodeDecSt> getDecSts() {
		return decSts;
	}



	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(NodeAST node : decSts)
        {
            builder.append(node.toString()).append("  ");
        }
        return "[Program:" + builder.toString().trim() + "]";
    }


	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
		
	}

	
}
