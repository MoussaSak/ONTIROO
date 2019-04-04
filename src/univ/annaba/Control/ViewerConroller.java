package univ.annaba.Control;

import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;


public class ViewerConroller extends OntologyController{
	private String badSmellOntologyPath;
	private String badSmellontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
	private ArrayList<OntClass> ClassesList;
	private ArrayList<Individual> individualList;
	
	
	public ViewerConroller(String badSmellOntologyPath) {
		this.badSmellOntologyPath = badSmellOntologyPath;
		ClassesList = new ArrayList<OntClass>();
		individualList = new ArrayList<Individual>();
	}
	
	public void getClasses() {
		OntModel badSmellOntology = this.readOntology(badSmellOntologyPath);
		OntClass ontClass = badSmellOntology.getOntClass(badSmellontologyURI+"BadSmells");
		if(ontClass != null) {
		ExtendedIterator<?> classes = ontClass.listSubClasses();
		while (classes.hasNext()) {
			OntClass thisClass = (OntClass) classes.next();
			ClassesList.add(thisClass);
			ExtendedIterator<?> instances = thisClass.listInstances();
		      while (instances.hasNext())
		      {
		        Individual thisInstance = (Individual) instances.next();
		        if(!individualList.contains(thisInstance)) {
		        individualList.add(thisInstance);
		        }
		      }
			}
		}
	}
	
	public JTree getHierarchy(JTree tree ){
		
		this.getClasses();
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Thing");
		OntClass individualCLass;
		OntClass individualSuperClass = null ;
		OntClass indiviualSuperSuperClass = null;
		
		for (int i = 0; i < individualList.size(); i++) {
			Individual individual = individualList.get(i);
			individualCLass = individual.getOntClass();
			if(individualCLass.hasSuperClass()) {
			individualSuperClass = individualCLass.getSuperClass();}
			if(individualSuperClass.hasSuperClass()) {
			indiviualSuperSuperClass = individualSuperClass.getSuperClass();}
			
			DefaultMutableTreeNode level1 = new DefaultMutableTreeNode(indiviualSuperSuperClass.getLocalName());
			DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(individualSuperClass.getLocalName());
			DefaultMutableTreeNode level3 = new DefaultMutableTreeNode(individualCLass.getLocalName());
			DefaultMutableTreeNode level4 = new DefaultMutableTreeNode(individual.getLocalName());

			level1.add(level2);
			level2.add(level3);
			level3.add(level4);
			racine.add(level1);
		}
		
		tree = new JTree(racine);
		return tree;
	}
	
	public static void main(String[] args) {
		ViewerConroller controller = new ViewerConroller("C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\BadSmellOntology.owl");
		controller.getHierarchy(new JTree());
	}

}
