package uva.TaxForm.TypeChecker;

import java.util.ArrayList;
import java.util.HashMap;
import uva.TaxForm.AST.ASTExpression;
import uva.TaxForm.AST.ASTIfStatement;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTNumber;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.AST.ASTVariable;
import uva.TaxForm.Utils.ShuntingYardAlgorithm;
import uva.TaxForm.Visitors.VisitAST;

public class TypeChecker {
	
	public static HashMap<String, Integer> checkAST(ASTNode node) {
		
		HashMap<String, Integer> msg = new HashMap<String, Integer>(0);
		
		//checkForUndefinedQuestions(node);
		msg.putAll(checkDuplicateQuestions(node));
		msg.putAll(checkConditionsNotOfTypeBoolean(node));
		msg.putAll(checkInvalidOperandToOperator(node));
		msg.putAll(checkCyclicDependencies(node));
		msg.putAll(checkDuplicateLabels(node));
		
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
	public static HashMap<String, Integer> checkDuplicateQuestions(ASTNode node) {
		
		HashMap<String, Integer> msg = new HashMap<String, Integer>(0);
		ArrayList<ASTNode> nodeList = VisitAST.getNodesByType(node, ASTNode.QUESTION);
		
		for (int i=0; i<nodeList.size(); i++) {
			ASTQuestion question = (ASTQuestion) nodeList.get(i);
			ASTVariable var = (ASTVariable) question.getExpression().getLeftNode();
			
			for (int j=i+1; j<nodeList.size(); j++) {
				ASTQuestion dupQuestion = (ASTQuestion) nodeList.get(j);
				ASTVariable dupVar = (ASTVariable) dupQuestion.getExpression().getLeftNode();
				
				if (var.getName().equals(dupVar.getName())) {
					msg.put("Error: Duplicate of variable '" + dupVar.getName() + "' already exisits in form", -1);
					//System.out.println("Duplicate: " + dupVar.getName());
				}
			}
		}
		
		return msg;
	}
	
	// Check for conditions not of type boolean
	public static HashMap<String, Integer> checkConditionsNotOfTypeBoolean(ASTNode node) {
		
		HashMap<String, Integer> msg = new HashMap<String, Integer>(0);
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
						msg.put("Error: Variable '" + var.getName() + "' is not of type BOOLEAN", -1);
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
							msg.put("Error: Operator of type '+' is not allowed in a conditional expression", -1);
							//System.out.println(condition);
							break;
						case ASTExpression.ASSIGN_EXP:
							msg.put("Error: Operator of type '=' is not allowed in a conditional expression", -1);
							//System.out.println(condition);
							break;
						case ASTExpression.DIVIDE_EXP:
							msg.put("Error: Operator of type '/' is not allowed in a conditional expression", -1);
							//System.out.println(condition);
							break;
						case ASTExpression.MINUS_EXP:
							msg.put("Error: Operator of type '-' is not allowed in a conditional expression", -1);
							//System.out.println(condition);
							break;
						case ASTExpression.MULTIPLY_EXP:
							msg.put("Error: Operator of type '*' is not allowed in a conditional expression", -1);
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
	public static HashMap<String, Integer> checkInvalidOperandToOperator(ASTNode node) {
		
		HashMap<String, Integer> msg = new HashMap<String, Integer>(0);
		ArrayList<ASTNode> nodeList = VisitAST.getNodesByType(node, ASTNode.EXPRESSION);
		
		for (int i=0; i<nodeList.size(); i++) {
			ASTExpression expr = (ASTExpression) nodeList.get(i);
			
			if (expr.getExpressionType() == ASTExpression.ASSIGN_EXP) {
				ASTExpression assExpr = (ASTExpression) expr.getRightNode();
				ArrayList<ASTNode> postfixList = ShuntingYardAlgorithm.astToPostfix(assExpr);
				ArrayList<Integer> operandTypes = getOperands(postfixList);
				
				Integer operandType = operandTypes.get(0);
				//System.out.println(operandTypes);
				for (int j=1; j<operandTypes.size(); j++) {
					if (operandTypes.get(j) != operandType) {
						//System.out.println("Operands of invalid type to operator in " + ShuntingYardAlgorithm.astToPostfixString(assExpr));
						msg.put("Error: Operands of invalid type to operator in " + ShuntingYardAlgorithm.astToPostfixString(assExpr), -1);
						break;
					}
				}
				
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
	
	public static HashMap<String, Integer> checkCyclicDependencies(ASTNode node) {
		
		HashMap<String, Integer> msg = new HashMap<String, Integer>(0);
		
		
		
		return msg;
	}
	
	public static HashMap<String, Integer> checkDuplicateLabels(ASTNode node) {
		
		HashMap<String, Integer> msg = new HashMap<String, Integer>(0);
		ArrayList<ASTNode> nodeList = VisitAST.getNodesByType(node, ASTNode.QUESTION);
		
		for (int i=0; i<nodeList.size(); i++) {
			ASTQuestion question = (ASTQuestion) nodeList.get(i);
			String label = question.getLabel();
			
			for (int j=i+1; j<nodeList.size(); j++) {
				ASTQuestion dupQuestion = (ASTQuestion) nodeList.get(j);
				String dupLabel = dupQuestion.getLabel();
				
				if (label.equals(dupLabel)) {
					msg.put("Warning: Duplicate label '" + dupLabel + "'", 0);
					//System.out.println("Warning: Duplicate label '" + dupLabel + "'");
				}
			}
		}
		return msg;
	}
}




















