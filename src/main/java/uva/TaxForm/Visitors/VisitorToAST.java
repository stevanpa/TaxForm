package uva.TaxForm.Visitors;

import org.antlr.v4.runtime.misc.NotNull;

import uva.TaxForm.AST.AST;
import uva.TaxForm.AST.ASTBlock;
import uva.TaxForm.AST.ASTForm;
import uva.TaxForm.AST.ASTIfStatement;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.antlr4.TaxFormBaseVisitor;
import uva.TaxForm.antlr4.TaxFormParser;
import uva.TaxForm.antlr4.TaxFormParser.BlockContext;
import uva.TaxForm.antlr4.TaxFormParser.ExpressionContext;
import uva.TaxForm.antlr4.TaxFormParser.IfConditionContext;
import uva.TaxForm.antlr4.TaxFormParser.QuestionContext;

public class VisitorToAST extends TaxFormBaseVisitor<Object> {

	public ASTNode visitForm( @NotNull TaxFormParser.FormContext ctx ) {
		//System.out.println(ctx.getText());
		//System.out.println(ctx.varName().getText());
		ASTForm form = AST.newForm();
		form.setName(ctx.varName().getText());
		//System.out.println(ctx.getChildCount());
		for (int i=0; i<ctx.getChildCount(); i++) {
			try {
				visitBlock( (BlockContext) ctx.getChild(i), form );
			} catch (ClassCastException e) {}
		}
		
		
		return form;
	}
	
	public ASTNode visitBlock( @NotNull TaxFormParser.BlockContext ctx, ASTNode node ) {
		
		ASTBlock block = AST.newBlock();
		block.setParent(node);
		System.out.println("StartBlockVisit");
		for (int i=0; i<ctx.getChildCount(); i++) {
			// You will encounter a Question or a IFstatment node, try catch?
			try {
				block.addChild( visitQuestion((QuestionContext) ctx.getChild(i), block) );
				System.out.println("Added Question to Block");
			} catch (ClassCastException e) {}
			
			try {
				block.addChild( visitIfCondition((IfConditionContext) ctx.getChild(i), block) );
				System.out.println("Added IfStatement to Block");
			} catch (ClassCastException e1) {}
		}
		System.out.println("EndBlockVisit");
		return block;
	}
	
	public ASTNode visitQuestion( @NotNull TaxFormParser.QuestionContext ctx, ASTNode node ) {
		ASTQuestion question = AST.newQuestion();
		question.setParent(node);
		//System.out.println(ctx.label().getText());
		//System.out.println(ctx.varName().getText());
		//System.out.println(ctx.varType().getText());
		question.setLabel(ctx.label().getText().substring(1, ctx.label().getText().length()-1));
		
		return question;
	}
	
	public ASTNode visitIfCondition( @NotNull TaxFormParser.IfConditionContext ctx, ASTNode node ) {
		ASTIfStatement ifStatement = AST.newIfStatement();
		ifStatement.setParent(node);
		System.out.println("StartVisit IfCondition");
		for (int i=0; i<ctx.getChildCount(); i++) {
			//System.out.println(ctx.getChild(i).getText());
			try {
				ifStatement.setCondition( visitExpression((ExpressionContext) ctx.getChild(i), ifStatement) );
			} catch (ClassCastException e) {}
			
			try {
				visitBlock((BlockContext) ctx.getChild(i), ifStatement);
			} catch (ClassCastException e1) {}
		}
		System.out.println("EndVisit IfCondition");
		return ifStatement;
	}
	
	public ASTNode visitExpression( @NotNull TaxFormParser.ExpressionContext ctx, ASTNode node ) {
		System.out.println("Expression");
		return node;
	}
	
	public ASTNode visitSingleExpression( @NotNull TaxFormParser.SingleExpressionContext ctx, ASTNode node ) {
		return node;
	}
	
	public ASTNode visitComputed( @NotNull TaxFormParser.ComputedContext ctx, ASTNode node) {
		return node;
	}
}
