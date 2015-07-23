package uva.TaxForm.GUI.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.Visitors.ASTVisitorToJSON;

public class SaveMenu implements ActionListener {

	JFileChooser fc;
	JFrame frame;
	ASTNode node;
	
	public SaveMenu(JFileChooser fc, JFrame frame, ASTNode node) {
		this.fc = fc;
		this.frame = frame;
		this.node = node;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		int returnVal = fc.showSaveDialog(frame);
		
		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			String fileName = fc.getSelectedFile().toString();
			fileName = (fileName.toLowerCase().endsWith(".json"))? fileName : fileName + ".json";
			ASTVisitorToJSON astToJSON = new ASTVisitorToJSON(node);
			
			try {
				FileWriter fw = new FileWriter(fileName);
				fw.write(astToJSON.visit());
				fw.flush();
				fw.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
		} 
		else {
			//System.out.println("Cancelled");
		}
	}

}
