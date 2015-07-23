package uva.TaxForm.AST;

public class ASTForm extends ASTBlock{
	
	private String name;
	
	ASTForm(AST ast) {
		super(ast);
	}

	@Override
	int getNodeType0() {
		return FORM;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
