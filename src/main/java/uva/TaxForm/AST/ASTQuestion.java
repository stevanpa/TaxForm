package uva.TaxForm.AST;

public class ASTQuestion extends ASTNode {

	private String label;
	private ASTExpression expression;
	private boolean computed = false;
	
	ASTQuestion(AST ast) {
		super(ast);
	}

	@Override
	int getNodeType0() {
		return QUESTION;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isComputed() {
		return computed;
	}

	public void setComputed(boolean computed) {
		this.computed = computed;
	}

	public ASTExpression getExpression() {
		return expression;
	}

	public void setExpression(ASTExpression expression) {
		this.expression = expression;
	}

}
