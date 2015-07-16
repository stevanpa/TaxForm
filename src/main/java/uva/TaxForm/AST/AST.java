package uva.TaxForm.AST;

public final class AST {

	public static AST newAST() {
		return new AST();
	}
	
	public static ASTForm newForm() {
		return new ASTForm(newAST());
	}

	public static ASTQuestion newQuestion() {
		return new ASTQuestion(newAST());
	}
	
	public static ASTIfStatement newIfStatement() {
		return new ASTIfStatement(newAST());
	}
	
	public static ASTExpression newExpression() {
		return new ASTExpression(newAST());
	}
	
	public static ASTVariable newVariable() {
		return new ASTVariable(newAST());
	}
	
	public static ASTNumber newNumber() {
		return new ASTNumber(newAST());
	}
	
	public static ASTBlock newBlock() {
		return new ASTBlock(newAST());
	}
}
