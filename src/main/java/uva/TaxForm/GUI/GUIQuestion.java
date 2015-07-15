package uva.TaxForm.GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uva.TaxForm.AST.ASTVariable;

public class GUIQuestion extends JPanel {

	private JCheckBox checkBox;
	private JTextField textField;
	
	public GUIQuestion(String label, final ASTVariable var) {
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//this.setBackground(new Color(0, 150, 0));
		setLayout(new GridLayout(1,2));
		add( new JLabel(label) );
		
		if (var.getType() == ASTVariable.BOOLEAN) {
			checkBox = new JCheckBox("Yes/No");
			checkBox.setName(var.getName());
			checkBox.setSelected(false);
			checkBox.setEnabled(true);
			
			checkBox.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					var.setValue(String.valueOf(checkBox.isSelected()));
				}
			});
			
			add(this.checkBox);
		}
		else if (var.getType() == ASTVariable.MONEY) {
			textField = new GUIMoneyTextField(var);
			
			add(this.textField);
		}
	}
}
