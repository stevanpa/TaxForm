package uva.TaxForm.GUI.Fields;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.PlainDocument;

import uva.TaxForm.AST.ASTVariable;
import uva.TaxForm.GUI.Fields.DocumentFilters.MoneyFilter;

public class MoneyTextField extends JTextField {
	
    public MoneyTextField(final ASTVariable var) {
    	super();
    	this.setName(var.getName());
    	this.setText("0.00");
    	PlainDocument doc = (PlainDocument) this.getDocument();
    	doc.setDocumentFilter(new MoneyFilter(var, "^[0-9]+[.]?[0-9]{0,2}$"));
    	
    	this.addFocusListener(new FocusAdapter() {
    		
    		@Override
			public void focusGained(FocusEvent evt) {
    			SwingUtilities.invokeLater(new Runnable() {
    				
					@Override
					public void run() {
						selectAll();
					}
    			});
    		}
    		
    		@Override
			public void focusLost(FocusEvent evt) {
    			SwingUtilities.invokeLater(new Runnable() {
    				
					@Override
					public void run() {
						String temp = String.format("%.2f", Double.parseDouble(getText()));
						temp = temp.replace(",", ".");
						setText(temp);
					}
    			});
    		}
    	});
    }
}