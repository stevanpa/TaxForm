package uva.TaxForm.AST;

import java.util.ArrayList;

public class ASTBlock extends ASTNode {

	private ArrayList<Object> store = new ArrayList<Object>(0);
	
	ASTBlock(AST ast) {
		super(ast);
	}

	@Override
	int getNodeType0() {
		return ASTNode.BLOCK;
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
