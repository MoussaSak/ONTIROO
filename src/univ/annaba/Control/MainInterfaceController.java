package univ.annaba.Control;

import java.util.ArrayList;
import java.util.Hashtable;

import univ.annaba.Model.MetricsParser;


public class MainInterfaceController {
	private MyVisitor myVisitor;
	
	private Hashtable<String,ArrayList<String>> conceptsReport;
	private Hashtable<String, Hashtable<String, Integer>> allMetrics;
	private CodeOntologyController ontologyController;
	private String metricsPath = "C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\Metrics.xml";
	private String codeOntologyOutputPath;
	private static MetricsParser metricParser;
	
	public MainInterfaceController(String sourceCodeFilePath) {
		myVisitor = new MyVisitor(sourceCodeFilePath);
		conceptsReport = myVisitor.getConceptsReport();
		metricParser = new MetricsParser(metricsPath);
		allMetrics = metricParser.getAllMetrics();
	}
	
	public String generateConceptsReport() {
		String text;
		
		text = 	"Methods: \n" +this.getElements("Methods") +
				"Classes: \n " +this.getElements("Classes") +
				"Packages: \n " + this.getElements("Packages");
				
		return text;
	}
	
	
	 public String generateConceptsFromMR(){
		 String text;
			
		 text = 	"Methods: "+metricParser.getAllMethods().toString() +
					"Classes: " +metricParser.getAllClasses().toString() +
					"Packages: " + metricParser.getAllPackages().toString();
					
		return text;
	 }
	
	public String generateMetricsReport() {
		String txt;
		metricParser = new MetricsParser(metricsPath);
		txt= "Mloc: "+ metricParser.getMlocMetric().toString()+"\n"+
				"NOF: "+metricParser.getNOFMetric().toString()+"\n"+
				"TLOC: "+metricParser.getTLOCMetric().toString()+"\n"+
				"Methods with Parameters: "+metricParser.getPARMetric().toString()+"\n"+
				"VG: "+metricParser.getVGMetric()+"\n"+
				"DIT"+ metricParser.getDITMetric().toString();
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
		this.codeOntologyOutputPath = ontologyOutputPath;
		if(myVisitor!= null){
		ontologyController = new CodeOntologyController(myVisitor);
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
	
	public Hashtable<String, ArrayList<String>> getConceptsReport() {
		return conceptsReport;
	}

	public Hashtable<String, Hashtable<String, Integer>> getAllMetrics() {
		return allMetrics;
	}

	public void generateBadSmellOntology(String badSmellOntologyOutputPath) {
		BadSmellOntologyController controller= new BadSmellOntologyController(badSmellOntologyOutputPath);
		controller.identifyLongMethod(codeOntologyOutputPath);
		controller.addConcepts();
	}
	
}
