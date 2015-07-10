package uva.TaxForm.AST.Visitors;

import org.antlr.v4.runtime.misc.NotNull;

import uva.TaxForm.AST.AST;
import uva.TaxForm.AST.ASTForm;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.antlr4.TaxFormBaseVisitor;
import uva.TaxForm.antlr4.TaxFormParser;

public class Visitor extends TaxFormBaseVisitor<Object> {

	public ASTNode visitForm( @NotNull TaxFormParser.FormContext ctx ) {
		//System.out.println(ctx.getText());
		//System.out.println(ctx.varName().getText());
		ASTForm form = AST.newForm();
		form.setName(ctx.varName().getText());
		VisitorForm.visit(this, ctx, form);
		
		return form;
	}
	
	public ASTNode visitQuestion( @NotNull TaxFormParser.QuestionContext ctx, ASTNode form ) {
		//System.out.println(ctx.label().getText());
		//System.out.println(ctx.varName().getText());
		//System.out.println(ctx.varType().getText());
		ASTQuestion question = AST.newQuestion();
		question.setLabel(ctx.label().getText());
		question.setName(ctx.varName().getText());
		question.setParent(form);
		
		return question;
	}
}
