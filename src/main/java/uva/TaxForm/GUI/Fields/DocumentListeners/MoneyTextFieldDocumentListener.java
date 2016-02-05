package uva.TaxForm.GUI.Fields.DocumentListeners;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;

import uva.TaxForm.GUI.Fields.MoneyTextField;

public class MoneyTextFieldDocumentListener extends AbstractDocumentListener {

	private MoneyTextField mtf;
	private JPanel panel;
	
	public MoneyTextFieldDocumentListener(final MoneyTextField mtf, final JPanel panel) {
		this.mtf = mtf;
		this.panel = panel;
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (Double.parseDouble(mtf.getText()) != 0) {
			enablePanel( panel, true);
		} else {
			enablePanel( panel, false);
			resetPanel(panel);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {}

}
