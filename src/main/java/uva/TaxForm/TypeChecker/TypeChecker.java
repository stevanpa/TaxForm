package uva.TaxForm.TypeChecker;

import java.util.ArrayList;
import java.util.HashSet;

import uva.TaxForm.AST.ASTExpression;
import uva.TaxForm.AST.ASTIfStatement;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTNumber;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.AST.ASTVariable;
import uva.TaxForm.Utils.ShuntingYardAlgorithm;
import uva.TaxForm.Visitors.VisitAST;

public class TypeChecker {
	
	public static HashSet<String> checkAST(ASTNode node) {
		
		HashSet<String> msg = new HashSet<String>(0);
		
		//checkForUndefinedQuestions(node);
		msg.addAll(checkForDuplicateQuestions(node));
		msg.addAll(checkForConditionsNotOfTypeBoolean(node));
		msg.addAll(checkInvalidOperandToOperator(node));
		
		return msg;
	}
	
	public static boolean checkForUndefinedQuestions(ASTNode node) {
		
		boolean undefined = false;
		ArrayList<ASTNode> nodeList = VisitAST.getNodesByType(node, ASTNode.QUESTION);
		
		for (int i=0; i<nodeList.size(); i++) {
			ASTQuestion question = (ASTQuestion) nodeList.get(i);
			ASTVariable var = (ASTVariable) question.getExpression().getLeftNode();
			
			System.out.println(var.getType());
			
			/*if (var.getName().equals(var.getName())) {
				undefined = true;
				System.out.println(var.getName());
			}*/
		}
		
		return undefined;
	}
	
	// Check for duplicate question declarations with different types
	public static HashSet<String> checkForDuplicateQuestions(ASTNode node) {
		
		HashSet<String> msg = new HashSet<String>(0);
		ArrayList<ASTNode> nodeList = VisitAST.getNodesByType(node, ASTNode.QUESTION);
		
		for (int i=0; i<nodeList.size(); i++) {
			ASTQuestion question = (ASTQuestion) nodeList.get(i);
			ASTVariable var = (ASTVariable) question.getExpression().getLeftNode();
			
			for (int j=i+1; j<nodeList.size(); j++) {
				ASTQuestion dupQuestion = (ASTQuestion) nodeList.get(j);
				ASTVariable dupVar = (ASTVariable) dupQuestion.getExpression().getLeftNode();
				
				if (var.getName().equals(dupVar.getName())) {
					msg.add("Duplicate of variable '" + dupVar.getName() + "' already exisits in form");
					//System.out.println("Duplicate: " + dupVar.getName());
				}
			}
		}
		
		return msg;
	}
	
	// Check for conditions not of type boolean
	public static HashSet<String> checkForConditionsNotOfTypeBoolean(ASTNode node) {
		
		HashSet<String> msg = new HashSet<String>(0);
		ArrayList<ASTNode> nodeList = VisitAST.getNodesByType(node, ASTNode.IF_STATEMENT);
		
		for (int i=0; i<nodeList.size(); i++) {
			ASTIfStatement statement = (ASTIfStatement) nodeList.get(i);
			ArrayList<ASTNode> postfixList = ShuntingYardAlgorithm.astToPostfix(statement.getExpression());
			
			for (int j=0; j<postfixList.size(); j++) {

				try {
					ASTVariable var = (ASTVariable) postfixList.get(j);
					/*System.out.println(var.getType());
					System.out.println(condition);*/
					
					if (var.getType() != ASTVariable.BOOLEAN) {
						msg.add("Variable '" + var.getName() + "' is not of type BOOLEAN");
						break;
					}
				} 
				catch (ClassCastException e) {
					//System.out.println(e.getMessage());
				}
				
				try {
					ASTExpression exp = (ASTExpression) postfixList.get(j);
					/*System.out.println(exp.getExpressionType());
					System.out.println(condition);*/
					
					switch (exp.getExpressionType()) {
						case ASTExpression.ADD_EXP: 
							msg.add("Operator of type '+' is not allowed in a conditional expression");
							//System.out.println(condition);
							break;
						case ASTExpression.ASSIGN_EXP:
							msg.add("Operator of type '=' is not allowed in a conditional expression");
							//System.out.println(condition);
							break;
						case ASTExpression.DIVIDE_EXP:
							msg.add("Operator of type '/' is not allowed in a conditional expression");
							//System.out.println(condition);
							break;
						case ASTExpression.MINUS_EXP:
							msg.add("Operator of type '-' is not allowed in a conditional expression");
							//System.out.println(condition);
							break;
						case ASTExpression.MULTIPLY_EXP:
							msg.add("Operator of type '*' is not allowed in a conditional expression");
							//System.out.println(condition);
							break;
					}
				} 
				catch (ClassCastException e) {
					//System.out.println(e.getMessage());
				}
			}
			//System.out.println(postfixList);
		}
		return msg;
	}
	
	// Check for operands of invalid types to operator e.g. int = int * double
	public static HashSet<String> checkInvalidOperandToOperator(ASTNode node) {
		
		HashSet<String> msg = new HashSet<String>(0);
		ArrayList<ASTNode> nodeList = VisitAST.getNodesByType(node, ASTNode.EXPRESSION);
		
		for (int i=0; i<nodeList.size(); i++) {
			ASTExpression expr = (ASTExpression) nodeList.get(i);
			
			if (expr.getExpressionType() == ASTExpression.ASSIGN_EXP) {
				ASTExpression assExpr = (ASTExpression) expr.getRightNode();
				ArrayList<ASTNode> postfixList = ShuntingYardAlgorithm.astToPostfix(assExpr);
				ArrayList<Integer> operandTypes = getOperands(postfixList);
				
				System.out.println(operandTypes);
				
				
			}
		}
		
		return msg;
	}
	
	private static ArrayList<Integer> getOperands(ArrayList<ASTNode> postfixList) {
		
		ArrayList<Integer> operandTypes = new ArrayList<Integer>(0);
		
		for (int j=0; j<postfixList.size(); j++) {
			try {
				ASTVariable var = (ASTVariable) postfixList.get(j);
				operandTypes.add(var.getType());
			}
			catch (ClassCastException e) {}
			
			try {
				ASTNumber num = (ASTNumber) postfixList.get(j);
				operandTypes.add(num.getType());
			}
			catch (ClassCastException e) {}
		}
		
		return operandTypes;
	}
}




















