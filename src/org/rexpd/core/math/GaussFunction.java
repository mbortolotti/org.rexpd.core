/*
 * Created on Jan 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.rexpd.core.math;

import static org.apache.commons.math3.util.FastMath.sqrt;
import static org.apache.commons.math3.util.FastMath.exp;



public class GaussFunction {
	
	private static final double log2 = Math.log(2.0);
	
	public static double getValue(double x, double FWHM) {
		double C0 = sqrt(4 * log2 / Math.PI) / FWHM;
    	double C1 = 4 * log2 / FWHM / FWHM;
		return C0 * exp(-C1 * x * x);
	}
	
	public static double[] getValues(double[] x, double x_m, double FWHM) {
		double C0 = sqrt(4 * log2 / Math.PI) / FWHM;
    	double C1 = 4 * log2 / FWHM / FWHM;
    	double[] values = new double[x.length];
    	for (int n = 0; n < x.length; n++) {
    		double x_n = x[n] - x_m;
    		values[n] = C0 * exp(-C1 * x_n * x_n);
    	}
		return values;
	}
	

}
