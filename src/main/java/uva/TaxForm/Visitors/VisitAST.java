package uva.TaxForm.Visitors;

import java.util.ArrayList;
import uva.TaxForm.AST.ASTBlock;
import uva.TaxForm.AST.ASTExpression;
import uva.TaxForm.AST.ASTForm;
import uva.TaxForm.AST.ASTIfStatement;
import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;

public final class VisitAST {
	
	public static ArrayList<ASTNode> getNodesByType( ASTNode startNode, int nodeType ) {
		ASTForm root = getRootNode(startNode);
		ArrayList<ASTNode> nodeList = visitBlock(root, nodeType);
		
		// to remove duplicate nodes, but I seem to need them to type check for duplicate questions
		/*Set<ASTNode> set = new HashSet<ASTNode>(nodeList);
		nodeList = new ArrayList<ASTNode>(set);*/
		
		return nodeList;
	}
	
	private static ASTForm getRootNode( ASTNode node ) {
		ASTNode tempNode = node;
		
		while (tempNode.getParent() != null) {
			//System.out.println("going up " + tempNode.toString());
			tempNode = tempNode.getParent();
		}
		//System.out.println("parent? " + tempNode.toString());
		
		ASTForm root = (ASTForm) tempNode;
		//System.out.println(root.size());
		
		return root;
	}
	
	private static ArrayList<ASTNode> visitBlock( ASTBlock node, int nodeType ) {
		ArrayList<ASTNode> nodeList = new ArrayList<ASTNode>();
		//System.out.println(node.size());
		for (int i=0; i<node.size(); i++) {
			int type = node.get(i).getNodeType();
			//System.out.println(type);
			
			if (type == nodeType) {
				nodeList.add(node.get(i));
			} else if(type == ASTNode.IF_STATEMENT) {
				nodeList.addAll(visitIfStatement( (ASTIfStatement) node.get(i), nodeType ));
			} else if(type == ASTNode.QUESTION) {
				nodeList.addAll(visitQuestion( (ASTQuestion) node.get(i), nodeType ));
			}
		}
		//System.out.println(nodeList.size());
		return nodeList;
	}

	private static ArrayList<ASTNode> visitIfStatement( ASTIfStatement node, int nodeType ) {
		ArrayList<ASTNode> nodeList = new ArrayList<ASTNode>();
		ASTNode leftNode = node.getLeftNode();
		ASTNode rightNode = node.getRightNode();
		
		if (leftNode != null) {
			ASTBlock blockNode = (ASTBlock) leftNode;
			nodeList.addAll(visitBlock(blockNode, nodeType));
		}
		
		if (rightNode != null) {
			if (rightNode.getNodeType() == ASTNode.BLOCK) {
				ASTBlock blockNode = (ASTBlock) rightNode;
				nodeList.addAll(visitBlock(blockNode, nodeType));
			} else {
				nodeList.addAll(visitIfStatement( (ASTIfStatement) rightNode, nodeType ));
			}
		}
		//System.out.println(nodeList.size());
		return nodeList;
	}
	
	private static ArrayList<ASTNode> visitQuestion( ASTQuestion node, int nodeType ) {
		ArrayList<ASTNode> nodeList = new ArrayList<ASTNode>();
		if (node.getExpression() != null) {
			if (node.getExpression().getNodeType() == nodeType) {
				nodeList.add(node.getExpression());
			}
			else {
				nodeList.addAll(visitExpression( node.getExpression(), nodeType ));
			}
		}
		//System.out.println(nodeList.size());
		return nodeList;
	}
	
	private static ArrayList<ASTNode> visitExpression( ASTExpression node, int nodeType ) {
		ArrayList<ASTNode> nodeList = new ArrayList<ASTNode>();
		
		ASTNode leftNode = node.getLeftNode();
		ASTNode rightNode = node.getRightNode();
		
		if (leftNode != null) {
			if (leftNode.getNodeType() == nodeType) {
				nodeList.add(leftNode);
			}
		}
		
		if (rightNode != null) {
			if (rightNode.getNodeType() == ASTNode.EXPRESSION) {
				ASTExpression exp = (ASTExpression) rightNode;
				nodeList.addAll(visitExpression(exp, nodeType));
			} else {
				nodeList.add(rightNode);
			}
		}
		//System.out.println(nodeList.size());
		//System.out.println(leftNode);
		return nodeList;
	}
}
