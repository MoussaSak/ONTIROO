package univ.annaba.Control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

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
	protected String sourceCodePath;
	
	
	public MyVisitor(String sourceCodePath) {
		this.sourceCodePath = sourceCodePath;
		
		try {
			init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();} catch (IOException e) { e.printStackTrace();
		}
 	}
	
	public void init() throws FileNotFoundException, IOException {
		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(sourceCodePath)); // Parse this file
		JavaLexer lexer = new JavaLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokens);
		ParseTree tree = parser.compilationUnit();
		this.visit(tree);
	}
	
	@Override
	public Void visitMyPackageName(MyPackageNameContext ctx) {
		String m = ctx.getText();
		packages.add(m);
		return super.visitMyPackageName(ctx);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public Void visitMyMethodName(MyMethodNameContext ctx) {
		String m = ctx.getText();
		ctx.getStart();
		methods.add(m);
		return super.visitMyMethodName(ctx);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public Void visitMyClassName(MyClassNameContext ctx) {
		String c = ctx.getText();
		classes.add(c);
		return super.visitMyClassName(ctx);

	}

	////////////////////////////////////////////////////////////////////////////////////
	public Void visitMyVariableName(MyVariableNameContext ctx) {
		String v = ctx.getText();
		variables.add(v);
		return super.visitMyVariableName(ctx);
	}

	public Hashtable<String, ArrayList<String>> getConceptsReport() {
		Hashtable<String, ArrayList<String>> hashtable = new Hashtable<String, ArrayList<String>>();
		hashtable.put("Packages", getPackages());hashtable.put("Classes", getClasses());
		hashtable.put("Methods", getMethods());hashtable.put("Variables", getVariables());
		return hashtable;
	}
	
	
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

		MyVisitor visitor = new MyVisitor("/home/moise/Documents/example/HelloWorld.java");
		System.out.println(visitor.getMethods());
		
	}
}
