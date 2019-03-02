package univ.annaba.Control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class OntologyController {
	protected OntModel ontology;
	private MyVisitor visitor;
	protected String ontologyPath = "/home/moise/Documents/example/OntoCode.owl";
	protected String ontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
	
	public OntologyController(MyVisitor myVisitor) {
		visitor = myVisitor;
	}
	
	public void writeOntology( String ontologyOutPutPath) {
		OutputStream out;
		OntModel ontology = ModelFactory.createOntologyModel();
		
		this.buildOntology(ontology);
		try {
			out = new FileOutputStream(ontologyOutPutPath);
			ontology.write(out, "RDF/XML");

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
	
	public OntModel readOntology(String ontologyInputPath) {
		
		ontology = ModelFactory.createOntologyModel();
		// use the FileManager to find the input file
		InputStream in = FileManager.get().open(ontologyInputPath);
		if (in == null) {
			throw new IllegalArgumentException("File: " + ontologyPath + " not found");
		}
		ontology.read(in, "", "RDF/XML");
		return ontology;
	}
	
	public OntModel addOntologyElement(ArrayList<String> concepts, String ontologyPath) {
		OntModel ontology = this.readOntology(ontologyPath);
		System.out.println(ontology);
		System.out.println(concepts);
		Iterator<String> i = concepts.iterator();
		while (i.hasNext()) {
			String d = (String) i.next();
			System.out.println("the iteraotor result: "+d);
			OntClass ontClass =  ontology.getOntClass(ontologyURI);
			
			System.out.println("this is the ontClass: "+ontology.getOntClass(ontologyURI));
			
			ontology.createIndividual(ontologyURI + d, ontClass);
		}
		return ontology;
	}
	
	public OntModel buildOntology(OntModel ontology){
		ontologyPath= "/home/moise/Documents/example/OntoCode.owl";
		ontology = this.addOntologyElement(visitor.getMethods(),ontologyPath);
		//ontology = this.addOntologyElement(visitor.getClasses(), ontologyPath);
		//ontology = this.addOntologyElement(visitor.getPackages(), ontologyPath);
		//ontology = this.addOntologyElement(visitor.getVariables(), ontologyPath);
		return ontology;
	}
	
	public MyVisitor getVisitor() {
		return visitor;
	}
	
	public void setVisitor(MyVisitor visitor) {
		this.visitor = visitor;
	}
	
	public static void main(String[] args) {
		MyVisitor myVisitor = new MyVisitor("/home/moise/Documents/example/HelloWorld.java");
		OntologyController controller = new OntologyController(myVisitor);
		OntModel ontModel = controller.readOntology("/home/moise/Documents/example/OntoCode.owl");
		controller.writeOntology("/home/moise/Documents/example/OntoCode.owl");
		
	}
}
