package uva.TaxForm;

import java.io.File;
import java.net.URL;

import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.GUI.GUI;
import uva.TaxForm.Visitors.ASTVisitorToGUI;

public class App {
	
	public static void main(String[] args) {
		String filePath;
		boolean internal = true;

		if (args.length == 0) {
			filePath = "/default.tax";
		} else {
			filePath = args[0];
			internal = false;
		}
		
		if (internal) {
			try {
				TaxForm taxForm = new TaxForm(URL.class.getResource(filePath), internal);
				ASTNode root = taxForm.start();
				
				//Visit AST and build GUI
				GUI gui = new GUI();
				
				ASTVisitorToGUI astVisitor = new ASTVisitorToGUI(gui);
				astVisitor.visit(root);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				TaxForm taxForm = new TaxForm(new File(filePath).toURI().toURL(), internal);
				taxForm.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}