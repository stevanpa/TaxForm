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
	
}
