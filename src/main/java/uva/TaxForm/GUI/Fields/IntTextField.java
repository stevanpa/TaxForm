package uva.TaxForm.GUI.Fields;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.PlainDocument;

import uva.TaxForm.AST.ASTVariable;
import uva.TaxForm.GUI.Fields.DocumentFilters.IntFilter;

public class IntTextField  extends JTextField {
	
    public IntTextField(final ASTVariable var) {
    	super();
    	this.setName(var.getName());
    	this.setText("0");
    	PlainDocument doc = (PlainDocument) this.getDocument();
    	doc.setDocumentFilter(new IntFilter(var, "^[0-9]+$"));
    	
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
    	});
    }
}
