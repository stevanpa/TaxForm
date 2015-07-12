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
import uva.TaxForm.antlr4.TaxFormBaseVisitor;
import uva.TaxForm.antlr4.TaxFormParser;
import uva.TaxForm.antlr4.TaxFormParser.BlockContext;
import uva.TaxForm.antlr4.TaxFormParser.ExpressionContext;
import uva.TaxForm.antlr4.TaxFormParser.IfConditionContext;
import uva.TaxForm.antlr4.TaxFormParser.MinusExpressionContext;
import uva.TaxForm.antlr4.TaxFormParser.QuestionContext;
import uva.TaxForm.antlr4.TaxFormParser.SingleExpressionContext;
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
	
	public ASTNode visitBlock( @NotNull TaxFormParser.BlockContext ctx, ASTNode node ) {
		
		//System.out.println(node.getNodeType());
		ASTBlock block = AST.newBlock();
		if (node.getNodeType() == ASTNode.FORM) {
			block = (ASTBlock) node;
		} else {
			block.setParent(node);
		}
		//System.out.println("StartBlockVisit");
		for (int i=0; i<ctx.getChildCount(); i++) {
			try {
				block.addChild( visitQuestion((QuestionContext) ctx.getChild(i), block) );
				//System.out.println("Added Question to Block");
			} catch (ClassCastException e) {}
			
			try {
				block.addChild( visitIfCondition((IfConditionContext) ctx.getChild(i), block) );
				//System.out.println("Added IfStatement to Block");
			} catch (ClassCastException e1) {}
		}
		//System.out.println("EndBlockVisit");
		return block;
	}
	
	public ASTNode visitQuestion( @NotNull TaxFormParser.QuestionContext ctx, ASTNode node ) {
		
		ASTQuestion question = AST.newQuestion();
		question.setParent(node);
		question.setLabel(ctx.label().getText().substring(1, ctx.label().getText().length()-1));
		
		ASTExpression expression = AST.newExpresion();
		expression.setParent(question);
		question.setExpression(expression);
		
		ASTVariable variable = AST.newVariable();
		variable.setParent(expression);
		variable.setName(ctx.varName().getText());
		visitVarType( (VarTypeContext) ctx.varType(), variable );
		
		expression.setLeftNode(variable);
		
		if (ctx.ASSIGN() == null) {
			expression.setExpressionType(ASTExpression.SINGLE_EXP);
		} else {
			expression.setExpressionType(ASTExpression.ASSIGN_EXP);
			//System.out.println(ctx.expression().size());
			for (int i=0; i<ctx.expression().size(); i++) {
				//System.out.println(ctx.expression(i).getText());
				expression.setRightNode(visitExpression( (ExpressionContext) ctx.expression(i), expression ));
			}
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
	
	public ASTNode visitExpression( @NotNull TaxFormParser.ExpressionContext ctx, ASTNode node ) {
		
		ASTExpression expNode = AST.newExpresion();
		expNode.setParent(node);
		//System.out.println(ctx.getChildCount());

		try {
			expNode.setLeftNode(visitSingleExpression((SingleExpressionContext) ctx, expNode));
		} catch (ClassCastException e) {
			//System.out.println(e.getMessage());
		}
		try {
			expNode.setRightNode(visitMinusExpression((MinusExpressionContext) ctx, expNode));
			expNode.setExpressionType(ASTExpression.MINUS_EXP);
		} catch (ClassCastException e) {
			//System.out.println(e.getMessage());
		}

		return expNode;
	}
	
	public ASTNode visitSingleExpression( @NotNull TaxFormParser.SingleExpressionContext ctx, ASTNode node ) {
		ArrayList<ASTNode> nodeList = VisitAST.getNodesByType(node, ASTNode.VARIABLE);
		ASTVariable var = null;
		
		//System.out.println(ctx.getChild(0).getText());
		for (ASTNode n: nodeList) {
			ASTVariable tempVar = (ASTVariable) n;
			
			if (tempVar.getName().equals(ctx.getChild(0).getText())) {
				var = tempVar;
			}
		}
		//if (var != null) System.out.println(var.getName());
		return var;
	}
	
	private ASTNode visitMinusExpression( @NotNull TaxFormParser.MinusExpressionContext ctx, ASTNode node ) {
		return visitSingleExpression( (SingleExpressionContext) ctx.getChild(ctx.getChildCount()-1), node );
	}
	
	public ASTNode visitIfCondition( @NotNull TaxFormParser.IfConditionContext ctx, ASTNode node ) {
		ASTIfStatement ifStatement = AST.newIfStatement();
		ifStatement.setParent(node);
		//System.out.println("StartVisit IfCondition");
		for (int i=0; i<ctx.getChildCount(); i++) {
			//System.out.println(ctx.getChild(i).getText());
			try {
				ifStatement.setCondition( visitExpression((ExpressionContext) ctx.getChild(i), ifStatement) );
			} catch (ClassCastException e) {}
			
			try {
				visitBlock((BlockContext) ctx.getChild(i), ifStatement);
			} catch (ClassCastException e1) {}
		}
		//System.out.println("EndVisit IfCondition");
		return ifStatement;
	}
}
