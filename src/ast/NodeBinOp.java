package ast;

import visitor.IVisitor;

/**
 * Implementa il nodo NodeBinOp dell'AST.
 * @author Benetti Luca 20043903
 */
public class NodeBinOp extends NodeExpr{

	LangOper op;
	NodeExpr left; 
	NodeExpr right;
	
	/**
	 * Costruttore per NodeBinOp.
	 * 
	 * @param op    l'operatore
	 * @param left  l'espressione a sinistra
	 * @param right l'espressione a destra
	 */
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	/**
     * Imposta un nuovo operatore per il NodeBinOp.
     * 
     * @param op il nuovo operatore
     */
    public void setOp(LangOper op) {
        this.op = op;
    }

	/**
	 * Restituisce l'operatore assegnato a un NodeBinOp.
	 * 
	 * @return il tipo di operatore
	 */
	public LangOper getOp() {
		return op;
	}


	/**
	 * Restituisce l'espressione a sinistra assegnata al NodeBinOp.
	 * 
	 * @return l'espressione a sinistra dell'operatore
	 */
	public NodeExpr getLeft() {
		return left;
	}


	/**
	 * Restituisce l'espressione a destra assegnata al NodeBinOp.
	 * 
	 * @return l'espressione a destra dell'operatore
	 */
	public NodeExpr getRight() {
		return right;
	}

	

	/**
	 * Imposta l'espressione a sinistra di un'operatore in seguito a una
	 * conversione.
	 * 
	 * @param left l'espressione a sinistra
	 */
	public void setLeft(NodeExpr left) {
		this.left = left;
	}


	/**
	 * Imposta l'espressione a destra dell'operatore in seguito a una conversione.
	 * 
	 * @param right l'espressione a destra
	 */
	public void setRight(NodeExpr right) {
		this.right = right;
	}


	@Override
	public String toString() {
		return "NodeBinOp [op=" + op + ", left=" + left + ", right=" + right + "]";
	}


	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
		
	}  

	
	
}
