package univ.annaba.Control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.Node;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.AnonId;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFVisitor;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

public class BadSmellOntologyController {
	protected Model badSmellOntology;
	protected String badSmellOntologyPath= "/home/moise/Documents/example/BadSmellOntology.owl";
	protected CodeOntologyController codeOntologyController;
	protected String longMethodRule = "/home/moise/Documents/example/rule.txt";
	protected String fanInRule = "/home/moise/Documents/example/rulefanin.txt";
	protected String deadCodeOntologyPath = "/home/moise/Documents/example/deadCode.owl";
	protected String badSmellontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
	
	public BadSmellOntologyController(String badSmellOntologyPath) {
		this.badSmellOntologyPath= badSmellOntologyPath;
		codeOntologyController = new CodeOntologyController();
		init();
	}
	
	public void init() {
		OntModel badSmellOntology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		OntClass badSmell = badSmellOntology.createClass(badSmellontologyURI+"BadSmells");
		this.addSubClass(badSmell, "ReportRefactoringOpportunities");
		OntClass variableLevel = this.addSubClass(badSmell, "VariableLevel");
		OntClass methodLevel = this.addSubClass(badSmell, "MethodLevel");
		OntClass classLevel = this.addSubClass(badSmell, "ClassLevel");
		this.addSubClass(variableLevel, "Comments");
		this.addSubClass(variableLevel, "TemporaryField");
		this.addSubClass(variableLevel, "PrimitiveObsession");
		this.addSubClass(variableLevel, "SpeculativeGenerality");
		this.addSubClass(variableLevel, "LongParametersList");
		this.addSubClass(methodLevel,"DuplicateCode" );
		this.addSubClass(methodLevel, "DeadCode");
		this.addSubClass(methodLevel, "FeatureEnvy");
		this.addSubClass(methodLevel, "SwitchStatements");
		this.addSubClass(methodLevel, "MessageChains");
		this.addSubClass(methodLevel, "LongMethod");
		this.addSubClass(classLevel, "ParallelInheritanceHierarchies");
		this.addSubClass(classLevel, "DivergentChange");
		this.addSubClass(classLevel, "LargeClass");
		this.addSubClass(classLevel, "DataClumps");
		this.addSubClass(classLevel, "InappropriateIntimacy");
		this.addSubClass(classLevel, "refusedBequest");
		this.addSubClass(classLevel, "IncompleteLibraryClass");
		this.addSubClass(classLevel, "AlternativeClassesWithDifferentInterfaces");
		this.addSubClass(classLevel, "ShotgunSurgery");
		this.addSubClass(classLevel, "DataClass");
		this.addSubClass(classLevel, "LazyClass");
		this.addSubClass(classLevel, "MiddleMan");
		
		OutputStream out = null;
		try {
			out = new FileOutputStream("/home/moise/Documents/example/BadSmellOntology.owl");
			badSmellOntology.write(out, "RDF/XML");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public  OntClass addSubClass(OntClass superClass, String subClassName) {
		OntClass subClass =null;
		subClass = superClass.getOntModel().createClass(badSmellontologyURI + subClassName);
		superClass.addSubClass(subClass);
		return subClass;
	}
	
	public void addConcepts(String codeSmellOntologyInputPath){
	
	}
	/**
	 * identify Long Methods (more then 30 lines of code) using the code ontology and SPARQL queries.
	 * @param codeOntologyInputPath
	 * @return
	 */
	public Model identifyLongMethod(String codeOntologyInputPath) {
		
		OntModel ontology = codeOntologyController.readOntology(codeOntologyInputPath);
		org.apache.jena.reasoner.Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(longMethodRule));
		InfModel inferredOntotlogy = ModelFactory.createInfModel(reasoner, ontology);
		inferredOntotlogy.prepare();
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
		//controller.identifyLongMethod("/home/moise/Documents/example/Test.owl");

	}

}
