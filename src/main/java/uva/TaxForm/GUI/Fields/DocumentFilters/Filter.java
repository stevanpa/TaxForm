package uva.TaxForm.GUI.Fields.DocumentFilters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import uva.TaxForm.AST.ASTVariable;

public abstract class Filter extends DocumentFilter {
	
	private ASTVariable variable = null;
	private String regex = "";
	
	public Filter(ASTVariable var, String regex) {
		this.variable = var;
		this.regex = regex;
	}
	
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
			throws BadLocationException {
		
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);
		
		if (test(sb.toString())) {
			super.insertString(fb,  offset,  string,  attr);
			variable.setValue(sb.toString());
		} 
		else {
			// warn the user and don't allow the insert
		}
	}
	
	private boolean test(String text) {
		if (text.matches(regex)) {
			return true;
		} 
		else {
			return false;
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) 
			throws BadLocationException {
		
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);
		
		if (test(sb.toString())) {
			super.replace(fb, offset, length, text, attr);
			variable.setValue(sb.toString());
		} 
		else {
			// warn the user and don't allow the insert
		}
	}
	
	@Override
	public void remove(FilterBypass fb, int offset, int length)
			throws BadLocationException {
		
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);
		
		if (test(sb.toString())) {
			super.remove(fb, offset, length);
			variable.setValue(sb.toString());
		} 
		else {
			// warn the user and don't allow the insert
		}
	}
}
