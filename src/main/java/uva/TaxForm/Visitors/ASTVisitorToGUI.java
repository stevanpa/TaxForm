package uva.TaxForm.Visitors;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import uva.TaxForm.AST.ASTIfStatement;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.GUI.GUI;
import uva.TaxForm.GUI.GUIQuestion;

public class ASTVisitorToGUI {

	private GUI gui;
	
	public ASTVisitorToGUI(GUI gui) {
		this.gui = gui;
	}
	
	public void visit(ASTNode ast) {
		
		/*if ( ast.size() > 0 ) {
			for ( int i=0; i<ast.size(); i++ ) {
				
				ASTNode node = ast.get(i);
				int nodeType = node.getNodeType();
				System.out.println(nodeType);
				
				switch ( nodeType ) {
					case ASTNode.FORM: addForm(node);
					case ASTNode.QUESTION: addQuestion(node);
					case ASTNode.IF_STATEMENT: addIfStatement(node);
					default: break;
				}
				
			}
		}*/
	}

	/*private void addIfStatement(ASTNode node) {
		//ASTNode ifStatement = (ASTNode) node;
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
		containerPanel.setName("Sub");
		//System.out.println("if");

		containerPanel.revalidate();
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
	
	private void addForm(ASTNode node) {
		// TODO Auto-generated method stub
		
	}*/
}
