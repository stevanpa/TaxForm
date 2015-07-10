package uva.TaxForm.Visitors;

import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.antlr4.TaxFormParser.FormContext;
import uva.TaxForm.antlr4.TaxFormParser.IfConditionContext;
import uva.TaxForm.antlr4.TaxFormParser.QuestionContext;

public class VisitorFormToAST {

	public static void visit( VisitorToAST ctfv, FormContext ctx, ASTNode node ) {
		for( int i=0; i<ctx.children.size(); i++ ) {
			//QuestionContext
			if (ctx.getChild(i).getClass().equals(uva.TaxForm.antlr4.TaxFormParser.QuestionContext.class)) {
				node.addChild(ctfv.visitQuestion((QuestionContext) ctx.getChild(i), node));
				
			}
			//IfConditionContext
			else if (ctx.getChild(i).getClass().equals(uva.TaxForm.antlr4.TaxFormParser.IfConditionContext.class)) {
				ctfv.visitIfCondition((IfConditionContext) ctx.getChild(i), node);
			}
		}
	}
}
