package uva.TaxForm.AST.Visitors;

import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.antlr4.TaxFormParser.FormContext;
import uva.TaxForm.antlr4.TaxFormParser.QuestionContext;

public class VisitorForm {

	public static void visit( Visitor ctfv, FormContext ctx, ASTNode form ) {
		for( int i=0; i<ctx.children.size(); i++ ) {
			//QuestionContext
			if (ctx.getChild(i).getClass().equals(uva.TaxForm.antlr4.TaxFormParser.QuestionContext.class)) {
				form.addChild(ctfv.visitQuestion((QuestionContext) ctx.getChild(i), form));
				
			}
		}
	}
}
