package uva.TaxForm.GUI.Fields.DocumentListeners;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;

import uva.TaxForm.GUI.Fields.IntTextField;

public class IntTextFieldDocumentListener extends AbstractDocumentListener {
	
	private IntTextField itf;
	private JPanel panel;
	
	public IntTextFieldDocumentListener(final IntTextField itf, final JPanel panel) {
		this.itf = itf;
		this.panel = panel;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (Integer.parseInt(itf.getText()) > 0) {
			enablePanel( panel, true);
		} else {
			enablePanel( panel, false);
			resetPanel(panel);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {}

}
