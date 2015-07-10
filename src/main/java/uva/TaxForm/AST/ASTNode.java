package uva.TaxForm.AST;

import java.util.ArrayList;


public abstract class ASTNode {

	public final static int FORM = 1;
	public final static int QUESTION = 2;
	public final static int IF_STATEMENT = 3;
	
	final AST ast;
	private int nodeType = 0;
	private ASTNode parent = null;
	private ArrayList<Object> store = new ArrayList<Object>(0);
	
	ASTNode(AST ast) {
		if (ast == null) {
			throw new IllegalArgumentException();
		}
		
		this.ast = ast;
		setNodeType(getNodeType0());
	}

	abstract int getNodeType0();
	
	public final int getNodeType() {
		return this.nodeType;
	}
	
	private void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	public ASTNode getParent() {
		return parent;
	}

	public void setParent(ASTNode parent) {
		this.parent = parent;
	}
	
	public void addChild(ASTNode node) {
		this.store.add(node);
	}
	
	public int size() {
		return this.store.size();
	}
	
	public ASTNode get(int index) {
		return (ASTNode) this.store.get(index);
	}
}
