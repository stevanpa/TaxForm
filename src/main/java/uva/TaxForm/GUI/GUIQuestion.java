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

import uva.TaxForm.GUI.Fields.DateSpinner;
import uva.TaxForm.GUI.Fields.DateSpinnerImproved;
import uva.TaxForm.GUI.Fields.IntTextField;
import uva.TaxForm.GUI.Fields.MoneyTextField;

public class GUIQuestion extends JPanel {

	private JCheckBox checkBox;
	private JTextField textField;
	private DateSpinner spinner;
	//private DateSpinnerImproved spinner;
	
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
				
				@Override
				public void actionPerformed(ActionEvent e) {
					var.setValue(String.valueOf(checkBox.isSelected()));
				}
			});
			
			add(this.checkBox);
		}
		else if (var.getType() == ASTVariable.MONEY) {
			textField = new MoneyTextField(var);
			add(this.textField);
		}
		else if (var.getType() == ASTVariable.INT) {
			textField = new IntTextField(var);
			
			add(this.textField);
		}
		else if (var.getType() == ASTVariable.DATE) {
			spinner = new DateSpinner(var);
			//spinner = new DateSpinnerImproved(var);
			
			add(this.spinner);
		}
	}
}
