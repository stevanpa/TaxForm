package uva.TaxForm.AST;

public class ASTExpression extends ASTNode{

	public final static int SINGLE_EXP 		= 1;
	public final static int AND_EXP 		= 2;
	public final static int OR_EXP 			= 3;
	public final static int NOT_EXP 		= 4;
	public final static int LOWER_EXP 		= 5;
	public final static int UPPER_EXP 		= 6;
	public final static int LOWER_EQUAL_EXP = 7;
	public final static int UPPER_EQUAL_EXP = 8;
	public final static int EQUAL_EXP 		= 9;
	public final static int NOT_EQUAL_EXP 	= 10;
	public final static int MINUS_EXP 		= 11;
	public final static int ADD_EXP 		= 12;
	public final static int MULTIPLY_EXP 	= 13;
	public final static int DIVIDE_EXP 		= 14;
	public final static int ASSIGN_EXP 		= 15;
	public final static int EXP		 		= 16;
	
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
