package uva.TaxForm.Interfaces;

public interface IVariable {

	public final static int BOOLEAN 	= 1;
	public final static int STRING 		= 2;
	public final static int INT 		= 3;
	public final static int DATE 		= 4;
	public final static int DOUBLE 		= 5;
	public final static int DECIMAL 	= DOUBLE;
	public final static int MONEY 		= DOUBLE;
	
	public String getName();
	public void setName(String name);
	public int getType();
	public void setType(int type);
	public String getValue();
	public void setValue(String value);
}
