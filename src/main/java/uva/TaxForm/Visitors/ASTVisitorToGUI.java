package uva.TaxForm.Visitors;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import uva.TaxForm.AST.ASTBlock;
import uva.TaxForm.AST.ASTIfStatement;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.AST.ASTVariable;
import uva.TaxForm.GUI.GUI;
import uva.TaxForm.GUI.GUIQuestion;

public class ASTVisitorToGUI {

	private GUI gui;
	
	public ASTVisitorToGUI(GUI gui) {
		this.gui = gui;
	}
	
	public void visit(ASTBlock ast) {
		
		if ( ast.size() > 0 ) {
			for ( int i=0; i<ast.size(); i++ ) {
				
				ASTNode node = ast.get(i);
				int nodeType = node.getNodeType();
				//System.out.println(nodeType);
				
				switch ( nodeType ) {
					case ASTNode.FORM: 
						addForm(node);
						break;
					case ASTNode.QUESTION: 
						addQuestion(node);
						break;
					case ASTNode.IF_STATEMENT: 
						addIfStatement(node);
						break;
					default: break;
				}
				
			}
		}
	}

	private void addIfStatement(ASTNode node) {
		//ASTNode ifStatement = (ASTNode) node;
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
		
		Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		containerPanel.setBorder(lowerEtched);
		
		containerPanel.setName("Sub");
		
		System.out.println("if " + node.getNodeType());
		ASTIfStatement stmnt = (ASTIfStatement) node;
		ASTNode leftNode = stmnt.getLeftNode();
		ASTNode rightNode = stmnt.getRightNode();
		System.out.println("leftNode " + leftNode);
		System.out.println("rightNode " + rightNode);
		
		if (leftNode != null) {
			System.out.println(leftNode.getNodeType());
			if (leftNode.getNodeType() == ASTNode.BLOCK) {
				ASTBlock block = (ASTBlock) leftNode;
				System.out.println(block.size());
				visit(block);
			} else {
				//visit ifstatement
			}
		}
		
		if (rightNode != null) {
			
		}
		
		containerPanel.revalidate();
	}
	
	private void addQuestion(ASTNode node) {
		
		ASTQuestion questionNode = (ASTQuestion) node;
		JPanel containerPanel = addContainerPanel();
		
		String label = questionNode.getLabel();
		ASTVariable var = (ASTVariable) questionNode.getExpression().getLeftNode();
		
		GUIQuestion question = new GUIQuestion(label, var.getType());
		containerPanel.add( question );
		containerPanel.revalidate();
	}
	
	public JPanel addContainerPanel() {

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
		containerPanel.setPreferredSize(new Dimension(this.gui.frame.getWidth() - 30, 20));
		this.gui.frame.add(containerPanel);
		
		return containerPanel;
	}
	
	private void addForm(ASTNode node) {
		// TODO Auto-generated method stub
		
	}
}
