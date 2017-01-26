package org.rexpd.core.math;

import static org.apache.commons.math3.util.FastMath.abs;
import static org.apache.commons.math3.util.FastMath.PI;
import static org.apache.commons.math3.util.FastMath.sqrt;


public class LorentzFunction {

	/** normalization factor so that (integral from -infinity to +infinity) = 1 **/
	private static final double CL = 4;
	
	public static double[] getValues(double[] x, double x_m, double FWHM) {
		double[] values = new double[x.length];
    	if (abs(FWHM) <= Double.MIN_VALUE)
    		return values;
    	double KL = sqrt(CL) / FWHM / PI; 
    	for (int n = 0; n < x.length; n++) {
    		double x_n = (x[n] - x_m) / FWHM;
    		values[n] = KL / ( 1 + CL * x_n * x_n);
    	}
		return values;
	}
	
}
