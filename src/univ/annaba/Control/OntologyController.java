package univ.annaba.Control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class OntologyController {
	public OntologyController() {
		
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
	 * write the Ontology to outputFile.
	 * @param ontology
	 * @param ontologyOutPutPath
	 */
	public void writeOntology(OntModel ontology, String ontologyOutPutPath) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(ontologyOutPutPath);
			ontology.write(out, "RDF/XML");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * write the Ontology Model to outputFile.
	 * @param ontology Model
	 * @param ontologyOutPutPath
	 */
	public void writeOntology(Model model, String outputhPath){
		OutputStream out = null;
		try {
			out = new FileOutputStream(outputhPath);
			model.write(out, "RDF/XML");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
