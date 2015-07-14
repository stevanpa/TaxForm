package uva.TaxForm.GUI.Filters;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import uva.TaxForm.AST.ASTVariable;

public class MoneyTextField extends JTextField {
	
    public MoneyTextField(ASTVariable var) {
    	super();
    	this.setName(var.getName());
    	this.setText("0.00");
    	PlainDocument doc = (PlainDocument) this.getDocument();
    	doc.setDocumentFilter(new MoneyFilter(var));
    	
    	this.addFocusListener(new FocusAdapter() {
    		public void focusGained(FocusEvent evt) {
    			SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						selectAll();
					}
    			});
    		}
    	});
    }
    
	class MoneyFilter extends DocumentFilter {
		
		private ASTVariable variable = null;
		
		public MoneyFilter(ASTVariable var) {
			variable = var;
		}
		
    	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
    			throws BadLocationException {
    		
    		Document doc = fb.getDocument();
    		StringBuilder sb = new StringBuilder();
    		sb.append(doc.getText(0, doc.getLength()));
    		sb.insert(offset, string);
    		
    		if (test(sb.toString())) {
    			/*System.out.println(doc.getText(0, doc.getLength()));
    			string = String.format("%.2f", Double.parseDouble(sb.toString()));
    			System.out.println(string);*/
    			super.insertString(fb,  offset,  string,  attr);
    			variable.setValue(sb.toString());
    		} else {
    			// warn the user and don't allow the insert
    		}
    	}
    	
    	private boolean test(String text) {
    		if (text.matches("^[0-9]+[.]?[0-9]{0,2}$")) {
    			return true;
    		} else {
    			return false;
    		}
    	}

    	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) 
    			throws BadLocationException {
    		
    		Document doc = fb.getDocument();
    		StringBuilder sb = new StringBuilder();
    		sb.append(doc.getText(0, doc.getLength()));
    		sb.replace(offset, offset + length, text);
    		
    		if (test(sb.toString())) {
    			/*System.out.println(doc.getText(0, doc.getLength()));
    			text = String.format("%.2f", Double.parseDouble(sb.toString()));
    			System.out.println(text);*/
    			super.replace(fb, offset, length, text, attr);
    			variable.setValue(sb.toString());
    		} else {
    			// warn the user and don't allow the insert
    		}
    	}
    	
    	public void remove(FilterBypass fb, int offset, int length)
    			throws BadLocationException {
    		
    		Document doc = fb.getDocument();
    		StringBuilder sb = new StringBuilder();
    		sb.append(doc.getText(0, doc.getLength()));
    		sb.delete(offset, offset + length);
    		
    		if (test(sb.toString())) {
    			//string = String.format("%.2f", string);
    			super.remove(fb, offset, length);
    			variable.setValue(sb.toString());
    		} else {
    			// warn the user and don't allow the insert
    		}
    	}
    }
}