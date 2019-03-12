package univ.annaba.Control;

public class SoftwareMetrics{
	private double VG; //McCabe Cyclomatic Complexity
	private double PAR; //Number of Parameters
	private double NBD; //Nested Block Depth
	private double DIT; //Depth of Inheritance Tree
	private double WMC; //Weighted methods per Class
	private double NSC; //Number of Children
	private double LCOM; //Lack of Cohesion of Methods
	private int NOF; //Number of Attributes"
	private int NSF; //Number of Static Attributes
	private int NOM; //Number of Methods
	private int NSM; //Number of Static Methods
	private int NOC; //Number of Classes
	private int NOI; //Number of Interfaces
	private int NOP; //Number of Packages
	private int TLOC; //Total Lines of Code
	private int MLOC; //Method Lines of Code
	
	/**
	 * @return the vG
	 */
	public double getVG() {
		return VG;
	}
	/**
	 * @param vG the vG to set
	 */
	public void setVG(double vG) {
		VG = vG;
	}
	/**
	 * @return the pAR
	 */
	public double getPAR() {
		return PAR;
	}
	/**
	 * @param pAR the pAR to set
	 */
	public void setPAR(double pAR) {
		PAR = pAR;
	}
	/**
	 * @return the nBD
	 */
	public double getNBD() {
		return NBD;
	}
	/**
	 * @param nBD the nBD to set
	 */
	public void setNBD(double nBD) {
		NBD = nBD;
	}
	/**
	 * @return the dIT
	 */
	public double getDIT() {
		return DIT;
	}
	/**
	 * @param dIT the dIT to set
	 */
	public void setDIT(double dIT) {
		DIT = dIT;
	}
	/**
	 * @return the wMC
	 */
	public double getWMC() {
		return WMC;
	}
	/**
	 * @param wMC the wMC to set
	 */
	public void setWMC(double wMC) {
		WMC = wMC;
	}
	/**
	 * @return the nSC
	 */
	public double getNSC() {
		return NSC;
	}
	/**
	 * @param nSC the nSC to set
	 */
	public void setNSC(double nSC) {
		NSC = nSC;
	}
	/**
	 * @return the lCOM
	 */
	public double getLCOM() {
		return LCOM;
	}
	/**
	 * @param lCOM the lCOM to set
	 */
	public void setLCOM(double lCOM) {
		LCOM = lCOM;
	}
	/**
	 * @return the nOF
	 */
	public int getNOF() {
		return NOF;
	}
	/**
	 * @param nOF the nOF to set
	 */
	public void setNOF(int nOF) {
		NOF = nOF;
	}
	/**
	 * @return the nSF
	 */
	public int getNSF() {
		return NSF;
	}
	/**
	 * @param nSF the nSF to set
	 */
	public void setNSF(int nSF) {
		NSF = nSF;
	}
	/**
	 * @return the nOM
	 */
	public int getNOM() {
		return NOM;
	}
	/**
	 * @param nOM the nOM to set
	 */
	public void setNOM(int nOM) {
		NOM = nOM;
	}
	/**
	 * @return the nSM
	 */
	public int getNSM() {
		return NSM;
	}
	/**
	 * @param nSM the nSM to set
	 */
	public void setNSM(int nSM) {
		NSM = nSM;
	}
	/**
	 * @return the nOC
	 */
	public int getNOC() {
		return NOC;
	}
	/**
	 * @param nOC the nOC to set
	 */
	public void setNOC(int nOC) {
		NOC = nOC;
	}
	/**
	 * @return the nOI
	 */
	public int getNOI() {
		return NOI;
	}
	/**
	 * @param nOI the nOI to set
	 */
	public void setNOI(int nOI) {
		NOI = nOI;
	}
	/**
	 * @return the nOP
	 */
	public int getNOP() {
		return NOP;
	}
	/**
	 * @param nOP the nOP to set
	 */
	public void setNOP(int nOP) {
		NOP = nOP;
	}
	/**
	 * @return the tLOC
	 */
	public int getTLOC() {
		return TLOC;
	}
	/**
	 * @param tLOC the tLOC to set
	 */
	public void setTLOC(int tLOC) {
		TLOC = tLOC;
	}
	/**
	 * @return the mLOC
	 */
	public int getMLOC() {
		return MLOC;
	}
	/**
	 * @param mLOC the mLOC to set
	 */
	public void setMLOC(int mLOC) {
		MLOC = mLOC;
	}
	
	@Override
	public String toString() {
		return "SoftwareMetric:: VG:"+ VG+ "MLOC: "+MLOC+"NOF: "+NOF+"NOM: "+NOM;
	}
}