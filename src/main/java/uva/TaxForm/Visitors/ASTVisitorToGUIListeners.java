package uva.TaxForm.Visitors;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;

import uva.TaxForm.AST.ASTBlock;
import uva.TaxForm.AST.ASTExpression;
import uva.TaxForm.AST.ASTIfStatement;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.AST.ASTVariable;
import uva.TaxForm.GUI.GUI;
import uva.TaxForm.GUI.Fields.MoneyTextField;
import uva.TaxForm.GUI.Fields.DocumentListeners.AbstractDocumentListener;

public class ASTVisitorToGUIListeners {
	
	private GUI gui;
	
	public ASTVisitorToGUIListeners(GUI gui) {
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
						visitQuestion(node);
						break;
					case ASTNode.IF_STATEMENT: 
						//System.out.println("IF_STATEMENT");
						visitIfStatement(node);
						break;
					default: break;
				}
			}
		}
	}
	
	public void visitIfStatement(ASTNode node) {
		ASTIfStatement stmnt = (ASTIfStatement) node;
		ASTNode leftNode = stmnt.getLeftNode();
		ASTNode rightNode = stmnt.getRightNode();
		
		if (leftNode != null) {
			ASTBlock block = (ASTBlock) leftNode;
			visit(block, this.gui.panel);
		}
		
		if (rightNode != null) {
			ASTBlock block = (ASTBlock) rightNode;
			visit(block, this.gui.panel);
		}
	}

	public void visitQuestion(ASTNode node) {
		
		ASTQuestion questionNode = (ASTQuestion) node;
		
		if (questionNode.isComputed()) {
			//ASTVariable var = (ASTVariable) questionNode.getExpression().getLeftNode();
			ASTExpression exp = (ASTExpression) questionNode.getExpression();
			
			//Component c = ASTVisitorToGUIUtils.getComponentByName(this.gui.frame, var.getName());
			
			//System.out.println(c.getName());
			//System.out.println(exp.getExpressionType());
			
			visitExpression(exp);
		}
	}
	
	public void visitExpression(ASTExpression exp) {
		
		if (exp.getExpressionType() == ASTExpression.ASSIGN_EXP) {
			System.out.println(exp.getLeftNode().toString() + " ASIGN left");
			ASTVariable leftNode = (ASTVariable) exp.getLeftNode();
			ASTExpression rightNode = (ASTExpression) exp.getRightNode();
			
			Component cParent, cChildLeft, cChildRight;
			MoneyTextField tfParent, tfChildLeft, tfChildRight;
			ASTVariable childLeft, childRight;
			
			cParent = ASTVisitorToGUIUtils.getComponentByName(this.gui.frame, leftNode.getName());
			childLeft = (ASTVariable) rightNode.getLeftNode();
			cChildLeft = ASTVisitorToGUIUtils.getComponentByName(this.gui.frame, childLeft.getName());
			childRight = (ASTVariable) rightNode.getRightNode();
			cChildRight = ASTVisitorToGUIUtils.getComponentByName(this.gui.frame, childRight.getName());
			
			tfParent = (MoneyTextField) cParent;
			tfChildLeft = (MoneyTextField) cChildLeft;
			tfChildRight = (MoneyTextField) cChildRight;
			
			tfChildLeft.getDocument().addDocumentListener(new MoneyDocumentListener(tfParent, tfChildLeft, tfChildRight));
			tfChildRight.getDocument().addDocumentListener(new MoneyDocumentListener(tfParent, tfChildLeft, tfChildRight));
		}
	}
	
	class MoneyDocumentListener extends AbstractDocumentListener {
		
		MoneyTextField parent;
		MoneyTextField[] children;
		
		public MoneyDocumentListener (MoneyTextField parent, MoneyTextField... children) {
			this.parent = parent;
			this.children = children;
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			double total = 0.00;
			for(int i=0; i<children.length; i++) {
				total += Double.parseDouble(children[i].getText());
			}
			parent.setText(Double.toString(total));
		}

		@Override
		public void removeUpdate(DocumentEvent e) {}

		@Override
		public void changedUpdate(DocumentEvent e) {}
		
	}
}
