package uva.TaxForm.AST;

import java.util.ArrayList;

public class ASTForm extends ASTNode{
	
	private String name;
	private ArrayList<Object> store = new ArrayList<Object>(0);
	
	ASTForm(AST ast) {
		super(ast);
	}

	int getNodeType0() {
		return FORM;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addChild(ASTNode node) {
		this.store.add(node);
	}
	
	public int size() {
		return this.store.size();
	}
	
	public ASTNode get(int index) {
		return (ASTNode) this.store.get(index);
	}

}
