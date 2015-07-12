package uva.TaxForm;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import uva.TaxForm.AST.ASTForm;
import uva.TaxForm.GUI.GUI;
import uva.TaxForm.Visitors.ASTVisitorToGUI;

public class App {
	
	public static void main(String[] args) {
		String filePath;
		boolean internal = true;
		TaxForm taxForm = null;
		ASTForm root = null;

		if (args.length == 0) {
			filePath = "/default.tax";
		} else {
			filePath = args[0];
			internal = false;
		}
		
		if (internal) {
			taxForm = new TaxForm(URL.class.getResource(filePath), internal);
		} else {
			try {
				taxForm = new TaxForm(new File(filePath).toURI().toURL(), internal);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			root = (ASTForm) taxForm.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Visit AST and build GUI
		GUI gui = new GUI();
		
		ASTVisitorToGUI astVisitor = new ASTVisitorToGUI(gui);
		astVisitor.visit(root);
	}

}