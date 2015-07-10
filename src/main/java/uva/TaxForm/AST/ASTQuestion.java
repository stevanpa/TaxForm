package uva.TaxForm.AST;

public class ASTQuestion extends ASTNode {

	private String label;
	private String name;
	private String type;
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isComputed() {
		return computed;
	}

	public void setComputed(boolean computed) {
		this.computed = computed;
	}

}
