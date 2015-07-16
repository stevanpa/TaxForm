package uva.TaxForm.Utils;

import java.util.ArrayList;
import java.util.Stack;

public class ShuntingYardAlgorithm {

	public static ArrayList<Object> infixToPostfix(ArrayList<Object> infixList) {
		
		final String ops = "-+/*^";
		ArrayList<Object> outputList = new ArrayList<Object>(0);
		Stack<Integer> opStack = new Stack<Integer>();
		
		for (Object token : infixList) {
			
			// Get the index of the operator
			String strToken = token.toString();
			int opIndex = ops.indexOf(strToken);
			
			// If we found an operator
			if (opIndex != -1) {
				// Check for an empty operator stack
				if (opStack.isEmpty()) {
					opStack.push(opIndex);
				}
				// Operator stack is not empty
				else {
					//TODO understand what's happening here... a bit of black magic?
					while (!opStack.isEmpty()) {
						int headIndex = opStack.peek() / 2;
						int nextIndex = opIndex / 2;
						if (headIndex > nextIndex || (headIndex == nextIndex && strToken != "^")) {
							outputList.add(ops.charAt(opStack.pop()));
						} else {
							break;
						}
					}
					opStack.push(opIndex);
				}
			}
			else if (strToken.equals("(")) {
				opStack.push(-2);
			}
			else if (strToken.equals(")")) {
				while (opStack.peek() != -2) {
					outputList.add(ops.charAt(opStack.pop()));
				}
				opStack.pop();
			}
			else {
				outputList.add(token);
			}
		}
		while (!opStack.isEmpty()) {
			outputList.add(ops.charAt(opStack.pop()));
		}
		
		return outputList;
	}
}
