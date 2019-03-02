package univ.annaba.Control;

import java.util.Hashtable;
import java.util.List;

import japa.parser.ast.body.Parameter;

public class MetricsReport {
	Hashtable <String, Integer> mloc;
	int LOC;
	int NOF;
	private Hashtable<String,List<Parameter> > parameters;
	
	public MetricsReport() {
		mloc = new Hashtable<String, Integer>();
		parameters = new Hashtable<String, List<Parameter>>();
		setLOC(0);
		setNOF(0);
	}
	
	public Hashtable<String, Integer> getMloc() {
		return mloc;
	}
	
	public void setMloc(Hashtable<String, Integer> mloc) {
		this.mloc = mloc;
	}

	public int getLOC() {
		return LOC;
	}

	public void setLOC(int lOC) {
		LOC = lOC;
	}

	public int getNOF() {
		return NOF;
	}

	public void setNOF(int nOF) {
		NOF = nOF;
	}

	public Hashtable<String, List<Parameter>> getParameters() {
		return parameters;
	}

	public void setParameters(Hashtable<String, List<Parameter>> parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	
	
}
