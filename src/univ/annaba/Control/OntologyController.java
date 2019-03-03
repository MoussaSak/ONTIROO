package univ.annaba.Control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

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
	
	public void writeOntology( String ontologyOutPutPath) {
		OutputStream out;
		
		OntModel ontology = ModelFactory.createOntologyModel();
		ontology = this.addOntologyElements(ontologyPath, report);
		try {
			out = new FileOutputStream(ontologyOutPutPath);
			ontology.write(out, "RDF/XML");

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
	
	public OntModel readOntology(String ontologyInputPath) {
		ontology = ModelFactory.createOntologyModel();
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
