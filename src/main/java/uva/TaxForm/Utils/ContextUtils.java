package uva.TaxForm.Utils;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.misc.NotNull;

import uva.TaxForm.AST.AST;
import uva.TaxForm.AST.ASTExpression;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTNumber;
import uva.TaxForm.AST.ASTVariable;
import uva.TaxForm.Visitors.VisitAST;
import uva.TaxForm.antlr4.TaxFormParser;
import uva.TaxForm.antlr4.TaxFormParser.ExpressionContext;

public class ContextUtils {

	public static ArrayList<Object> expressionToInfix( @NotNull TaxFormParser.ExpressionContext ctx, ArrayList<Object> infixList ) {
		
		infixList = (infixList != null)? infixList : new ArrayList<Object>(0);
		
		for (int i=0; i<ctx.getChildCount(); i++) {
			if (ctx.getChild(i).getChildCount() > 1) {
				expressionToInfix((ExpressionContext) ctx.getChild(i), infixList);
			} else {
				infixList.add(ctx.getChild(i).getText());
			}
		}
		return infixList;
	}
	
	public static ASTExpression postfixToASTExpression(ArrayList<Object> postfixList, ASTExpression parentNode) {
		
		ASTExpression expr = null;
		
		// ASTVariable
		if (postfixList.size() == 1) {
			ASTNode node = getNode(postfixList.get(0), parentNode);
			try {
				ASTVariable var = (ASTVariable) node;
				var.setParent(parentNode);
				parentNode.setExpressionType(ASTExpression.SINGLE_EXP);
				parentNode.setLeftNode(var);
			} catch (ClassCastException e) {}
		} 
		
		// ASTExpression
		else {
			// Convert list to stack
			Stack<Object> stack = new Stack<Object>();
			stack.addAll(postfixList);
			
			while (!stack.isEmpty()) {
				Object item = stack.pop();
				ASTNode node = getNode(item, parentNode);
				
				if (node.getNodeType() == ASTNode.EXPRESSION) {
					System.out.println(item);
					
					expr = addExpression(expr, node);
				}
				else if (node.getNodeType() == ASTNode.VARIABLE) {
					System.out.println(item);
					
					if (!addVariable(expr, node)) {
						while (expr.getLeftNode() != null || expr.getRightNode() != null) {
							expr = (ASTExpression) expr.getParent();
						}
						stack.add(item);
					}
				}
				else if (node.getNodeType() == ASTNode.NUMBER) {
					System.out.println(item);
					
					if (!addNumber(expr, node)) {
						while (expr.getLeftNode() != null || expr.getRightNode() != null) {
							expr = (ASTExpression) expr.getParent();
						}
						stack.add(item);
					}
				}
			}
		}
		
		return null;
	}
	
	public static void astExpressionToPostfix(ASTExpression exp) {
		
	}
	
	private static ASTExpression addExpression(ASTExpression expr, ASTNode node) {
		if (expr == null) {
			expr = (ASTExpression) node;
		}
		else if (expr.getRightNode() == null) {
			ASTExpression newNode = (ASTExpression) node;
			newNode.setParent(expr);
			expr.setRightNode(newNode);
			expr = newNode;
		}
		else {
			ASTExpression newNode = (ASTExpression) node;
			newNode.setParent(expr);
			expr.setLeftNode(newNode);
			expr = newNode;
		}
		
		return expr;
	}
	
	private static boolean addVariable(ASTExpression expr, ASTNode node) {
		
		boolean valid = false;
		
		if (expr.getRightNode() == null) {
			ASTVariable newNode = (ASTVariable) node;
			newNode.setParent(expr);
			expr.setRightNode(newNode);
			valid = true;
		}
		else if (expr.getRightNode() != null && expr.getLeftNode() == null) {
			ASTVariable newNode = (ASTVariable) node;
			newNode.setParent(expr);
			expr.setLeftNode(newNode);
			valid = true;
		}
		
		return valid;
	}
	
	private static boolean addNumber(ASTExpression expr, ASTNode node) {
		
		boolean valid = false;
		
		if (expr.getRightNode() == null) {
			ASTNumber newNode = (ASTNumber) node;
			newNode.setParent(expr);
			expr.setRightNode(newNode);
			valid = true;
		}
		else if (expr.getRightNode() != null && expr.getLeftNode() == null) {
			ASTNumber newNode = (ASTNumber) node;
			newNode.setParent(expr);
			expr.setLeftNode(newNode);
			valid = true;
		}
		
		return valid;
	}

	private static void getNodeValue(ASTNode node) {
		
	}
	
	private static ASTNode getNode(Object item, ASTNode parentNode) {
		
		ASTNode node = null;
		ASTExpression expr = null;
		ASTVariable var = null;
		ASTNumber num = null;
		
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
		else if (Pattern.matches("^[0-9]+[.][0-9]{0,2}$", item.toString())) {
			num = AST.newNumber();
			num.setType(ASTNumber.DOUBLE);
			num.setValue(item.toString());
		}
		else if (Pattern.matches("^[0-9]+$", item.toString())) {
			num = AST.newNumber();
			num.setType(ASTNumber.INT);
			num.setValue(item.toString());
		}
		else {
			ArrayList<ASTNode> nodeList = VisitAST.getNodesByType(parentNode, ASTNode.VARIABLE);
			
			for (ASTNode n: nodeList) {
				ASTVariable tempVar = (ASTVariable) n;
				
				if (tempVar.getName().equals(item.toString())) {
					var = tempVar;
				}
			}
		}
		
		if (expr != null) {
			node = expr;
		}
		else if (var != null) {
			node = var;
		}
		else if (num != null) {
			node = num;
		}
		
		return node;
	}
}
