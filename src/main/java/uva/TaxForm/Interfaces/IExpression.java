package uva.TaxForm.Interfaces;

import uva.TaxForm.AST.ASTNode;

public interface IExpression {

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
	
	public int getExpressionType();
	public void setExpressionType(int expressionType);
	public ASTNode getLeftNode();
	public void setLeftNode(ASTNode leftNode);
	public ASTNode getRightNode();
	public void setRightNode(ASTNode rightNode);
}
