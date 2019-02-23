package univ.annaba.Control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import univ.annaba.Control.JavaParser.MyClassNameContext;
import univ.annaba.Control.JavaParser.MyMethodNameContext;
import univ.annaba.Control.JavaParser.MyPackageNameContext;
import univ.annaba.Control.JavaParser.MyVariableNameContext;

/**
 * the {@code Visitor} visits packages, classes, methods and variables of java
 * source file
 * 
 * @author Moussa
 */

@SuppressWarnings("deprecation")
public class MyVisitor extends JavaBaseVisitor<Void> {

	protected ArrayList<String> methods = new ArrayList<String>();
	protected ArrayList<String> variables = new ArrayList<String>();
	protected ArrayList<String> classes = new ArrayList<String>();
	protected ArrayList<String> packages = new ArrayList<String>();
	protected String ontologyPath = "/home/moise/Documents/example/OntoCode.owl";
	protected String ontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";

	public void writeOntology(OntModel ontology) {
		OutputStream out;
		try {
			out = new FileOutputStream(ontologyPath);
			ontology.write(out, "RDF/XML");

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	public OntModel readOntology() {
		OntModel ontology = ModelFactory.createOntologyModel();
		String inputFileName = ontologyPath;
		// use the FileManager to find the input file
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName + " not found");
		}
		ontology.read(in, "", "RDF/XML");
		return ontology;
	}

	@Override
	public Void visitMyPackageName(MyPackageNameContext ctx) {
		String m = ctx.getText();
		OntModel ontology = this.addOntologyElement(m, packages);
		this.writeOntology(ontology);
		return super.visitMyPackageName(ctx);
	}

	public OntModel addOntologyElement(String element, ArrayList<String> concepts) {

		OntModel ontology = this.readOntology();

		concepts.add(element);
		Iterator<String> i = concepts.iterator();
		while (i.hasNext()) {
			String d = (String) i.next();
			String URI = ontologyURI;
			OntClass ontClass = ontology.getOntClass(ontologyURI);
			ontology.createIndividual(URI + d, ontClass);
		}
		return ontology;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public Void visitMyMethodName(MyMethodNameContext ctx) {
		String m = ctx.getText();
		OntModel ontology = this.addOntologyElement(m, methods);
		this.writeOntology(ontology);
		return super.visitMyMethodName(ctx);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public Void visitMyClassName(MyClassNameContext ctx) {
		String c = ctx.getText();
		OntModel ontology = this.addOntologyElement(c, classes);
		this.writeOntology(ontology);
		return super.visitMyClassName(ctx);

	}

	////////////////////////////////////////////////////////////////////////////////////
	public Void visitMyVariableName(MyVariableNameContext ctx) {
		String v = ctx.getText();
		OntModel ontology = this.addOntologyElement(v, variables);
		this.writeOntology(ontology);
		return super.visitMyVariableName(ctx);
	}

	public void visitSourceCode(String sourceCodePath) throws FileNotFoundException, IOException {
		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(sourceCodePath)); // Parse this file
		JavaLexer lexer = new JavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokens);
		ParseTree tree = parser.compilationUnit();
		this.visit(tree);
	}

	public Hashtable<String, ArrayList<String>> getConceptsReport() {
		Hashtable<String, ArrayList<String>> hashtable = new Hashtable<String, ArrayList<String>>();
		hashtable.put("Packages", packages);hashtable.put("Classes", classes);hashtable.put("Methods", methods);hashtable.put("Variables", variables);
		return hashtable;
	}
	
	/*
	 * public Void visitPrimitiveType(PrimitiveTypeContext ctx) {
	 * System.out.println("variable type : " + ctx.getText()); return
	 * super.visitPrimitiveType(ctx); }
	 */

	/*
	 * public Void visitClassOrInterfaceModifier(ClassOrInterfaceModifierContext
	 * ctx) { System.out.println("modifier : " + ctx.getText()); return
	 * super.visitClassOrInterfaceModifier(ctx); }
	 */

	public ArrayList<String> getMethods() {
		return methods;
	}

	public void setMethods(ArrayList<String> methods) {
		this.methods = methods;
	}

	public ArrayList<String> getVariables() {
		return variables;
	}

	public void setVariables(ArrayList<String> variables) {
		this.variables = variables;
	}

	public ArrayList<String> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<String> classes) {
		this.classes = classes;
	}

	public ArrayList<String> getPackages() {
		return packages;
	}

	public void setPackages(ArrayList<String> packagee) {
		this.packages = packagee;
	}

	public static void main(String[] args) throws IOException {

		String javaExampleURI = "/home/moise/Documents/example/HelloWorld.java";

		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(javaExampleURI)); // Parse this file

		JavaLexer lexer = new JavaLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		JavaParser parser = new JavaParser(tokens);
		ParseTree tree = parser.compilationUnit(); // point de d�part de l'analyse de fichier
		MyVisitor visitor = new MyVisitor();
		visitor.visit(tree);
		Hashtable<String, ArrayList<String>> hashtable = visitor.getConceptsReport();
		System.out.println(hashtable.get("Variables"));

	}
}
