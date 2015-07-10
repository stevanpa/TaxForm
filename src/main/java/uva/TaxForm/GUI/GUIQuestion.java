package uva.TaxForm.GUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIQuestion extends JPanel {

	private JCheckBox checkBox;
	
	public GUIQuestion(String label, String type) {
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add( new JLabel(label) );
		
		if (type == "boolean") {
			checkBox = new JCheckBox("Yes/No");
			checkBox.setSelected(false);
			checkBox.setEnabled(true);
			
			checkBox.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(e.getActionCommand());
				}
			});
		}
		add(this.checkBox);
	}
}
