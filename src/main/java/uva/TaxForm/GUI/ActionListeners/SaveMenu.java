package uva.TaxForm.GUI.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import uva.TaxForm.GUI.GUI;

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
	public void actionPerformed(ActionEvent evt) {
		int returnVal = fc.showSaveDialog(frame);
		
		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			//System.out.println(fc.getSelectedFile() + ".json");
			
			try {
				FileWriter fw = new FileWriter(fc.getSelectedFile() + ".json");
				fw.write("test json stuff");
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
