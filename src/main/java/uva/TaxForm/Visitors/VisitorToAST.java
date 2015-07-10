package uva.TaxForm.Visitors;

import org.antlr.v4.runtime.misc.NotNull;

import uva.TaxForm.AST.AST;
import uva.TaxForm.AST.ASTForm;
import uva.TaxForm.AST.ASTIfStatement;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.AST.ASTStatement;
import uva.TaxForm.antlr4.TaxFormBaseVisitor;
import uva.TaxForm.antlr4.TaxFormParser;
import uva.TaxForm.antlr4.TaxFormParser.AllMightyContext;
import uva.TaxForm.antlr4.TaxFormParser.QuestionContext;

public class VisitorToAST extends TaxFormBaseVisitor<Object> {

	public ASTNode visitForm( @NotNull TaxFormParser.FormContext ctx ) {
		//System.out.println(ctx.getText());
		//System.out.println(ctx.varName().getText());
		ASTForm form = AST.newForm();
		form.setName(ctx.varName().getText());
		VisitorFormToAST.visit(this, ctx, form);
		
		return form;
	}
	
	public ASTNode visitQuestion( @NotNull TaxFormParser.QuestionContext ctx, ASTNode node ) {
		ASTQuestion question = AST.newQuestion();
		//System.out.println(ctx.label().getText());
		//System.out.println(ctx.varName().getText());
		//System.out.println(ctx.varType().getText());
		//System.out.println(ctx.computed());
		if (ctx.computed() != null) {
			question.setLabel(ctx.label().getText().substring(1, ctx.label().getText().length()-1));
			question.setName(ctx.varName().getText());
			question.setParent(node);
			visitComputed( ctx.computed(), question );
		} else {
			question.setLabel(ctx.label().getText().substring(1, ctx.label().getText().length()-1));
			question.setName(ctx.varName().getText());
			question.setType(ctx.varType().getText());
			question.setParent(node);
		}
		
		return question;
	}
	
	public ASTNode visitIfCondition( @NotNull TaxFormParser.IfConditionContext ctx, ASTNode node ) {
		ASTIfStatement ifStatement = AST.newIfStatement();
		ifStatement.setParent(node);
		
		//System.out.println(ctx.condition().toString());
		for (int i=0; i<ctx.getChildCount(); i++) {
			if (ctx.getChild(i).getChildCount() > 0) {
				if (ctx.getChild(i).getClass().equals(uva.TaxForm.antlr4.TaxFormParser.AllMightyContext.class)) {
					visitAllMightyContext((AllMightyContext) ctx.getChild(i), ifStatement);
					//System.out.println(ifStatement.getStatementType());
				} else if (ctx.getChild(i).getClass().equals(uva.TaxForm.antlr4.TaxFormParser.QuestionContext.class)) {
					visitQuestion( (QuestionContext) ctx.getChild(i), node );
				}
				//System.out.println(ctx.getChild(i).getClass());
				//visitIfCondition( (IfConditionContext) ctx.getChild(i), form );
			}
			//System.out.println(ctx.getChild(i).getChildCount());
		}
		//System.out.println();
		
		return ifStatement;
	}
	
	public ASTNode visitAllMightyContext( @NotNull TaxFormParser.AllMightyContext ctx, ASTNode node ) {
		
		for (int i=0; i<ctx.getChildCount(); i++) {
			if (ctx.getChild(i).getClass().equals(uva.TaxForm.antlr4.TaxFormParser.SingleExpressionContext.class)) {
				((ASTStatement) node).setStatementType(ASTIfStatement.SINGLE_EXP);
			}
		}
		return node;
	}
	
	public ASTNode visitSingleExpression( @NotNull TaxFormParser.SingleExpressionContext ctx, ASTNode node ) {
		return node;
	}
	
	public ASTNode visitComputed( @NotNull TaxFormParser.ComputedContext ctx, ASTNode node) {
		return node;
	}
}
