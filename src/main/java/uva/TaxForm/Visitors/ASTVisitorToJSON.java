package uva.TaxForm.Visitors;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import uva.TaxForm.AST.ASTNode;
import uva.TaxForm.AST.ASTQuestion;
import uva.TaxForm.AST.ASTVariable;

public class ASTVisitorToJSON {

	ArrayList<ASTNode> qList = null;
	
	public ASTVisitorToJSON(ASTNode node) {
		this.qList = VisitAST.getNodesByType(node, ASTNode.QUESTION);
	}
	
	public String visit() {
		Map<String, Boolean> config = new HashMap<>(0);
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		JsonWriterFactory jwf = Json.createWriterFactory(config);
		
		JsonObjectBuilder model = Json.createObjectBuilder();
		JsonArrayBuilder questions = Json.createArrayBuilder();
		JsonObjectBuilder q = Json.createObjectBuilder();
		
		for (int i=0; i<qList.size(); i++) {
			ASTQuestion question = (ASTQuestion) qList.get(i);
			ASTVariable var = (ASTVariable) question.getExpression().getLeftNode();
			String label = question.getLabel();
			String value = (var.getValue().equals(""))? "undefined" : var.getValue();
			
			q.add(label, value);
			questions.add(q);
		}
		
		model.add("form", questions);
		
		StringWriter stWriter = new StringWriter();
		JsonWriter jsonWriter = jwf.createWriter(stWriter);
		jsonWriter.writeObject(model.build());
		jsonWriter.close();
		
		return stWriter.toString();
	}
}
