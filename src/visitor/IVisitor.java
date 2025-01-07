package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;

public interface IVisitor {

	/**
	 * Visita il nodo NodeProgram.
	 * 
	 * @param node il NodeProgram da visitare
	 */
	public abstract void visit(NodeProgram node);
	
	/**
	 * Visita il nodo NodeId.
	 * 
	 * @param node il NodeId da visitare
	 */
	public abstract void visit(NodeId node);
	
	/**
	 * Visita il nodo NodeDecl.
	 * 
	 * @param node il NodeDecl da visitare
	 */
	public abstract void visit(NodeDecl node);
	
	/**
	 * Visita il nodo NodeBinOp.
	 * 
	 * @param node il NodeBinOp da visitare
	 */
	public abstract void visit(NodeBinOp node);
	
	/**
	 * Visita il nodo NodePrint.
	 * 
	 * @param node il NodePrint da visitare
	 */
	public abstract void visit(NodePrint node);
	
	/**
	 * Visita il nodo NodeCost.
	 * 
	 * @param node il NodeCost da visitare
	 */
	public abstract void visit(NodeCost node);
	
	/**
	 * Visita il nodo NodeDeref.
	 * 
	 * @param node il NodeDeref da visitare
	 */
	public abstract void visit(NodeDeref node);
	
	/**
	 * Visita il nodo NodeAssign.
	 *
	 * @param node il NodeAssign da visitare
	 */
	public abstract void visit(NodeAssign node);
	
}
