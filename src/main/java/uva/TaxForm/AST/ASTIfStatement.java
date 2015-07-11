package uva.TaxForm.AST;

public class ASTIfStatement extends ASTNode {
	
	private ASTNode condition;
	private ASTNode leftNode = null;
	private ASTNode rightNode = null;
	
	ASTIfStatement(AST ast) {
		super(ast);
	}

	int getNodeType0() {
		return IF_STATEMENT;
	}

	public ASTNode getCondition() {
		return condition;
	}

	public void setCondition(ASTNode node) {
		this.condition = node;
	}
	
	public ASTNode getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(ASTNode leftNode) {
		this.leftNode = leftNode;
	}

	public ASTNode getRightNode() {
		return rightNode;
	}

	public void setRightNode(ASTNode rightNode) {
		this.rightNode = rightNode;
	}

}
