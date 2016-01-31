package uva.TaxForm.Visitors;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import uva.TaxForm.AST.ASTBlock;
import uva.TaxForm.AST.ASTExpression;
import uva.TaxForm.AST.ASTIfStatement;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.AST.ASTVariable;
import uva.TaxForm.GUI.GUI;
import uva.TaxForm.GUI.GUIQuestion;
import uva.TaxForm.GUI.Fields.IntTextField;
import uva.TaxForm.GUI.Fields.MoneyTextField;
import uva.TaxForm.GUI.Fields.ActionListeners.AbstractActionListener;
import uva.TaxForm.GUI.Fields.ActionListeners.JCheckBoxActionListener;
import uva.TaxForm.GUI.Fields.DocumentListeners.IntTextFieldDocumentListener;
import uva.TaxForm.GUI.Fields.DocumentListeners.MoneyTextFieldDocumentListener;

public class ASTVisitorToGUI {

	private GUI gui;
	
	public ASTVisitorToGUI(GUI gui) {
		this.gui = gui;
	}
	
	public void visit(ASTBlock ast) {
		visit(ast, this.gui.panel);
	}
	
	public void visit(ASTBlock ast, JPanel parentPanel) {
		
		if ( ast.size() > 0 ) {
			for ( int i=0; i<ast.size(); i++ ) {
				
				ASTNode node = ast.get(i);
				int nodeType = node.getNodeType();
				
				switch ( nodeType ) {
					case ASTNode.FORM:
						break;
					case ASTNode.QUESTION: 
						parentPanel.add(addQuestion(node));
						parentPanel.revalidate();
						break;
					case ASTNode.IF_STATEMENT: 
						parentPanel.add(addIfStatement(node));
						parentPanel.revalidate();
						break;
					default: break;
				}
			}
		}
	}

	private JPanel addIfStatement(ASTNode node) {
		final JPanel panel = addContainerPanel();
		
		ASTIfStatement stmnt = (ASTIfStatement) node;
		ASTExpression exp = (ASTExpression) stmnt.getExpression();
		ASTNode leftNode = stmnt.getLeftNode();
		ASTNode rightNode = stmnt.getRightNode();
		
		if (leftNode != null) {
			ASTBlock block = (ASTBlock) leftNode;
			visit(block, panel);
		}
		
		if (rightNode != null) {
			ASTBlock block = (ASTBlock) rightNode;
			visit(block, panel);
		}
		
		if (exp != null) {
			visitExpression(exp, panel);
		}
		
		return panel;
	}
	
	private void visitExpression(ASTExpression exp, final JPanel panel) {
		// Single expression fields
		// Add actionListener to evaluate single condition (TF1)
		if (exp.getExpressionType() == ASTExpression.SINGLE_EXP) {
			ASTVariable var = (ASTVariable) exp.getLeftNode();
			
			if (var.getValue().isEmpty()) {
				AbstractActionListener.enablePanel(panel, false);
			}
			
			Component c = ASTVisitorToGUIUtils.getComponentByName(this.gui.frame, var.getName());
			
			// CheckBox
			try {
				final JCheckBox checkBox = (JCheckBox) c;
				checkBox.addActionListener( 
						new JCheckBoxActionListener(checkBox, panel) );
			} catch (ClassCastException e) {}
			
			// IntTextField
			try {
				final IntTextField textField = (IntTextField) c;
				textField.getDocument().addDocumentListener( 
						new IntTextFieldDocumentListener(textField, panel) );
			} catch (ClassCastException e) {}
			
			// MoneyTextField
			try {
				final MoneyTextField textField = (MoneyTextField) c;
				textField.getDocument().addDocumentListener( 
						new MoneyTextFieldDocumentListener(textField, panel) );
			} catch (ClassCastException e) {}
			
		}
		// Evaluate expression field
		// TODO - Add actionListener/documentListener to evaluate the condition of (TF1 > TF2) or (!TF1)
		else if (exp.getExpressionType() == ASTExpression.EXP) {
			//ASTVariable leftVar = (ASTVariable) exp.getLeftNode();
			
			//Component leftComponent = getComponentByName(this.gui.frame, leftVar.getName());
			
		}
		else {
		}
	}
	
	private GUIQuestion addQuestion(ASTNode node) {
		
		ASTQuestion questionNode = (ASTQuestion) node;
		
		String label = questionNode.getLabel();
		ASTVariable var = (ASTVariable) questionNode.getExpression().getLeftNode();
		
		final GUIQuestion question = new GUIQuestion(label, var);
		question.setPreferredSize(new Dimension(this.gui.panel.getWidth()-30, 20));
		
		if (questionNode.getExpression().getExpressionType() == ASTExpression.EXP) {
			// If it's a calculated question we should add some triggers to update the field
			visitQuestionExpression(questionNode.getExpression());
		}/* else if (questionNode.getExpression().getExpressionType() == ASTExpression.SINGLE_EXP) {
			// If it's a calculated question we should add some triggers to update the field
			visitExpression(questionNode.getExpression(), this.gui.panel);
		}*/
		
		return question;
	}
	
	private void visitQuestionExpression(ASTExpression exp) {
		
		// Calculated expression field
		// TODO - Add actionListener to Multiple TextField e.g. TF3 = TF1 - TF2
		ASTVariable resultVar = (ASTVariable) exp.getLeftNode();
		Component leftNode = ASTVisitorToGUIUtils.getComponentByName(this.gui.frame, resultVar.getName());
		Component rightNode = null;
		
		if (exp.getRightNode().getNodeType() == ASTNode.EXPRESSION) {
			
		} 
		else {
			
		}
		
		
	}
	
	public JPanel addContainerPanel() {

		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
		//containerPanel.setPreferredSize(new Dimension(this.gui.frame.getWidth() - 30, 20));
		//this.gui.frame.add(containerPanel);
		
		return containerPanel;
	}
}
