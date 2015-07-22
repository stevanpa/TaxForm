package uva.TaxForm;

import java.net.URL;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.TypeChecker.TypeChecker;
import uva.TaxForm.Visitors.VisitorToAST;
import uva.TaxForm.antlr4.TaxFormLexer;
import uva.TaxForm.antlr4.TaxFormParser;

public class TaxForm {
	private URL formDefinition;
	private String filePath;
	
    public TaxForm(URL url, boolean internal) {
    	formDefinition = url;
    	
    	if (internal) {
	    	filePath = formDefinition.getPath().toString().substring(1, formDefinition.getPath().toString().length());
    	} else {
	    	filePath = formDefinition.getPath().toString();
    	}
    	
    	System.out.println( "filePath: " + filePath );
    }
    
    public ASTNode start() throws Exception {
    	
    	ANTLRFileStream input = new ANTLRFileStream(filePath, "UTF-8");
    	
    	TaxFormLexer lexer = new TaxFormLexer(input);
    	CommonTokenStream tokens = new CommonTokenStream(lexer);
		TaxFormParser parser = new TaxFormParser(tokens);
		
		ParseTree tree = parser.form();
		VisitorToAST visitor = new VisitorToAST();
		ASTNode root = (ASTNode) visitor.visit(tree);
		TypeChecker.checkAST(root);
		
		return root;
		
		
		//System.out.println(root.getNodeType());
		
		
		/*System.out.println( ast.getName() );
		
		QuestionChecker Qchecker = new QuestionChecker();
		System.out.println( Qchecker.duplicates(ast) );*/
    }
}