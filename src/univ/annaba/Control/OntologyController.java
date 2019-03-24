package univ.annaba.Control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;

import univ.annaba.Model.MetricsParser;

public class OntologyController {
	protected MyVisitor visitor;
	protected OntModel ontology;
	protected String ontologyPath = "/home/moise/Documents/example/OntoCode.owl";
	protected String ontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
	protected String fanInRule = "/home/moise/Documents/example/rulefanin.txt";
	protected String longMethodRule = "/home/moise/Documents/example/rule.txt";
	protected String deadCodeOntologyURI = "/home/moise/Documents/example/deadCode.owl";
	protected String longMethodOntologyURI = "/home/moise/Documents/example/Longmethod.owl";
	
	protected Hashtable<String, ArrayList<String>> report;
	
	
	public OntologyController(MyVisitor visitor) {
		this.visitor = visitor;
		report = visitor.getConceptsReport();
		ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
	}
	
	/**
	 * This method generates Ontology with code concepts to output file .
	 * @param ontologyOutPutPath
	 */
	public void addOntologyConcepts( String ontologyOutPutPath) {
		try {
			if (ontologyPath.contentEquals(ontologyOutPutPath)) {
				ontologyOutPutPath = ontologyPath;
			}else {
			copyFileUsingStream(ontologyPath, ontologyOutPutPath);}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ontology = this.addOntologyElements(ontologyOutPutPath, report);
		this.writeOntology(ontologyOutPutPath, ontology);
	}
	
	/**
	 * write the ontology metrics to output file.
	 * @param ontologyOutPutPath
	 */
	public void writeOntologyMetrics( String ontologyOutPutPath) {
		try {
			copyFileUsingStream(ontologyPath, ontologyOutPutPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ontology = this.addOntologyMetrics(ontologyOutPutPath);
		this.writeOntology(ontologyOutPutPath, ontology);
		
	}
	/**
	 * write the Ontology to outputFile
	 * @param ontologyOutPutPath
	 * @param ontology
	 */
	public void writeOntology(String ontologyOutPutPath, OntModel ontology) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(ontologyOutPutPath);
			ontology.write(out, "RDF/XML");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * read the Ontology from an input file.
	 * @param ontologyInputPath
	 * @return
	 */
	public OntModel readOntology(String ontologyInputPath) {
		OntModel ontology= ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		FileManager.get().readModel(ontology, ontologyInputPath);
		InputStream in = FileManager.get().open(ontologyInputPath);
		if (in == null) {
			throw new IllegalArgumentException("File: " + ontologyInputPath + " not found");
		}
		ontology.read(in,"RDF/XML");
		return ontology;
	}
	/**
	 * adds the ontology methods, classes, packages and variables concepts. 
	 * @param ontologyPath
	 * @param report
	 * @return Ontology
	 */
	public OntModel addOntologyElements(String ontologyPath, Hashtable<String, ArrayList<String>> report) {
		ontology = this.readOntology(ontologyPath);
		this.addMethodsConcepts(report);
		this.addClassesConcepts(report);
		this.addPackagesConcepts(report);
		this.addVariablesConcepts(report);
		return ontology;
	}
	
	/**
	 * adds methods concepts
	 * @param report
	 */
	public void addMethodsConcepts(Hashtable<String, ArrayList<String>> report) {
		Hashtable<String, ArrayList<String>> hash = report;
		if (hash.containsKey("Methods")) {
			ArrayList<String> methods = hash.get("Methods");
			for (int i = 0; i < methods.size(); i++) {
					OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Method");
					String method = methods.get(i);
					ontClass.createIndividual(ontologyURI + method);
				}
			}
	}
	/**
	 * add classes concepts
	 * @param report
	 */
	public void addClassesConcepts(Hashtable<String, ArrayList<String>> report) {
		Hashtable<String, ArrayList<String>> hash = report;
		if (hash.containsKey("Classes")) {
			ArrayList<String> classes = hash.get("Classes");
			for (int i = 0; i < classes.size(); i++) {
					OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Classs");
					String classs = classes.get(i);
					ontClass.createIndividual(ontologyURI + classs);
				}
		}
	}
	/**
	 * add packages concepts
	 * @param report
	 */
	public void addPackagesConcepts(Hashtable<String, ArrayList<String>> report) {
		Hashtable<String, ArrayList<String>> hash = report;
		if (hash.containsKey("Packages")) {
			ArrayList<String> packages = hash.get("Packages");
			for (int i = 0; i < packages.size(); i++) {
					OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Package");
					String packagee = packages.get(i);
					ontClass.createIndividual(ontologyURI + packagee);
				}
		}
	}
	/**
	 * add variables concepts.
	 * @param report
	 */
	public void addVariablesConcepts(Hashtable<String, ArrayList<String>> report) {
		Hashtable<String, ArrayList<String>> hash = report;
		if (hash.containsKey("Variables")) {
			ArrayList<String> variables = hash.get("Variables");
			for (int i = 0; i < variables.size(); i++) {
					OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Variable");
					String variable = variables.get(i);
					ontClass.createIndividual(ontologyURI + variable);
				}
		}
	}
	/**
	 * add ontology Methods and classes metrics.
	 * @param ontologyPath
	 * @return ontology
	 */
	public OntModel addOntologyMetrics(String ontologyPath){
		this.addMethodsMetrics("MLOC");
		this.addMethodsMetrics("VG");
		this.addClassesMetrics("NOF");
		this.addClassesMetrics("NOM");
		return ontology;
	}
	/**
	 * adds methods metrics
	 * @param metric
	 */
	public void addMethodsMetrics(String metric) {
		MetricsParser metricsParser = MainInterfaceController.getMetricParser();
		String attributeName="";
		Individual individual = null;
		Hashtable<String, Integer> mlocMetrics = metricsParser.getMetricNameAndValue(metric);
		if (!mlocMetrics.isEmpty()) {
		for (int i = 0; i < mlocMetrics.size(); i++) {
			attributeName = metricsParser.getAttributeName().get(i);
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + metric);
			individual  = ontology.getIndividual(ontologyURI + attributeName);
			if(individual!=null) {
			int dataPropertyValue = mlocMetrics.get(attributeName);
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
			}
		}
		}

	}
	/**
	 * adds classes metrics
	 * @param metric
	 */
	public void addClassesMetrics(String metric) {
		MetricsParser metricsParser = MainInterfaceController.getMetricParser();
		String attributeName="";
		Individual individual = null;
		Hashtable<String, Integer> nomMetrics = metricsParser.getMetricNameAndValue(metric);
		if(!nomMetrics.isEmpty()) {
		for (int i = 0; i < nomMetrics.size(); i++) {
			attributeName = metricsParser.getAllClasses().get(i);
			attributeName = this.removeSuffix(attributeName);
			individual  = ontology.getIndividual(ontologyURI + attributeName);
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + metric);
			int dataPropertyValue = nomMetrics.get(attributeName);
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
		}
		}
	}
	/**
	 * copy file usig FileUtils 
	 * @param sourcePath
	 * @param destPath
	 * @throws IOException
	 */
	public void copyFileUsingStream(String sourcePath, String destPath) throws IOException {
		File source = new File(sourcePath);
		File dest = new File(destPath);
		FileUtils.copyFile(source, dest);
	}
	
	public void identifyLongMethod(String ontologyInputPath) {
		
		OntModel ontology = this.readOntology(ontologyInputPath);
		org.apache.jena.reasoner.Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(longMethodRule));

		InfModel inferredOntotlogy = ModelFactory.createInfModel(reasoner, ontology);
		inferredOntotlogy.prepare();
		System.out.println("ontologie inferred "+inferredOntotlogy);
		Model deadCodeOntology = inferredOntotlogy.getDeductionsModel();

		OutputStream out;
		try {
			out = new FileOutputStream(longMethodOntologyURI);
			deadCodeOntology.write(out, "RDF/XML"); 

		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}
	}
	/**
	 * removes the <i>.java</i> suffix from the class name.
	 * @param attributeName
	 * @return attributeName without suffix
	 */
	public String removeSuffix(String attributeName) {
		String str = attributeName;
		String search = ".java";

		int index = str.lastIndexOf(search);
		if (index > 0) {
		    str = str.substring(0, index);
		}
		return str;
	}
	public MyVisitor getVisitor() {
		return visitor;
	}
	
	public void setVisitor(MyVisitor visitor) {
		this.visitor = visitor;
	}
	public String getOntologyPath() {
		return ontologyPath;
	}

	public void setOntologyPath(String ontologyPath) {
		this.ontologyPath = ontologyPath;
	}
	
	public static void main(String[] args) {
		OntologyController controller = new OntologyController(new MyVisitor("/home/moise/Documents/example/HelloWorld.java"));
		if ("/home/moise/Documents/example/HelloWorld.java".contentEquals("/home/moise/Documents/example/HelloWorld.java")) {
			System.out.println("i was runed");
		}else {
		System.out.println("i was left!!");
		}
	}
}
