package uva.TaxForm.Visitors;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.GUI.GUI;
import uva.TaxForm.GUI.GUIQuestion;

public class ASTVisitorToGUI {

	private ASTNode ast;
	private GUI gui;
	
	public ASTVisitorToGUI(GUI gui) {
		this.gui = gui;
	}
	
	public void visit(ASTNode ast) {
		this.ast = ast;
		
		if ( this.ast.size() > 0 ) {
			for ( int i=0; i<this.ast.size(); i++ ) {
				
				ASTNode node = this.ast.get(i);
				int nodeType = this.ast.get(i).getNodeType();
				
				switch ( nodeType ) {
					case ASTNode.FORM: addForm(node);
					case ASTNode.QUESTION: addQuestion(node);
					default: break;
				}
				
			}
		}
	}

	private void addForm(ASTNode node) {
		// TODO Auto-generated method stub
		
	}
	
	private void addQuestion(ASTNode node) {
		
		ASTQuestion questionNode = (ASTQuestion) node;
		JPanel containerPanel = addContainerPanel();
		GUIQuestion question = new GUIQuestion(questionNode.getLabel(), questionNode.getType());
		containerPanel.add( question );
		containerPanel.revalidate();
	}
	
	public JPanel addContainerPanel() {

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
		this.gui.frame.add(containerPanel);
		
		return containerPanel;
	}
}
