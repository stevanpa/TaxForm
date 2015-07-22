package uva.TaxForm.AST;

import uva.TaxForm.Interfaces.IExpression;

public class ASTExpression extends ASTNode implements IExpression{
	
	private int expressionType = 0;
	private ASTNode leftNode = null;
	private ASTNode rightNode = null;
	
	ASTExpression(AST ast) {
		super(ast);
	}

	public int getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(int expressionType) {
		this.expressionType = expressionType;
	}

	int getNodeType0() {
		return ASTNode.EXPRESSION;
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
