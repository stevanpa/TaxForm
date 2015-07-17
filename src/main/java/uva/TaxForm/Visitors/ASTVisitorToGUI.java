package uva.TaxForm.Visitors;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uva.TaxForm.AST.ASTBlock;
import uva.TaxForm.AST.ASTExpression;
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
			visitExpresion(exp, panel);
		}
		
		return panel;
	}
	
	private void visitExpresion(ASTExpression exp, final JPanel panel) {
		// Single expression field
		// Add actionListener to evaluate single condition (TF1)
		if (exp.getExpressionType() == ASTExpression.SINGLE_EXP) {
			ASTVariable var = (ASTVariable) exp.getLeftNode();
			
			if (var.getValue().isEmpty()) {
				enablePanel(panel, false);
			}
			
			Component c = getComponentByName(this.gui.frame, var.getName());
			
			// CheckBox
			try {
				final JCheckBox checkBox = (JCheckBox) c;
				checkBox.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (checkBox.isSelected()) {
							enablePanel( panel, true);
						} else {
							enablePanel( panel, false);
							resetPanel(panel);
						}
					}
				});
			} catch (ClassCastException e) {}
			
			// TextField
			// TODO - Add actionListener to one TextField e.g. show hide a part of the form if a condition is met
			
		}
		// Evaluate expression field
		// TODO - Add actionListener to evaluate the condition of (TF1 > TF2) or (!TF1)
		else if (exp.getExpressionType() == ASTExpression.EXP) {
			//ASTVariable leftVar = (ASTVariable) exp.getLeftNode();
			
			//Component leftComponent = getComponentByName(this.gui.frame, leftVar.getName());
			
		}
		else {
		}
		
	}
	
	private void resetPanel( Container container ) {
		Component[] components = container.getComponents();
		for (Component c : components) {
			if (c instanceof Container) {
				resetPanel( (Container) c );
			}
			
			// CheckBox
			try {
				JCheckBox checkBox = (JCheckBox) c;
				checkBox.setSelected(false);
			} catch (ClassCastException e) {}
			
			// TextField
			// TODO - Reset different types of TextFields e.g. money/string
			try {
				JTextField textField = (JTextField) c;
				textField.setText("0.00");
			} catch (ClassCastException e) {}
		}
	}
	
	private Component getComponentByName(Container container, String name) {
		Component returnComp = null;
		boolean abort = false;
		Component[] comps = container.getComponents();
		
		for (int i=0; i<comps.length && !abort; i++) {
			if (name.equals(comps[i].getName())) {
				returnComp = comps[i];
				abort = true;
			} else {
				Container cont = (Container) comps[i];
				returnComp = getComponentByName(cont, name);
				if (returnComp != null) {
					abort = true;
				}
			}
		}
		return returnComp;
	}
	
	private void enablePanel( Container container, boolean enable ) {
		Component[] components = container.getComponents();
		for (Component c : components) {
			c.setEnabled(enable);
			if (c instanceof Container) {
				enablePanel( (Container) c, enable );
			}
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
		}
		
		return question;
	}
	
	private void visitQuestionExpression(ASTExpression exp) {
		
		// Calculated expression field
		// TODO - Add actionListener to Multiple TextField e.g. TF3 = TF1 - TF2
		ASTVariable resultVar = (ASTVariable) exp.getLeftNode();
		Component leftNode = getComponentByName(this.gui.frame, resultVar.getName());
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
