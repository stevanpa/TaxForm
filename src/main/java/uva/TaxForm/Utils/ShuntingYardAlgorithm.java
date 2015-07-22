package uva.TaxForm.Utils;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

import uva.TaxForm.AST.AST;
import uva.TaxForm.AST.ASTExpression;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTNumber;
import uva.TaxForm.AST.ASTVariable;
import uva.TaxForm.Visitors.VisitAST;

public class ShuntingYardAlgorithm {

	public static void infixToAST(ArrayList<Object> infixList, ASTExpression exp) {
		ArrayList<Object> postfixList = infixToPostfix(infixList);
		postfixToAST(postfixList, exp);
	}

	public static ArrayList<Object> infixToPostfix(ArrayList<Object> infixList) {
		
		final String ops = "-+/*^";
		ArrayList<Object> outputList = new ArrayList<Object>(0);
		Stack<Integer> opStack = new Stack<Integer>();
		
		for (Object token : infixList) {
			
			// Get the index of the operator
			String strToken = token.toString();
			int opIndex = ops.indexOf(strToken);
			
			// If we found an operator
			if (opIndex != -1) {
				// Check for an empty operator stack
				if (opStack.isEmpty()) {
					opStack.push(opIndex);
				}
				// Operator stack is not empty
				else {
					//TODO understand what's happening here... a bit of black magic?
					while (!opStack.isEmpty()) {
						int headIndex = opStack.peek() / 2;
						int nextIndex = opIndex / 2;
						if (headIndex > nextIndex || (headIndex == nextIndex && strToken != "^")) {
							outputList.add(ops.charAt(opStack.pop()));
						} else {
							break;
						}
					}
					opStack.push(opIndex);
				}
			}
			else if (strToken.equals("(")) {
				opStack.push(-2);
			}
			else if (strToken.equals(")")) {
				while (opStack.peek() != -2) {
					outputList.add(ops.charAt(opStack.pop()));
				}
				opStack.pop();
			}
			else {
				outputList.add(token);
			}
		}
		while (!opStack.isEmpty()) {
			outputList.add(ops.charAt(opStack.pop()));
		}
		
		return outputList;
	}
	
	public static void postfixToAST(ArrayList<Object> postfixList, ASTExpression parentNode) {
		
		//System.out.println("postfixList " + postfixList);
		
		// ASTVariable - SINGLE_EXP
		if (postfixList.size() == 1) {
			ASTNode node = getNode(postfixList.get(0), parentNode);
			ASTVariable var = (ASTVariable) node;
			
			var.setParent(parentNode);
			parentNode.setExpressionType(ASTExpression.SINGLE_EXP);
			parentNode.setLeftNode(var);
		} 
		
		// ASTExpression
		else {
			
			Stack<Object> postfixStack = new Stack<Object>();
			postfixStack.addAll(postfixList);
			Stack<Object> treeStack = new Stack<Object>();
			Stack<Object> operatorStack = new Stack<Object>();
			
			while (!postfixStack.isEmpty()) {
				
				ASTNode node = getNode(postfixStack.pop(), parentNode);
				
				//TODO 	What to do when the getNode() method returns null?
				//		it's a reference to an undefined question
				if (node.getNodeType() == ASTNode.EXPRESSION) {
					operatorStack.add(node);
				}
				else if (node.getNodeType() != ASTNode.EXPRESSION) {
					treeStack.add(node);
				}
				
				if (treeStack.size() >= 2 && operatorStack.size() > 0) {
					ASTNode leftNode = (ASTNode) treeStack.pop();
					ASTNode rightNode = (ASTNode) treeStack.pop();
					ASTExpression expression = (ASTExpression) operatorStack.pop();
					
					leftNode.setParent(expression);
					rightNode.setParent(expression);
					//System.out.println(expression.getExpressionType());
					//expression.setExpressionType(expression.getExpressionType());
					expression.setLeftNode(leftNode);
					expression.setRightNode(rightNode);
					
					treeStack.add(expression);
				}
			}
			ASTExpression node = (ASTExpression) treeStack.pop();
			ASTNode leftNode = node.getLeftNode();
			ASTNode rightNode = node.getRightNode();
			
			leftNode.setParent(parentNode);
			rightNode.setParent(parentNode);
			
			parentNode.setExpressionType(node.getExpressionType());
			parentNode.setLeftNode(leftNode);
			parentNode.setRightNode(rightNode);
		}
	}
	
