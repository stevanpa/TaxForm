package uva.TaxForm.GUI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;

public class GUIVisitor {

	private ASTNode ast;
	private GUI gui;
	
	public GUIVisitor(GUI gui) {
		this.gui = gui;
	}
	
	public void visit(ASTNode ast) {
		this.ast = ast;
		
		if ( this.ast.size() > 0 ) {
			for ( int i=0; i<this.ast.size(); i++ ) {
				
				ASTNode node = this.ast.get(i);
				int nodeType = this.ast.get(i).getNodeType();
				
				switch ( nodeType ) {
					case 1: nodeType = ASTNode.FORM;
						addForm(node);
					case 2: nodeType = ASTNode.QUESTION;
						addQuestion(node);
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
