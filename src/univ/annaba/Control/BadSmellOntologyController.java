package univ.annaba.Control;


import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.iterator.ExtendedIterator;


public class BadSmellOntologyController extends OntologyController{
	protected OntModel badSmellOntology;
	protected String badSmellOntologyPath= "C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\BadSmellOntology.owl";
	protected CodeOntologyController codeOntologyController;
	protected String longMethodRule = "C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\rule.txt";
	protected String fanInRule = "C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\rulefanin.txt";
	protected String deadCodeOntologyPath = "C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\deadCode.owl";
	protected String longMethodOntologyPath="C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\longMethod.owl";
	protected String badSmellontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
	protected OntClass deadCode;
	protected OntClass longMethod;
	
	public BadSmellOntologyController(String badSmellOntologyPath) {
		this.badSmellOntologyPath= badSmellOntologyPath;
		codeOntologyController = new CodeOntologyController();
		init();
	}
	
	public void init() {
		badSmellOntology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
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
		deadCode = this.addSubClass(methodLevel, "DeadCode");
		this.addSubClass(methodLevel, "FeatureEnvy");
		this.addSubClass(methodLevel, "SwitchStatements");
		this.addSubClass(methodLevel, "MessageChains");
		longMethod = this.addSubClass(methodLevel, "LongMethod");
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
	}
	
	public  OntClass addSubClass(OntClass superClass, String subClassName) {
		OntClass subClass =null;
		subClass = superClass.getOntModel().createClass(badSmellontologyURI + subClassName);
		superClass.addSubClass(subClass);
		return subClass;
	}
	
	/**
	 * create Bad Smell Ontology concepts.
	 * @param codeOntologyInputPath
	 * @return 
	 */
	public  OntModel addConcepts(){
	OntModel longMethodOntology = this.readOntology(longMethodOntologyPath);
	OntClass ontClass = longMethodOntology.getOntClass(badSmellontologyURI+"LongMethod");
	if(ontClass != null) {
	ExtendedIterator<?> instances = ontClass.listInstances();
	while (instances.hasNext()) {
		Individual individual = (Individual) instances.next();
		this.longMethod.createIndividual(individual.toString());
		}
	this.writeOntology(badSmellOntology, badSmellOntologyPath);
	}
	return badSmellOntology;
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
		Model longMethodOntology = inferredOntotlogy.getDeductionsModel();
		this.writeOntology(longMethodOntology, longMethodOntologyPath);
		return longMethodOntology;
	}
	

	public static void main(String[] args) {
		BadSmellOntologyController controller= new BadSmellOntologyController("C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\BadSmellOntology.owl");
		controller.identifyLongMethod("C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\Test.owl");
		controller.addConcepts();
	}

}
