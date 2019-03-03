package univ.annaba.Control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class OntologyController {
	protected OntModel ontology;
	protected MyVisitor visitor;
	protected String ontologyPath = "/home/moise/Documents/example/OntoCode.owl";
	protected String ontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
	
	public OntologyController(MyVisitor myVisitor) {
		visitor = myVisitor;
	}
	
	public void writeOntology( String ontologyOutPutPath) {
		OutputStream out;
		OntModel ontology = ModelFactory.createOntologyModel();
		ontology = this.buildOntology(ontology);
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
	
	public OntModel addOntologyElement(String ontologyPath, ArrayList<String> concepts) {
		OntModel ontology = this.readOntology(ontologyPath);
		Hashtable<String, ArrayList<String>> hash = visitor.getConceptsReport();
		
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
	public OntModel addOntologyMethods(String ontologyPath, ArrayList<String> methods) {
		OntModel ontology = this.readOntology(ontologyPath);
		Iterator<String> i = methods.iterator();
		while (i.hasNext()) {
			String method = (String) i.next();
			OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Method");
			ontClass.createIndividual(ontologyURI+method);
		}
		return ontology;
	}
	
	public OntModel addOntologyClasses(String ontologyPath, ArrayList<String> classes) {
		OntModel ontology = this.readOntology(ontologyPath);
		Iterator<String> i = classes.iterator();
		while (i.hasNext()) {
			String class1 = (String) i.next();
			OntClass ontClass =  ontology.getOntClass(ontology.getNsPrefixURI("")+"Class");
			ontClass.createIndividual(ontologyURI+class1);
		}
		return ontology;
	}
	*/
	
	public OntModel buildOntology(OntModel ontology){
		
		ontology = this.addOntologyElement(ontologyPath, visitor.getMethods());
		//ontology = this.addOntologyElement(ontologyPath, visitor.getClasses(),"Classs");
		//ontology = this.addOntologyElement(ontologyPath, visitor.getPackages(),"Package");
		//ontology = this.addOntologyElement(ontologyPath, visitor.getVariables(),"Variable");

		return ontology;
	}
	
	public MyVisitor getVisitor() {
		return visitor;
	}
	
	public void setVisitor(MyVisitor visitor) {
		this.visitor = visitor;
	}
	
	public static void main(String[] args) {
		String ontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
		MyVisitor myVisitor = new MyVisitor("/home/moise/Documents/example/HelloWorld.java");
		OntologyController controller = new OntologyController(myVisitor);
		OntModel model = controller.readOntology("/home/moise/Documents/example/OntoCode.owl");
		controller.writeOntology("/home/moise/Documents/example/OntoCode.owl");
	
	}
}
