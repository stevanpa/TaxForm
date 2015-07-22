package uva.TaxForm.AST;

import uva.TaxForm.Interfaces.IExpression;

public class ASTExpression extends ASTNode implements IExpression{
	
	private int expressionType = 0;
	private ASTNode leftNode = null;
	private ASTNode rightNode = null;
	
	ASTExpression(AST ast) {
		super(ast);
	}

	@Override
	public int getExpressionType() {
		return expressionType;
	}

	@Override
	public void setExpressionType(int expressionType) {
		this.expressionType = expressionType;
	}

	@Override
	int getNodeType0() {
		return ASTNode.EXPRESSION;
	}
	
	@Override
	public ASTNode getLeftNode() {
		return leftNode;
	}

	@Override
	public void setLeftNode(ASTNode leftNode) {
		this.leftNode = leftNode;
	}

	@Override
	public ASTNode getRightNode() {
		return rightNode;
	}

	@Override
	public void setRightNode(ASTNode rightNode) {
		this.rightNode = rightNode;
	}

}
