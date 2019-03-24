package univ.annaba.Control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

public class BadSmellOntologyController {
	protected Model badSmellOntology;
	protected String badSmellOntologyPath= "/home/moise/Documents/example/BadSmellOntology.owl";
	protected CodeOntologyController codeOntologyController;
	protected String longMethodRule = "/home/moise/Documents/example/rule.txt";
	protected String fanInRule = "/home/moise/Documents/example/rulefanin.txt";
	protected String deadCodeOntologyPath = "/home/moise/Documents/example/deadCode.owl";
	
	public BadSmellOntologyController(String badSmellOntologyPath) {
		this.badSmellOntologyPath= badSmellOntologyPath;
		codeOntologyController = new CodeOntologyController();
	}
	
	public Model identifyLongMethod(String codeOntologyInputPath) {
		
		OntModel ontology = codeOntologyController.readOntology(codeOntologyInputPath);
		org.apache.jena.reasoner.Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(longMethodRule));
		InfModel inferredOntotlogy = ModelFactory.createInfModel(reasoner, ontology);
		inferredOntotlogy.prepare();
		System.out.println("ontologie inferred "+inferredOntotlogy);
		badSmellOntology = inferredOntotlogy.getDeductionsModel();	
		this.writeBadSmellOntology(badSmellOntologyPath);
		return badSmellOntology;
	}
	
	
	public void writeBadSmellOntology(String outputhPath){
		OutputStream out = null;
		try {
			out = new FileOutputStream(outputhPath);
			badSmellOntology.write(out, "RDF/XML");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		BadSmellOntologyController controller= new BadSmellOntologyController("/home/moise/Documents/example/BadSmellOntology.owl");
		controller.identifyLongMethod("/home/moise/Documents/example/Test.owl");

	}

}
