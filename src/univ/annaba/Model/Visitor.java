package univ.annaba.Model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
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
	private List<VariableDeclarator> fields ;

	public Visitor() {
		body = new Hashtable<String, String>();
		
	}
	
	/**
	 * visits the Methods on the source file
	 */
	@Override
	public void visit(MethodDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if (n.getName()!=null) {
			methods += n.getName()+"." ;
			body.put(n.getName(), n.getBody().toString());
		}
	}
	
	/**
	 * visits the constructors on the source file
	 */
	@Override
	public void visit(ConstructorDeclaration n, Object arg) {
		// TODO Auto-generated method stub
		if(n.getName()!=null){
			constructors += n.getName()+"." ;
			body.put(n.getName(), n.getBlock().toString());
		}
	}
	
	@Override
	public void visit(FieldDeclaration n, Object arg) {
		fields = new ArrayList<VariableDeclarator>();
		if (n !=null) {
			fields.addAll(n.getVariables());
		}
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
	
	public List<VariableDeclarator> getFields() {
		return fields;
	}
	
	public void setFields(List<VariableDeclarator> fields) {
		this.fields = fields;
	}
	
}
