package uva.TaxForm;

import uva.TaxForm.AST.ASTForm;
import uva.TaxForm.GUI.GUI;
import uva.TaxForm.Visitors.ASTVisitorToGUI;
import uva.TaxForm.Visitors.ASTVisitorToGUIListeners;

public class App {
	
	public static void main(String[] args) {
		String filePath;
		boolean internal = true;
		TaxForm taxForm = null;
		ASTForm root = null;
		GUI gui = null;

		if (args.length == 0) {
			filePath = "resources/default.tax";
		} else {
			filePath = args[0];
			internal = false;
		}
		
		taxForm = new TaxForm(filePath, internal);
		
		try {
			root = (ASTForm) taxForm.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Visit AST and build GUI
		gui = new GUI(root);
		ASTVisitorToGUI astToGUI = new ASTVisitorToGUI(gui);
		astToGUI.visit(root);
		
		// Add Action/DocumentListeners to update computed fields.
		ASTVisitorToGUIListeners astToGUIListeners = new ASTVisitorToGUIListeners(gui);
		astToGUIListeners.visit(root);
	}

}