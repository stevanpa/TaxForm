package uva.TaxForm.AST;

public class ASTVariable extends ASTNode {

	public final static int BOOLEAN 	= 1;
	public final static int STRING 		= 2;
	public final static int INT 		= 3;
	public final static int DATE 		= 4;
	public final static int DECIMAL 	= 5;
	public final static int MONEY 		= 6;
	
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
