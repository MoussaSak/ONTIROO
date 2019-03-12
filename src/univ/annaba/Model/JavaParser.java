package univ.annaba.Model;

import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.VariableDeclaratorId;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * the {@code JavaParser} class handles the java file  source it makes a static 
 * analysis for the code and retrieves constructors and methods from that it. 
 * @author Moussa
 * @see {@link Visitor}
 */
public class JavaParser extends Visitor {
	private String path;
	private FileInputStream fileInputStream;
	private CompilationUnit compilationUnit;

	
	public JavaParser(String path) {
		this.path = path;
		init();
	}
	
	/**
	 * it initials the file to be treaded 
	 * @exception {@link FileNotFoundException}
	 * @exception {@link ParseException}
	 * @exception {@link IOException}
	 */
	public void init(){
		
		try {
			fileInputStream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	    try {
            try {
				compilationUnit = japa.parser.JavaParser.parse(fileInputStream);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    	} finally {
            try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	    visit(compilationUnit,null);
	}
	
	/**
	 * this method retrieves constructors and methods from the java file
	 * @return an array contains constructors and methods. 
	 */
	public String[] parseMethods() {
        	visit(compilationUnit,null);
        	String methods = getConstructors()+getMethods();
        	String[] str = methods.split("\\.");
        	
        return str;
    }
	
	public  List<VariableDeclaratorId> parseFields() {
		visit(compilationUnit, null);
		List<VariableDeclaratorId> fields = getFields();
		return fields;
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		setNOL(n.getEndLine());
		super.visit(n, arg);
	}
	
	public static void main(String[] args) {
		JavaParser parser= new JavaParser("/home/moise/Documents/example/Application.java");
		System.out.println(parser.parseMethods());
		System.out.println(parser.getNOF());
		
		}
}
