package uva.TaxForm.GUI.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import uva.TaxForm.TaxForm;
import uva.TaxForm.AST.ASTForm;
import uva.TaxForm.GUI.GUI;
import uva.TaxForm.Visitors.ASTVisitorToGUI;

public class LoadMenu implements ActionListener {

	JFileChooser fc;
	JFrame frame;
	GUI gui;
	
	public LoadMenu(JFileChooser fc, JFrame frame, GUI gui) {
		this.fc = fc;
		this.frame = frame;
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		int returnVal = fc.showOpenDialog(frame);
		
		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			File file = fc.getSelectedFile();
			ASTForm root = null;
			TaxForm taxForm = null;
			ASTVisitorToGUI astVisitor = null;
			
			taxForm = new TaxForm(file.toPath().toString(), false);
			
			try {
				root = (ASTForm) taxForm.start();
				astVisitor = new ASTVisitorToGUI(gui.resetFrame());
				astVisitor.visit(root);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		} 
		else {
			//System.out.println("Cancelled");
		}
	}
}
