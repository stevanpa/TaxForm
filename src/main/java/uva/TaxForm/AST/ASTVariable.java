package uva.TaxForm.AST;

import uva.TaxForm.Interfaces.IVariable;

public class ASTVariable extends ASTNode implements IVariable {
	
	private String name = "";
	private int type = 0;
	private String value = "";
	
	ASTVariable(AST ast) {
		super(ast);
	}

	int getNodeType0() {
		return ASTNode.VARIABLE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
