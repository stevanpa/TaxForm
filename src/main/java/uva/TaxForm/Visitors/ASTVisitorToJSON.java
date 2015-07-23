package uva.TaxForm.Visitors;

import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

import uva.TaxForm.AST.ASTForm;
import uva.TaxForm.AST.ASTNode;

public class ASTVisitorToJSON {

	ASTForm root = null;
	
	public ASTVisitorToJSON(ASTNode node) {
		this.root = VisitAST.getRootNode(node);
	}
	
	public void visit() {
		JsonObjectBuilder model = Json.createObjectBuilder();
		JsonArrayBuilder questions = Json.createArrayBuilder();
		JsonObjectBuilder q = Json.createObjectBuilder();
		
		q.add("Q1", "A1");
		questions.add(q);

		q.add("Q2", "A2");
		questions.add(q);
		
		model.add("form", questions);
		
		
		
		StringWriter stWriter = new StringWriter();
		JsonWriter jsonWriter = Json.createWriter(stWriter);
		jsonWriter.writeObject(model.build());
		jsonWriter.close();
		
		System.out.println(stWriter);
	}
}
