package uva.TaxForm.AST;

import uva.TaxForm.Interfaces.INumber;

public class ASTNumber extends ASTNode implements INumber {
	
	private String value;
	private int type = 0;
	
	ASTNumber(AST ast) {
		super(ast);
	}

	int getNodeType0() {
		return ASTNode.NUMBER;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
