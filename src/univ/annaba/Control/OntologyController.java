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
	protected OntModel ontology;
	protected MyVisitor visitor;
	protected String ontologyPath = "/home/moise/Documents/example/OntoCode.owl";
	protected String ontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
	protected String fanInRule = "/home/moise/Documents/example/rulefanin.txt";
	protected String longMethodRule = "/home/moise/Documents/example/rule.txt";
	protected String deadCodeOntologyURI = "/home/moise/Documents/example/deadCode.owl";
	protected String longMethodOntologyURI = "/home/moise/Documents/example/Longmethod.owl";
	
	protected Hashtable<String, ArrayList<String>> report;
	
	public OntologyController() {
		// TODO Auto-generated constructor stub
	}
	
	public OntologyController(MyVisitor visitor) {
		this.visitor = visitor;
		report = visitor.getConceptsReport();
	}
	
	/**
	 * this method generate Ontology with code concepts to output file .
	 * @param ontologyOutPutPath
	 */
	public void addOntologyConcepts( String ontologyOutPutPath) {
		OntModel ontology = ModelFactory.createOntologyModel();
		
		try {
			if (ontologyPath.contentEquals(ontologyOutPutPath)) {
				ontologyOutPutPath = ontologyPath;
			}else {
			copyFileUsingStream(ontologyPath, ontologyOutPutPath);}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ontology = this.addOntologyElements(ontologyOutPutPath, report);
		writeOntology(ontologyOutPutPath, ontology);
	}
	
	/**
	 * add
	 * @param ontologyOutPutPath
	 */
	public void writeOntologyMetrics( String ontologyOutPutPath) {
		
		ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		
		try {
			copyFileUsingStream(ontologyPath, ontologyOutPutPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ontology = this.addOntologyMetrics(ontologyOutPutPath);
		writeOntology(ontologyOutPutPath, ontology);
		
	}

	public void writeOntology(String ontologyOutPutPath, OntModel ontology) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(ontologyOutPutPath);
			ontology.write(out, "RDF/XML");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				out.close();
				ontology.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public OntModel readOntology(String ontologyInputPath) {
		ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		InputStream in = FileManager.get().open(ontologyInputPath);
		if (in == null) {
			throw new IllegalArgumentException("File: " + ontologyInputPath + " not found");
		}
		ontology.read(in,"RDF/XML");
		return ontology;
	}
	
	public OntModel addOntologyElements(String ontologyPath, Hashtable<String, ArrayList<String>> report) {
		OntModel ontology = this.readOntology(ontologyPath);
		Hashtable<String, ArrayList<String>> hash = report;
		
		if (hash.containsKey("Methods")) {
		ArrayList<String> methods = hash.get("Methods");
		for (int i = 0; i < methods.size(); i++) {
				OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Method");
				ontClass.createIndividual(ontologyURI + methods.get(i));
			}
		}
		if (hash.containsKey("Classes")) {
			ArrayList<String> classes = hash.get("Classes");
			for (int i = 0; i < classes.size(); i++) {
					OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Classs");
					ontClass.createIndividual(ontologyURI + classes.get(i));
				}
		}
		if (hash.containsKey("Packages")) {
			ArrayList<String> packages = hash.get("Packages");
			for (int i = 0; i < packages.size(); i++) {
					OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Package");
					ontClass.createIndividual(ontologyURI + packages.get(i));
				}
		}
		if (hash.containsKey("Variables")) {
			ArrayList<String> variables = hash.get("Variables");
			for (int i = 0; i < variables.size(); i++) {
					OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Variable");
					ontClass.createIndividual(ontologyURI + variables.get(i));
				}
		}
			return ontology;
	}
	
	/**
	 * add ontology metrics
	 * @param ontologyPath
	 * @return ontology
	 */
	public OntModel addOntologyMetrics(String ontologyPath){
		OntModel ontology = this.readOntology(ontologyPath);
		MainInterfaceController controller = new MainInterfaceController();
		MetricsParser metrics = controller.getMetricParser();
		Hashtable<String, Hashtable<String, Integer>> metricHash = metrics.getAllMetrics();
		
		Hashtable<String, Integer> vgMetrics = metricHash.get("VG");
		String attributeName="";
		Individual individual = null;
		for (int i = 0; i < vgMetrics.size(); i++) {
			attributeName = metrics.getAttributeName().get(i);
			individual  = ontology.getIndividual(ontologyURI + attributeName);
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + "VG");
			int dataPropertyValue = vgMetrics.get(attributeName);
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
		}
		
		Hashtable<String, Integer> mlocMetrics = metricHash.get("MLOC");
		for (int i = 0; i < mlocMetrics.size(); i++) {
			attributeName = metrics.getAttributeName().get(i);
			individual  = ontology.getIndividual(ontologyURI + attributeName);
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + "MLOC");
			int dataPropertyValue = mlocMetrics.get(attributeName);
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
		}
		Hashtable<String, Integer> nofMetrics = metricHash.get("NOF");
		for (int i = 0; i < nofMetrics.size(); i++) {
			attributeName = metrics.getAttributeName().get(i);
			individual  = ontology.getIndividual(ontologyURI + attributeName);
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + "NOF");
			int dataPropertyValue = nofMetrics.get(attributeName);
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
		}
		Hashtable<String, Integer> nomMetrics = metricHash.get("NOM");
		for (int i = 0; i < nomMetrics.size(); i++) {
			attributeName = metrics.getAttributeName().get(i);
			individual  = ontology.getIndividual(ontologyURI + attributeName);
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + "NOM");
			int dataPropertyValue = nomMetrics.get(attributeName);
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
		}
		
		return ontology;
	}
	
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
		System.out.println("ontologie inferred");
		Model deadCodeOntology = inferredOntotlogy.getDeductionsModel();

		OutputStream out;
		try {
			out = new FileOutputStream(longMethodOntologyURI);
			deadCodeOntology.write(out, "RDF/XML"); 

		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}
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
		OntologyController controller = new OntologyController();
		controller.identifyLongMethod("/home/moise/Documents/example/Test.owl");
	}
}
