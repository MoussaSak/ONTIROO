package univ.annaba.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class MetricsParser {
	private String path;
	private List<String> attributeName;
	private List<String> attributeValue;
	private List<String> attributeSource;
	private List<String> attributePackage;
	private Document document;
	public int length;

	public MetricsParser(String path) {
		this.path = path;
		init();
		attributeName = new ArrayList<String>();
		attributeValue = new ArrayList<String>();
		attributeSource = new ArrayList<String>();
		attributePackage = new ArrayList<String>();
	}

	public void init() {
		SAXBuilder parser = new SAXBuilder();
		File xml = new File(path);
		try {
			document = parser.build(xml);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void getMetrics(String metric)  {
		Element root = document.getRootElement();
		List<?> listOfMetric = root.getChildren();
		Iterator<?> listOfMetricIt = listOfMetric.iterator();
		attributeName.clear();
		attributeValue.clear();
		attributeSource.clear();
		attributePackage.clear();
		while (listOfMetricIt.hasNext()) {
			Element currentModel = (Element) listOfMetricIt.next();
			if (metric.equals(currentModel.getAttribute("id").getValue())){ 
			List<?> values = currentModel.getChildren();
			Element listOfvalues = (Element) values.get(0);
			List<?> listOflistOfvalues = listOfvalues.getChildren();
			Iterator<?> valueIt = listOflistOfvalues.iterator();

			while (valueIt.hasNext()) {
				Element currentCompartment = (Element) valueIt.next();
				attributeName.add(currentCompartment.getAttribute("name").getValue());
				attributeValue.add(currentCompartment.getAttribute("value").getValue());
				if(currentCompartment.getAttribute("source")!= null) {
					String className = currentCompartment.getAttribute("source").getValue();
					if(!attributeSource.contains(className))
				attributeSource.add(currentCompartment.getAttribute("source").getValue());
				}
				if(currentCompartment.getAttribute("package")!= null) {
					String packageName = currentCompartment.getAttribute("package").getValue();
					if(!attributePackage.contains(packageName))
				attributePackage.add(currentCompartment.getAttribute("package").getValue());
				}
				}
			
		}
		}
	}
	
	public Hashtable<String, Integer> getMetricNameAndValue(String metric){
		this.getMetrics(metric);
		Hashtable<String, Integer> hashtable = new Hashtable<String, Integer>();
		for (int i = 0; i < attributeName.size(); i++) {
			hashtable.put(getAttributeName().get(i), Integer.parseInt(getAttributeValue().get(i)));
		}
		return hashtable;
	}
	/**
	 * 
	 * @return
	 */
	
	public Hashtable<String, Hashtable<String, Integer>> getAllMetrics(){
		Hashtable<String, Hashtable<String, Integer>> allMetrics = new Hashtable<String, Hashtable<String, Integer>>();
		allMetrics.put("MLOC", getMlocMetric());
		allMetrics.put("VG",getVGMetric());
		allMetrics.put("NOF", getNOFMetric());
		allMetrics.put("NSF",getNSFMetric());
		//allMetrics.put("TLOC", getTLOCMetric());
		allMetrics.put("NOM",getNOFMetric());
		allMetrics.put("NSM",getNSMMetric());
		allMetrics.put("DIT",getDITMetric());
		allMetrics.put("PAR",getPARMetric());
		return allMetrics;
	}
	
	/**
	 * get MLOC metric from the metrics file
	 * @return
	 */
	public Hashtable<String,Integer> getMlocMetric() {
		return getMetricNameAndValue("MLOC");
	}
	
	/**
	 * get VG (McCabe Cyclomatic Complexity) metric from the metrics file
	 * @return
	 */
	public Hashtable<String,Integer> getVGMetric() {
		return getMetricNameAndValue("VG");
	}
	
	/**
	 * get the NOF (number of fields) from the metrics file
	 * @return
	 */
	public Hashtable<String,Integer> getNOFMetric() {
		return getMetricNameAndValue("NOF");
	}
	
	/**
	 * get number NOF (Number of Static Fields) from the metrics file.
	 * @return
	 */
	public Hashtable<String,Integer> getNSFMetric() {
		return getMetricNameAndValue("NSF");
	}
	/**
	 * get NOM (number of methods) from the metrics file.
	 * @return
	 */
	public Hashtable<String,Integer> getNOMMetric() {
		return getMetricNameAndValue("NOM");
	}
	/**
	 * get TLOC (total line of code) from the metrcis file.
	 * @return
	 */
	public String getTLOCMetric() {
		Element root = document.getRootElement();
		List<?> listOfMetric = root.getChildren();
		Iterator<?> listOfMetricIt = listOfMetric.iterator();
		String tloc = "";
		while (listOfMetricIt.hasNext()) {
			Element currentModel = (Element) listOfMetricIt.next();
			if ("TLOC".equals(currentModel.getAttribute("id").getValue())){ 
			List<?> values = currentModel.getChildren();
			Element listOfvalues = (Element) values.get(0);
			tloc = listOfvalues.getAttributeValue("value");
		}
		}
		return tloc;
	}
	/**
	 * get NSM (number of static Method) from the metrics file.
	 * @return
	 */
	public Hashtable<String,Integer> getNSMMetric() {
		return getMetricNameAndValue("NSM");
	}
	/**
	 * get NOC (Number of classes) from the source file.
	 * @return
	 */
	public Hashtable<String,Integer> getNOCMetric() {
		return getMetricNameAndValue("NOC");
	}
	/**
	 * get DIT (Depth of Inheritance Tree) from the metrics file.
	 * @return
	 */
	public Hashtable<String,Integer> getDITMetric() {
		return getMetricNameAndValue("DIT");
	}
	
	public Hashtable<String,Integer> getPARMetric() {
		return getMetricNameAndValue("PAR");
	}
	
	/**
	 * gets all the methods that exists within a project.
	 * @return a List of all methods.
	 */
	public List<String> getAllMethods() {
		this.getMlocMetric();
		return this.getAttributeName();
	}
	/**
	 * gets all classes that exists within a project.
	 * @return a List of all classes.
	 */
	public List<String> getAllClasses() {
		this.getVGMetric();
		return this.getAttributeSource();
	}
	/**
	 * gets all packages that exists within a project.
	 * @return a List of Packages.
	 */
	public List<String> getAllPackages() {
		this.getVGMetric();
		return this.getAttributePackage();
	}
	
	/**
	 * @return the attributeName
	 */
	public List<String> getAttributeName() {
		return attributeName;
	}

	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(List<String> attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @return the attributeValue
	 */
	public List<String> getAttributeValue() {
		return attributeValue;
	}

	/**
	 * @param attributeValue the attributeValue to set
	 */
	public void setAttributeValue(List<String> attributeValue) {
		this.attributeValue = attributeValue;
	}

	/**
	 * @return the attributeSource
	 */
	public List<String> getAttributeSource() {
		return attributeSource;
	}

	/**
	 * @param attributeSource the attributeSource to set
	 */
	public void setAttributeSource(List<String> attributeSource) {
		this.attributeSource = attributeSource;
	}

	/**
	 * @return the attributePackage
	 */
	public List<String> getAttributePackage() {
		return attributePackage;
	}

	/**
	 * @param attributePackage the attributePackage to set
	 */
	public void setAttributePackage(List<String> attributePackage) {
		this.attributePackage = attributePackage;
	}

	public static void main(String[] args)  {
		MetricsParser builder = new MetricsParser("C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\Metrics.xml");
		
		System.out.println(builder.getMetricNameAndValue("DIT"));
		//System.out.println(builder.getAllClasses());
				
		
	}
}
