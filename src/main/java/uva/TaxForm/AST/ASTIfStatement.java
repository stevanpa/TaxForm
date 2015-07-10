package uva.TaxForm.AST;

public class ASTIfStatement extends ASTStatement {
	
	ASTIfStatement(AST ast) {
		super(ast);
	}

	int getNodeType0() {
		return IF_STATEMENT;
	}

}
