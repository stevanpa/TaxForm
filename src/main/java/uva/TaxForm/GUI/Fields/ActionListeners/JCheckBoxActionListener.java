package uva.TaxForm.GUI.Fields.ActionListeners;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class JCheckBoxActionListener extends AbstractActionListener {

	private JCheckBox cb;
	private JPanel panel;
	
	public JCheckBoxActionListener(final JCheckBox cb, final JPanel panel) {
		this.cb = cb;
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (cb.isSelected()) {
			enablePanel( panel, true);
		} else {
			enablePanel( panel, false);
			resetPanel(panel);
		}
	}

}
