/*
 * Created on Feb 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rietveldextreme.utils;

/**
 * @author mauro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CCTBXNative {

	public static final int MILLER_IDX_SIZE = 6;
	public static final int SF_DATA_SIZE = 6;
	
	static {
		ReXNative.loadRexLibrary();
	}

	public static native double[] computeStructureFactors(double a, double b, double c, double alpha, double beta, double gamma,
			String HallSymbol, double d_spacing_min, String ScFactorsTable,
			double[] ASUcoordinates, 
			double[] ASUoccupancies, 
			double[] ASUisobfactors, 
			String[] ASUatomLabels);
	
	public static native double[] computeStructureFactorsAnisoBfactors(double a, double b, double c, double alpha, double beta, double gamma,
			String HallSymbol, double d_spacing_min, String ScatFactorsTable,
			double[] ASUcoordinates, 
			double[] ASUoccupancies, 
			double[] ASUanisobfactors,
			String[] ASUatomLabels);
	
	/**
	 * @param a
	 * @param b
	 * @param c
	 * @param alpha
	 * @param beta
	 * @param gamma
	 * @param SpaceGroupHM
	 * @param xcoord
	 * @param ycoord
	 * @param zcoord
	 * @return
	 */
	public static native double[] computeEquivalentPositions(double a, double b, double c, double alpha, double beta, double gamma,
			String HallSymbol, double xcoord, double ycoord, double zcoord);
	
	/**
	 * @param a
	 * @param b
	 * @param c
	 * @param alpha
	 * @param beta
	 * @param gamma
	 * @param HallSymbol
	 * @param d_spacing_min
	 * @return
	 */
	public static native int[] generateMillerIndices(double a, double b, double c, double alpha, double beta, double gamma, 
	        String HallSymbol, double d_spacing_min);
	
	/**
	 * Lookup space group symbol
	 * 
	 * @param space_group_symbol generic space group symbol
	 * @return Hall symbol
	 */
	public static native String sgSymbol2Hall(String space_group_symbol);
	
	/**
	 * Lookup space group symbol
	 * 
	 * @param ITNumber generic space group symbol
	 * @param extension extension to the Hermann-Mauguin symbol (optional)
	 * @return Hall symbol
	 */
	public static native String itNumber2Hall(int ITNumber, String extension);
	
	/**
	 * @param HallSymbol
	 * @return
	 */
	public static native String[] getSymmetryOperators(String HallSymbol);
	
	/**
	 * @param HallSymbol
	 * @return
	 */
	public static native int getCrystalSystem(String HallSymbol);
	

}
