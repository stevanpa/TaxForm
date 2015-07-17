package uva.TaxForm.Visitors;

import java.util.ArrayList;

import org.antlr.v4.runtime.misc.NotNull;

import uva.TaxForm.AST.AST;
import uva.TaxForm.AST.ASTBlock;
import uva.TaxForm.AST.ASTExpression;
import uva.TaxForm.AST.ASTForm;
import uva.TaxForm.AST.ASTIfStatement;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.AST.ASTVariable;
import uva.TaxForm.Utils.ContextUtils;
import uva.TaxForm.Utils.ShuntingYardAlgorithm;
import uva.TaxForm.antlr4.TaxFormBaseVisitor;
import uva.TaxForm.antlr4.TaxFormParser;
import uva.TaxForm.antlr4.TaxFormParser.BlockContext;
import uva.TaxForm.antlr4.TaxFormParser.ExpressionContext;
import uva.TaxForm.antlr4.TaxFormParser.IfConditionContext;
import uva.TaxForm.antlr4.TaxFormParser.QuestionContext;
import uva.TaxForm.antlr4.TaxFormParser.VarTypeContext;

public class VisitorToAST extends TaxFormBaseVisitor<Object> {

	public ASTNode visitForm( @NotNull TaxFormParser.FormContext ctx ) {
		ASTForm form = AST.newForm();
		form.setName(ctx.varName().getText());
		
		for (int i=0; i<ctx.getChildCount(); i++) {
			try {
				visitBlock( (BlockContext) ctx.getChild(i), form );
			} catch (ClassCastException e) {}
		}
		
		return form;
	}
	
	public ASTBlock visitBlock( @NotNull TaxFormParser.BlockContext ctx, ASTBlock block ) {

		//System.out.println("StartBlockVisit");
		for (int i=0; i<ctx.getChildCount(); i++) {
			try {
				ASTIfStatement ifStatement = AST.newIfStatement();
				ifStatement.setParent(block);
				block.addChild(ifStatement);
				visitIfCondition((IfConditionContext) ctx.getChild(i), ifStatement);
				//System.out.println("Added IfStatement to Block");
			} catch (ClassCastException e) {}
			try {
				ASTQuestion question = AST.newQuestion();
				question.setParent(block);
				block.addChild( visitQuestion((QuestionContext) ctx.getChild(i), question) );
				//System.out.println("Added Question to Block");
			} catch (ClassCastException e) {}
		}
		//System.out.println("EndBlockVisit");
		return block;
	}
	
	public ASTNode visitQuestion( @NotNull TaxFormParser.QuestionContext ctx, ASTQuestion question ) {
		
		question.setLabel(ctx.label().getText().substring(1, ctx.label().getText().length()-1));
		
		ASTExpression expression = AST.newExpression();
		expression.setParent(question);
		question.setExpression(expression);
		
		ASTVariable variable = AST.newVariable();
		variable.setParent(expression);
		variable.setName(ctx.varName().getText());
		visitVarType( (VarTypeContext) ctx.varType(), variable );
		
		expression.setLeftNode(variable);
		
		if (ctx.ASSIGN() != null) {
			//expression.setExpressionType(ASTExpression.EXP);
			expression.setExpressionType(ASTExpression.ASSIGN_EXP);
			//System.out.println(ctx.expression().size());
			for (int i=0; i<ctx.expression().size(); i++) {
				//System.out.println(ctx.expression(i).getText());
				ASTExpression rightNodeExp = AST.newExpression();
				rightNodeExp.setParent(expression);
				expression.setRightNode(rightNodeExp);
				visitExpression( (ExpressionContext) ctx.expression(i), rightNodeExp );
			}
		}
		else {
			expression.setExpressionType(ASTExpression.SINGLE_EXP);
		}
		
		return question;
	}

	public ASTNode visitVarType( @NotNull VarTypeContext varType, ASTVariable variableNode ) {
		
		if (varType.BOOLEAN() != null) {
			variableNode.setType(ASTVariable.BOOLEAN);
		} else if (varType.INT() != null) {
			variableNode.setType(ASTVariable.INT);
		} else if (varType.MONEY() != null) {
			variableNode.setType(ASTVariable.MONEY);
		} else if (varType.STRING() != null) {
			variableNode.setType(ASTVariable.STRING);
		}
		
		return variableNode;
	}
	
	public void visitExpression( @NotNull TaxFormParser.ExpressionContext ctx, ASTExpression exp ) {

		ArrayList<Object> infixList = ContextUtils.expressionToInfix(ctx, null);
		ShuntingYardAlgorithm.infixToAST(infixList, exp);
		
		//System.out.println(ShuntingYardAlgorithm.astToPostfix(exp));
	}
	
	public ASTIfStatement visitIfCondition( @NotNull TaxFormParser.IfConditionContext ctx, ASTIfStatement ifStatement ) {
		
		//System.out.println("StartVisit IfCondition");
		for (int i=0; i<ctx.getChildCount(); i++) {
			//System.out.println(ctx.getChild(i).getText());
			//System.out.println(i);
			try {
				ASTExpression exp = AST.newExpression();
				exp.setParent(ifStatement);
				ifStatement.setExpression(exp);
				visitExpression((ExpressionContext) ctx.getChild(i), exp);
				//System.out.println(ctx.getChild(i).getText());
			} catch (ClassCastException e) {}

			try {
				if (ifStatement.getLeftNode() == null) {
					ASTBlock block = AST.newBlock();
					block.setParent(ifStatement);
					ifStatement.setLeftNode(block);
					visitBlock((BlockContext) ctx.getChild(i), block);
				} else {
					ASTBlock block = AST.newBlock();
					block.setParent(ifStatement);
					ifStatement.setRightNode(block);
					visitBlock((BlockContext) ctx.getChild(i), block);
				}
				//System.out.println(ctx.getChild(i).getText());
			} catch (ClassCastException e) {}
		}
		//System.out.println("EndVisit IfCondition");
		return ifStatement;
	}
}
