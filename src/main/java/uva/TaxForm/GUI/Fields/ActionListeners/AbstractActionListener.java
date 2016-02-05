package uva.TaxForm.GUI.Fields.ActionListeners;

import java.awt.Component;
import java.awt.Container;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;

public abstract class AbstractActionListener extends AbstractAction {
	
	public static void enablePanel( Container container, boolean enable) {
		Component[] components = container.getComponents();
		for (Component c : components) {
			c.setEnabled(enable);
			if (c instanceof Container) {
				enablePanel( (Container) c, enable );
			}
		}
	};
	
	public void resetPanel( Container container) {
		Component[] components = container.getComponents();
		for (Component c : components) {
			if (c instanceof Container) {
				resetPanel( (Container) c );
			}
			
			// CheckBox
			try {
				JCheckBox checkBox = (JCheckBox) c;
				checkBox.setSelected(false);
			} catch (ClassCastException e) {}
		}
	};
}
