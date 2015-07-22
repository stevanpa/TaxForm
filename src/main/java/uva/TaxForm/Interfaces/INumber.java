package uva.TaxForm.Interfaces;

import uva.TaxForm.AST.ASTVariable;

public interface INumber {

	public final static int INT 		= ASTVariable.INT;
	public final static int DOUBLE 		= ASTVariable.DOUBLE;
	
	public String getValue();
	public void setValue(String value);
	public int getType();
	public void setType(int type);
}
