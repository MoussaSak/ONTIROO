package univ.annaba.Control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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

	static ArrayList<String> methods = new ArrayList<String>();
	static ArrayList<String> variables = new ArrayList<String>();
	static ArrayList<String> classes = new ArrayList<String>();
	static ArrayList<String> packagee = new ArrayList<String>();
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

		OntModel ontology = readOntology();
		packagee.add(m);
		Iterator<String> i = packagee.iterator();
		while (i.hasNext()) {
			String d = (String) i.next();
			System.out.println("package  : " + d);
			String URI = ontologyURI;
			OntClass packagee = ontology.getOntClass(ontologyURI);
			ontology.createIndividual(URI + d, packagee);
		}
		this.writeOntology(ontology);
		return super.visitMyPackageName(ctx);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public Void visitMyMethodName(MyMethodNameContext ctx) {
		String a = ctx.getText();

		OntModel ontology = readOntology();

		System.out.println("method  : " + a);
		methods.add(a);
		Iterator<String> i = methods.iterator();
		while (i.hasNext()) {
			String x = (String) i.next();

			String URI = ontologyURI;
			OntClass method = ontology.getOntClass(ontologyURI + "Method");
			ontology.createIndividual(URI + x, method);
		}

		writeOntology(ontology);
		return super.visitMyMethodName(ctx);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public Void visitMyClassName(MyClassNameContext ctx) {
		String a = ctx.getText();
		OntModel ontology = readOntology();

		System.out.println("Class  : " + a);

		classes.add(a);
		Iterator<String> i = classes.iterator();
		while (i.hasNext()) {
			String g = (String) i.next();

			String URI = ontologyURI;
			OntClass Classs = ontology.getOntClass(ontologyURI + "Classs");
			ontology.createIndividual(URI + g, Classs);
		}
		writeOntology(ontology);
		return super.visitMyClassName(ctx);

	}

	////////////////////////////////////////////////////////////////////////////////////
	public Void visitMyVariableName(MyVariableNameContext ctx) {
		String b = ctx.getText();

		System.out.println("variable : " + ctx.getText());

		OntModel ontology = ModelFactory.createOntologyModel();
		String inputFileName = ontologyPath;
		// use the FileManager to find the input file
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName + " not found");
		}
		ontology.read(in, "", "RDF/XML");
		variables.add(b);
		Iterator<String> i = variables.iterator();
		while (i.hasNext()) {
			String f = (String) i.next();

			String URI = ontologyURI;
			OntClass variable = ontology.getOntClass(ontologyURI + "Variable");
			ontology.createIndividual(URI + f, variable);
		}

		writeOntology(ontology);

		return super.visitMyVariableName(ctx);
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

	public static ArrayList<String> getMethods() {
		return methods;
	}

	public static void setMethods(ArrayList<String> methods) {
		MyVisitor.methods = methods;
	}

	public static ArrayList<String> getVariables() {
		return variables;
	}

	public static void setVariables(ArrayList<String> variables) {
		MyVisitor.variables = variables;
	}

	public static ArrayList<String> getClasses() {
		return classes;
	}

	public static void setClasses(ArrayList<String> classes) {
		MyVisitor.classes = classes;
	}

	public static ArrayList<String> getPackagee() {
		return packagee;
	}

	public static void setPackagee(ArrayList<String> packagee) {
		MyVisitor.packagee = packagee;
	}

	public static void main(String[] args) throws IOException {

		String javaExampleURI = "/home/moise/Documents/example/Application.java";

		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(javaExampleURI)); // Parse this file

		JavaLexer lexer = new JavaLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		JavaParser parser = new JavaParser(tokens);
		ParseTree tree = parser.compilationUnit(); // point de d�part de l'analyse de fichier
		MyVisitor visitor = new MyVisitor();
		visitor.visit(tree);
	}
}
