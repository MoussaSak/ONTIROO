package univ.annaba.Model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import japa.parser.ast.body.Parameter;

import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

/**
 * the {@code Visitor} visits  methods and constructors of java source file
 * @author Moussa
 * @see VoidVisitorAdapter
 * @see MethodDeclaration
 * @see ConstructorDeclaration
 */
public class Visitor extends VoidVisitorAdapter<Object> {
	private String constructors = "" ;
	private String methods = "" ;
	private Hashtable<String, String> body ;
	private Hashtable<String, Integer> mloc;
	private List<VariableDeclarator>  fields;
	private Hashtable<String,List<Parameter> > parameters;
	private int NOF;
	private int NOL;
	

	

	public Visitor() {
		body = new Hashtable<String, String>();
		mloc = new Hashtable<String, Integer>();
		fields = new ArrayList<VariableDeclarator>();
		parameters = new Hashtable<String, List<Parameter>>();
		setNOF(0);
		setNOL(0);
	}
	
	/**
	 * visits the Methods on the source file
	 */
	@Override
	public void visit(MethodDeclaration n, Object arg) {
		int MLOC=0;
		if (n.getName()!=null) {
			methods += n.getName()+"." ;
			body.put(n.getName(), n.getBody().toString());
			MLOC = (n.getEndLine()) - ( n.getBeginLine());
			mloc.put(n.getName(), MLOC);
			parameters.put(n.getName(),n.getParameters());
		}
	}
	
	/**
	 * visits the constructors on the source file
	 */
	@Override
	public void visit(ConstructorDeclaration n, Object arg) {
		int MLOC=0;
		if(n.getName()!=null){
			constructors += n.getName()+"." ;
			body.put(n.getName(), n.getBlock().toString());
			MLOC = (n.getEndLine()) - ( n.getBeginLine());
			mloc.put(n.getName(), MLOC);
		}
	}
	
	@Override
	public void visit(ClassExpr n, Object arg) {
		// TODO Auto-generated method stub
		NOL = n.getEndLine();
	}
	
	@Override
	public void visit(FieldDeclaration n, Object arg) {
		fields.addAll(n.getVariables());
		setNOF(fields.size());
	}
	
	/**
	 * gets the constructors 
	 * @return array of string contains the constructors
	 */
	public String getConstructors() {
		return constructors;
	}
	
	/**
	 * gets the methods 
	 * @return array of methods contains the methods
	 */
	public String getMethods() {
		return methods;
	}
	
	/**
	 * gets the methods body
	 * @return the methods body
	 */
	public Hashtable<String, String> getBody() {
		return body;
	}
	
	public  List<VariableDeclarator> getFields() {
		return fields;
	}
	
	public void setFields(List<VariableDeclarator> fields) {
		this.fields = fields;
	}
	
	public Hashtable<String, Integer> getMloc() {
		return mloc;
	}

	public void setMloc(Hashtable<String, Integer> mloc) {
		this.mloc = mloc;
	}
	
	public Hashtable<String, List<Parameter>> getParameters() {
		return parameters;
	}

	public void setParameters(Hashtable<String, List<Parameter>> parameters) {
		this.parameters = parameters;
	}

	public int getNOF() {
		return NOF;
	}

	public void setNOF(int nOF) {
		NOF = nOF;
	}
	
	public int getNOL() {
		return NOL;
	}

	public void setNOL(int nOL) {
		NOL = nOL;
	}
}