	private static ASTNode getNode(Object item, ASTNode parentNode) {
		
		ASTNode node = null;
		
		if (Pattern.matches("^[0-9]+$", item.toString())) {
			node = getNumberNode(item);
		} 
		else if (Pattern.matches("^[0-9]+[.][0-9]{0,2}$", item.toString())) {
			node = getNumberNode(item);
		}
		else if (item.toString().matches("[a-zA-Z_0-9]+")) {
			node = getVariableNode(item, parentNode);
		} 
		else {
			node = getExpressionNode(item);
		}
		
		return node;
	}
	
	private static ASTVariable getVariableNode(Object item, ASTNode parentNode) {
		
		ArrayList<ASTNode> nodeList = VisitAST.getNodesByType(parentNode, ASTNode.VARIABLE);
		ASTVariable var = null;
		
		for (ASTNode n: nodeList) {
			ASTVariable tempVar = (ASTVariable) n;
			
			if (tempVar.getName().equals(item.toString())) {
				var = tempVar;
				break;
			}
		}
		
		return var;
	}
	
	private static ASTNumber getNumberNode(Object item) {
		ASTNumber num = null;
		
		if (Pattern.matches("^[0-9]+[.][0-9]{0,2}$", item.toString())) {
			num = AST.newNumber();
			num.setType(ASTNumber.DOUBLE);
			num.setValue(item.toString());
		}
		else if (Pattern.matches("^[0-9]+$", item.toString())) {
			num = AST.newNumber();
			num.setType(ASTNumber.INT);
			num.setValue(item.toString());
		}
		
		return num;
	}
	
