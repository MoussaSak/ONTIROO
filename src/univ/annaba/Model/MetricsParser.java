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
				attributeSource.add(currentCompartment.getAttribute("source").getValue());
				}
				if(currentCompartment.getAttribute("package")!= null) {
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
		MetricsParser builder = new MetricsParser("/home/moise/Documents/example/Metrics");
		System.out.println(builder.getMetricNameAndValue("NOF"));
		
	}
}
