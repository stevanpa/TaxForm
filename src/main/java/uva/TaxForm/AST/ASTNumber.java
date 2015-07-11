package uva.TaxForm.AST;

public class ASTNumber extends ASTNode {

	public final static int INT 		= 1;
	public final static int DOUBLE 		= 2;
	
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
