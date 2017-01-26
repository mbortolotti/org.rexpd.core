/*
 * Created on Jan 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.rexpd.core.math;

import static org.apache.commons.math3.util.FastMath.sqrt;
import static org.apache.commons.math3.util.FastMath.exp;
import static org.apache.commons.math3.util.FastMath.abs;
import static org.apache.commons.math3.util.FastMath.log;
import static org.apache.commons.math3.util.FastMath.PI;



public class GaussFunction {
	

	/** normalization factor so that (integral from -infinity to +infinity) = 1 **/
	private static final double CG = 4 * log(2.0);

	public static double[] getValues(double[] x, double x_m, double FWHM) {
    	double[] values = new double[x.length];
    	if (abs(FWHM) <= Double.MIN_VALUE)
    		return values;
    	for (int n = 0; n < x.length; n++) {
    		double x_n = (x[n] - x_m) / FWHM;
    		values[n] = sqrt(CG / PI) / FWHM * exp(-CG * x_n * x_n);
    	}
		return values;
	}
	

}
