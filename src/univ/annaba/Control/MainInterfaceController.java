package univ.annaba.Control;

import java.util.ArrayList;
import java.util.Hashtable;


public class MainInterfaceController {
	private MyVisitor myVisitor;
	private Hashtable<String,ArrayList<String>> conceptsReport;
	private OntologyController ontologyController;
	
	public MainInterfaceController() {
	}
	
	public String generateConceptsReport(String sourceCodeFilePath) {
		String text;
		myVisitor = new MyVisitor(sourceCodeFilePath);
		conceptsReport = myVisitor.getConceptsReport();
		
		text = 	"Variables: "+this.getElements("Variables")+ 
				"Methods: "+this.getElements("Methods") +
				"Classes: " +this.getElements("Classes") +
				"Packages: " + this.getElements("Packages");
				
		return text;
	}
	
	public String getElements(String element) {
		String variables = "";
		ArrayList<String> varConcepts = conceptsReport.get(element);
		for (int i = 0; i < varConcepts.size(); i++) {
			variables += conceptsReport.get(element).get(i)+"\n";
		}
		return variables;
	}
	
	public MetricsReport generateMetricsReport() {
		MetricsReport metricsReport = new MetricsReport();
		metricsReport.setLOC(1);
		return metricsReport;
		
	}
	
	public void generateOntology(String ontologyOutPutPath){
		if(myVisitor!= null){
		ontologyController = new OntologyController(myVisitor);
		}
		ontologyController.writeOntology(ontologyOutPutPath);
	}
	
}
