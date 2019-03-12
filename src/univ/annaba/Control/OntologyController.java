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
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import univ.annaba.Model.MetricsParser;

public class OntologyController {
	protected OntModel ontology;
	protected MyVisitor visitor;
	protected String ontologyPath = "/home/moise/Documents/example/OntoCode.owl";
	protected String ontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
	protected Hashtable<String, ArrayList<String>> report;
	
	public OntologyController(MyVisitor visitor) {
		this.visitor = visitor;
		report = visitor.getConceptsReport();
	}
	
	public void writeOntologyConcepts( String ontologyOutPutPath) {
		OutputStream out = null;
		OntModel ontology = ModelFactory.createOntologyModel();
		try {
			copyFileUsingStream(ontologyPath, ontologyOutPutPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ontology = this.addOntologyElements(ontologyOutPutPath, report);
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
	
	public void writeOntologyMetrics( String ontologyOutPutPath) {
		OutputStream out = null;
		OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		
		try {
			copyFileUsingStream(ontologyPath, ontologyOutPutPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ontology = this.addOntologyMetrics(ontologyOutPutPath);
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
	
	public OntModel addOntologyMetrics(String ontologyPath){
		OntModel ontology = this.readOntology(ontologyPath);
		MainInterfaceController controller = new MainInterfaceController();
		MetricsParser metrics = controller.getParser();
		Hashtable<String, Integer> hashtable = metrics.getMetricNameAndValue("MLOC");
		String attributeName="";
		Individual individual = null;
		for (int i = 0; i < hashtable.size(); i++) {
			attributeName = metrics.getAttributeName().get(i);
			individual  = ontology.getIndividual(ontologyURI + attributeName);
		
			DatatypeProperty dataProperty = ontology.getDatatypeProperty(ontologyURI + "MLOC");
			int dataPropertyValue = hashtable.get(attributeName);
			Literal literal = ontology.createTypedLiteral(dataPropertyValue);
			individual.addProperty(dataProperty,literal);
		}
		return ontology;
	}
	
	private static void copyFileUsingStream(String sourcePath, String destPath) throws IOException {
		File source = new File(sourcePath);
		File dest = new File(destPath);
		FileUtils.copyFile(source, dest);
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
