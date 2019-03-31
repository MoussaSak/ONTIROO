package univ.annaba.Control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;


import univ.annaba.Model.MetricsParser;

public class CodeOntologyController extends OntologyController{
	protected MyVisitor visitor;
	protected OntModel codeOntology;
	protected String ontologyPath = "C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\OntoCode.owl";
	protected String ontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
	
	protected Hashtable<String, ArrayList<String>> report;
	
	
	public CodeOntologyController(MyVisitor visitor) {
		this.visitor = visitor;
		report = visitor.getConceptsReport();
		codeOntology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
	}
	
	public CodeOntologyController() {
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
		codeOntology = this.addOntologyElements(ontologyOutPutPath, report);
		this.writeOntology(codeOntology, ontologyOutPutPath);
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
		codeOntology = this.addOntologyMetrics(ontologyOutPutPath);
		this.writeOntology(codeOntology, ontologyOutPutPath);
		
	}
	
	/**
	 * adds the ontology methods, classes, packages and variables concepts. 
	 * @param ontologyPath
	 * @param report
	 * @return Ontology
	 */
	public OntModel addOntologyElements(String ontologyPath, Hashtable<String, ArrayList<String>> report) {
		codeOntology = this.readOntology(ontologyPath);
		this.addMethodsConcepts(report);
		this.addClassesConcepts(report);
		this.addPackagesConcepts(report);
		this.addVariablesConcepts(report);
		return codeOntology;
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
					OntClass ontClass =  codeOntology.getOntClass(codeOntology.getNsPrefixURI("")+"Method");
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
					OntClass ontClass =  codeOntology.getOntClass(codeOntology.getNsPrefixURI("")+"Classs");
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
					OntClass ontClass =  codeOntology.getOntClass(codeOntology.getNsPrefixURI("")+"Package");
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
					OntClass ontClass =  codeOntology.getOntClass(codeOntology.getNsPrefixURI("")+"Variable");
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
		return codeOntology;
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
			DatatypeProperty dataProperty = codeOntology.getDatatypeProperty(ontologyURI + metric);
			individual  = codeOntology.getIndividual(ontologyURI + attributeName);
			if(individual!=null) {
			int dataPropertyValue = mlocMetrics.get(attributeName);
			Literal literal = codeOntology.createTypedLiteral(dataPropertyValue);
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
			individual  = codeOntology.getIndividual(ontologyURI + attributeName);
			DatatypeProperty dataProperty = codeOntology.getDatatypeProperty(ontologyURI + metric);
			int dataPropertyValue = nomMetrics.get(attributeName);
			Literal literal = codeOntology.createTypedLiteral(dataPropertyValue);
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

}
