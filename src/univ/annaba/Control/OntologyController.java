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
	 * this method generate Ontology with code concepts to output file .
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
	 * add
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

	public void writeOntology(String ontologyOutPutPath, OntModel ontology) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(ontologyOutPutPath);
			ontology.write(out, "RDF/XML");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		/**  finally {
		 
			try {
				out.close();
				ontology.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
	
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
	
	public OntModel addOntologyElements(String ontologyPath, Hashtable<String, ArrayList<String>> report) {
		ontology = this.readOntology(ontologyPath);
		Hashtable<String, ArrayList<String>> hash = report;
		
		if (hash.containsKey("Methods")) {
		ArrayList<String> methods = hash.get("Methods");
		for (int i = 0; i < methods.size(); i++) {
				OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Method");
				String method = methods.get(i);
				ontClass.createIndividual(ontologyURI + method);
			}
		}
		if (hash.containsKey("Classes")) {
			ArrayList<String> classes = hash.get("Classes");
			for (int i = 0; i < classes.size(); i++) {
					OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Classs");
					String classs = classes.get(i);
					ontClass.createIndividual(ontologyURI + classs);
				}
		}
		if (hash.containsKey("Packages")) {
			ArrayList<String> packages = hash.get("Packages");
			for (int i = 0; i < packages.size(); i++) {
					OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Package");
					String packagee = packages.get(i);
					ontClass.createIndividual(ontologyURI + packagee);
				}
		}
		if (hash.containsKey("Variables")) {
			ArrayList<String> variables = hash.get("Variables");
			for (int i = 0; i < variables.size(); i++) {
					OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Variable");
					String variable = variables.get(i);
					ontClass.createIndividual(ontologyURI + variable);
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
		MetricsParser metrics = MainInterfaceController.getMetricParser();
		String attributeName="";
		Individual individual = null;
		Hashtable<String, Integer> mlocMetrics = metrics.getMlocMetric();
		if (!mlocMetrics.isEmpty()) {
		for (int i = 0; i < mlocMetrics.size(); i++) {
			attributeName = metrics.getAttributeName().get(i);
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + "MLOC");
			individual  = ontology.getIndividual(ontologyURI + attributeName);
			if(individual!=null) {
			int dataPropertyValue = mlocMetrics.get(attributeName);
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
			}
		}
		}
		
		Hashtable<String, Integer> vgMetrics = metrics.getVGMetric();
		if (!vgMetrics.isEmpty()) {
		for (int i = 0; i < vgMetrics.size(); i++) {
			attributeName = metrics.getAttributeName().get(i);
			individual  = ontology.getIndividual(ontologyURI + attributeName);
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + "VG");
			int dataPropertyValue = vgMetrics.get(attributeName);
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
		}
		}
		
		
		
		Hashtable<String, Integer> nofMetrics = metrics.getNOFMetric();
		System.out.println("the hashtable size: "+nofMetrics.size());
		System.out.println("the classes list: "+metrics.getAllClasses().size());
		if(!nofMetrics.isEmpty()) {
		for (int i = 0; i < nofMetrics.size(); i++) {
			attributeName = metrics.getAllClasses().get(i);
			attributeName = this.removeSuffix(attributeName);
			System.out.println("this is the Class Name: "+attributeName);
			individual  = ontology.getIndividual(ontologyURI + attributeName);
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + "NOF");
			int dataPropertyValue = nofMetrics.get(attributeName);
			System.out.println("this is what i should get: "+attributeName+".java");
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
		}
		}
		Hashtable<String, Integer> nomMetrics = metrics.getNOMMetric();
		if(!nomMetrics.isEmpty()) {
		for (int i = 0; i < nomMetrics.size(); i++) {
			attributeName = metrics.getAllClasses().get(i);
			attributeName = this.removeSuffix(attributeName);
			individual  = ontology.getIndividual(ontologyURI + attributeName);
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + "NOM");
			int dataPropertyValue = nomMetrics.get(attributeName);
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
		}
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
		OntModel model = controller.readOntology("/home/moise/Documents/example/Test.owl");
		Individual individual = model.getIndividual("http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#getSomething");
		DatatypeProperty dataProperty = model.getDatatypeProperty("http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#" + "VG");
		int dataPropertyValue = 1;
		Literal literal = model.createTypedLiteral(dataPropertyValue);
		individual.addProperty(dataProperty,literal);
		controller.writeOntology("/home/moise/Documents/example/Test.owl", model);	
	}
}
