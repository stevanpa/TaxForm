package uva.TaxForm;

import java.io.InputStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.TypeChecker.TypeChecker;
import uva.TaxForm.Visitors.VisitorToAST;
import uva.TaxForm.antlr4.TaxFormLexer;
import uva.TaxForm.antlr4.TaxFormParser;

public class TaxForm {
	private String filePath;
	
    public TaxForm(String filePath, boolean internal) {
    	this.filePath = filePath;
    }
    
    public ASTNode start() throws Exception {
    	
    	InputStream in = getClass().getClassLoader().getResourceAsStream(filePath);
    	TaxFormLexer lexer;
    	
    	if (in == null) {
    		ANTLRFileStream input = new ANTLRFileStream(filePath, "UTF-8");
    		lexer = new TaxFormLexer(input);
    	}
    	else {
    		ANTLRInputStream input = new ANTLRInputStream(in);
    		lexer = new TaxFormLexer(input);
    	}
    	
    	CommonTokenStream tokens = new CommonTokenStream(lexer);
		TaxFormParser parser = new TaxFormParser(tokens);
		
		ParseTree tree = parser.form();
		VisitorToAST visitor = new VisitorToAST();
		ASTNode root = (ASTNode) visitor.visit(tree);
		TypeChecker.checkAST(root);
		
		return root;
    }
}