	private static ASTExpression getExpressionNode(Object item) {
		ASTExpression expr = null;
		
		if (item.toString().equals("-")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.MINUS_EXP);
		}
		else if (item.toString().equals("+")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.ADD_EXP);
		}
		else if (item.toString().equals("/")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.DIVIDE_EXP);
		}
		else if (item.toString().equals("*")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.MULTIPLY_EXP);
		}
		else if (item.toString().equals("&&")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.AND_EXP);
		}
		else if (item.toString().equals("||")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.OR_EXP);
		}
		else if (item.toString().equals("!")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.NOT_EXP);
		}
		else if (item.toString().equals("<")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.LOWER_EXP);
		}
		else if (item.toString().equals(">")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.UPPER_EXP);
		}
		else if (item.toString().equals("<=")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.LOWER_EQUAL_EXP);
		}
		else if (item.toString().equals(">=")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.UPPER_EQUAL_EXP);
		}
		else if (item.toString().equals("==")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.EQUAL_EXP);
		}
		else if (item.toString().equals("!=")) {
			expr = AST.newExpression();
			expr.setExpressionType(ASTExpression.NOT_EQUAL_EXP);
		}
		
		return expr;
	}
	
	
	public static ArrayList<ASTNode> astToPostfix(ASTExpression exp) {
		
		ArrayList<ASTNode> postfixList = new ArrayList<ASTNode>(0);
		ASTNode leftNode = exp.getLeftNode();
		ASTNode rightNode = exp.getRightNode();
		//System.out.println(exp.getExpressionType());
		
		if (exp.getExpressionType() == ASTExpression.SINGLE_EXP) {
			postfixList.add(leftNode);
		}
		else if (leftNode.getNodeType() == ASTNode.EXPRESSION && rightNode.getNodeType() == ASTNode.EXPRESSION) {
			postfixList.addAll(astToPostfix((ASTExpression) leftNode));
			postfixList.addAll(astToPostfix((ASTExpression) rightNode));
			postfixList.add(exp);
		}
		else if (leftNode.getNodeType() != ASTNode.EXPRESSION && rightNode.getNodeType() == ASTNode.EXPRESSION) {
			postfixList.add(leftNode);
			postfixList.addAll(astToPostfix((ASTExpression) rightNode));
			postfixList.add(exp);
		}
		else if (leftNode.getNodeType() == ASTNode.EXPRESSION && rightNode.getNodeType() != ASTNode.EXPRESSION) {
			postfixList.addAll(astToPostfix((ASTExpression) leftNode));
			postfixList.add(rightNode);
			postfixList.add(exp);
		}
		else {
			postfixList.add(leftNode);
			postfixList.add(rightNode);
			postfixList.add(exp);
		}
		
		return postfixList;
	}

	//TODO - YAGNI?
	public static ArrayList<Object> astToPostfixString(ASTExpression exp) {
		
		ArrayList<Object> postfixList = new ArrayList<Object>(0);
		ASTNode leftNode = exp.getLeftNode();
		ASTNode rightNode = exp.getRightNode();
		//System.out.println(exp.getExpressionType());
		
		if (exp.getExpressionType() == ASTExpression.SINGLE_EXP) {
			postfixList.add(getNodeData(leftNode));
		}
		else if (leftNode.getNodeType() == ASTNode.EXPRESSION && rightNode.getNodeType() == ASTNode.EXPRESSION) {
			postfixList.addAll(astToPostfixString((ASTExpression) leftNode));
			postfixList.addAll(astToPostfixString((ASTExpression) rightNode));
			postfixList.add(getNodeData(exp));
		}
		else if (leftNode.getNodeType() != ASTNode.EXPRESSION && rightNode.getNodeType() == ASTNode.EXPRESSION) {
			postfixList.add(getNodeData(leftNode));
			postfixList.addAll(astToPostfixString((ASTExpression) rightNode));
			postfixList.add(getNodeData(exp));
		}
		else if (leftNode.getNodeType() == ASTNode.EXPRESSION && rightNode.getNodeType() != ASTNode.EXPRESSION) {
			postfixList.addAll(astToPostfixString((ASTExpression) leftNode));
			postfixList.add(getNodeData(rightNode));
			postfixList.add(getNodeData(exp));
		}
		else {
			postfixList.add(getNodeData(leftNode));
			postfixList.add(getNodeData(rightNode));
			postfixList.add(getNodeData(exp));
		}
		
		return postfixList;
	}
	
	//TODO - YAGNI?
	private static Object getNodeData(ASTNode node) {
		
		Object value = null;
		
		if (node.getNodeType() == ASTNode.VARIABLE) {
			ASTVariable var = null;
			var = (ASTVariable) node;
			value = var.getName();
			
		}
		else if (node.getNodeType() == ASTNode.NUMBER) {
			ASTNumber num = null;
			num = (ASTNumber) node;
			value = num.getValue();
		}
		else {
			ASTExpression exp = (ASTExpression) node;
			int type = exp.getExpressionType();
			
			if (type == ASTExpression.MINUS_EXP) {
				value = "-";
			}
			else if (type == ASTExpression.ADD_EXP) {
				value = "+";
			}
			else if (type == ASTExpression.DIVIDE_EXP) {
				value = "/";
			}
			else if (type == ASTExpression.MULTIPLY_EXP) {
				value = "*";
			}
			else if (type == ASTExpression.AND_EXP) {
				value = "&&";
			}
			else if (type == ASTExpression.OR_EXP) {
				value = "||";
			}
			else if (type == ASTExpression.NOT_EXP) {
				value = "!";
			}
			else if (type == ASTExpression.LOWER_EXP) {
				value = "<";
			}
			else if (type == ASTExpression.UPPER_EXP) {
				value = ">";
			}
			else if (type == ASTExpression.LOWER_EQUAL_EXP) {
				value = "<=";
			}
			else if (type == ASTExpression.UPPER_EQUAL_EXP) {
				value = ">=";
			}
			else if (type == ASTExpression.EQUAL_EXP) {
				value = "==";
			}
			else if (type == ASTExpression.NOT_EQUAL_EXP) {
				value = "!=";
			}
		}
		return value;
	}
}
