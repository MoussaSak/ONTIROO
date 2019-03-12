package univ.annaba.Control;

import java.util.ArrayList;
import java.util.Hashtable;

import univ.annaba.Model.MetricsParser;


public class MainInterfaceController {
	private MyVisitor myVisitor;
	
	private Hashtable<String,ArrayList<String>> conceptsReport;
	private OntologyController ontologyController;
	private String metricsPath = "/home/moise/Documents/example/Metrics";
	
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
	
	public String generateMetricsReport() {
		String txt;
		MetricsParser parser = new MetricsParser(metricsPath);
		txt= "Mloc: "+ parser.getMetricNameAndValue("MLOC").toString()+"\n"+
				"NOF: "+parser.getMetricNameAndValue("NOF").toString()+"\n"+
				"TLOC: "+parser.getMetricNameAndValue("TLOC").toString()+"\n"+
				"Methods with Parameters: "+parser.getMetricNameAndValue("PAR").toString()+"\n"+
				"VG: "+parser.getMetricNameAndValue("VG").toString()+"\n"+
				"DIT"+ parser.getMetricNameAndValue("DIT").toString();
		return txt;
	}
	
	public String getElements(String element) {
		String variables = "";
		ArrayList<String> varConcepts = conceptsReport.get(element);
		for (int i = 0; i < varConcepts.size(); i++) {
			variables += conceptsReport.get(element).get(i)+"\n";
		}
		return variables;
	}
	
	public void generateOntology(String ontologyOutPutPath){
		if(myVisitor!= null){
		ontologyController = new OntologyController(myVisitor);
		}
		ontologyController.writeOntology(ontologyOutPutPath);
	}
	
}
