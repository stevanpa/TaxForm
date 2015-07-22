package uva.TaxForm.AST;

public class ASTIfStatement extends ASTNode {
	
	private ASTExpression expression = null;
	private ASTNode leftNode = null;
	private ASTNode rightNode = null;
	
	ASTIfStatement(AST ast) {
		super(ast);
	}

	@Override
	int getNodeType0() {
		return IF_STATEMENT;
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

	public ASTExpression getExpression() {
		return expression;
	}

	public void setExpression(ASTExpression expression) {
		this.expression = expression;
	}

}
