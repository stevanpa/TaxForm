package uva.TaxForm.AST;

public class ASTQuestion extends ASTNode {

	private String label;
	private ASTVariable variable;
	private boolean computed = false;
	
	ASTQuestion(AST ast) {
		super(ast);
	}

	int getNodeType0() {
		return QUESTION;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getName() {
		return this.variable.getName();
	}
	
	public int getType() {
		return this.variable.getType();
	}

	public boolean isComputed() {
		return computed;
	}

	public void setComputed(boolean computed) {
		this.computed = computed;
	}

	public ASTNode getVariable() {
		return variable;
	}

	public void setVariable(ASTVariable variable) {
		this.variable = variable;
	}

}
