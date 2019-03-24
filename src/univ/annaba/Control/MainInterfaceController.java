package univ.annaba.Control;

import java.util.ArrayList;
import java.util.Hashtable;

import univ.annaba.Model.MetricsParser;


public class MainInterfaceController {
	private MyVisitor myVisitor;
	
	private Hashtable<String,ArrayList<String>> conceptsReport;
	private OntologyController ontologyController;
	private String metricsPath = "/home/moise/Documents/example/Metrics.xml";
	private static MetricsParser metricParser;
	
	public MainInterfaceController() {
	}
	
	public String generateConceptsReport(String sourceCodeFilePath) {
		String text;
		myVisitor = new MyVisitor(sourceCodeFilePath);
		conceptsReport = myVisitor.getConceptsReport();
		
		text = 	"Methods: "+this.getElements("Methods") +
				"Classes: " +this.getElements("Classes") +
				"Packages: " + this.getElements("Packages");
				
		return text;
	}
	 public String generateConceptsFromMR(){
		 String text;
		 metricParser = new MetricsParser(metricsPath);
		 
			
		 text = 	"Methods: "+metricParser.getAllMethods().toString() +
					"Classes: " +metricParser.getAllClasses().toString() +
					"Packages: " + metricParser.getAllPackages().toString();
					
			return text;
	 }
	
	public String generateMetricsReport() {
		String txt;
		metricParser = new MetricsParser(metricsPath);
		txt= "Mloc: "+ metricParser.getMetricNameAndValue("MLOC").toString()+"\n"+
				"NOF: "+metricParser.getMetricNameAndValue("NOF").toString()+"\n"+
				"TLOC: "+metricParser.getMetricNameAndValue("TLOC").toString()+"\n"+
				"Methods with Parameters: "+metricParser.getMetricNameAndValue("PAR").toString()+"\n"+
				"VG: "+metricParser.getMetricNameAndValue("VG").toString()+"\n"+
				"DIT"+ metricParser.getMetricNameAndValue("DIT").toString();
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
	
	public void generateOntology(String ontologyOutputPath){
		if(myVisitor!= null){
		ontologyController = new OntologyController(myVisitor);
		}
		ontologyController.addOntologyConcepts(ontologyOutputPath);
	}
	
	public void enrichOntology(String ontologyOutputPath){
		if(ontologyController!= null){
		ontologyController.writeOntologyMetrics(ontologyOutputPath);
		}
		}
	
	/**
	 * @return the parser
	 */
	public static MetricsParser getMetricParser() {
		return metricParser;
	}

	/**
	 * @param parser the parser to set
	 */
	public void setMetricParser(MetricsParser parser) {
		MainInterfaceController.metricParser = parser;
	}
	
}
