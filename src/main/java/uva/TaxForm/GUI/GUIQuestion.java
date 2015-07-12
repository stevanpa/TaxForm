package uva.TaxForm.GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uva.TaxForm.AST.ASTVariable;

public class GUIQuestion extends JPanel {

	private JCheckBox checkBox;
	private JTextField textField;
	
	public GUIQuestion(String label, int type) {
		
		setLayout(new GridLayout(1,2));
		add( new JLabel(label) );
		
		if (type == ASTVariable.BOOLEAN) {
			checkBox = new JCheckBox("Yes/No");
			checkBox.setSelected(false);
			checkBox.setEnabled(true);
			
			checkBox.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//System.out.println(e.getActionCommand());
					System.out.println(checkBox.isSelected());
				}
			});
			add(this.checkBox);
		} else if (type == ASTVariable.MONEY) {
			textField = new JTextField(6);
			
			textField.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//System.out.println(e.getActionCommand());
					System.out.println(textField.getText());
				}
			});
			add(this.textField);
		}
	}
}
