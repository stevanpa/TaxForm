package uva.TaxForm.GUI.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import uva.TaxForm.TaxForm;
import uva.TaxForm.AST.ASTForm;
import uva.TaxForm.GUI.GUI;
import uva.TaxForm.Visitors.ASTVisitorToGUI;

public class SaveMenu implements ActionListener {

	JFileChooser fc;
	JFrame frame;
	GUI gui;
	
	public SaveMenu(JFileChooser fc, JFrame frame, GUI gui) {
		this.fc = fc;
		this.frame = frame;
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int returnVal = fc.showSaveDialog(frame);
		
		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			File file = fc.getSelectedFile();
			ASTForm root = null;
			TaxForm taxForm = null;
			ASTVisitorToGUI astVisitor = null;
			
			try {
				taxForm = new TaxForm(file.toURI().toURL(), false);
			} 
			catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
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
