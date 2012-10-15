package org.rexpd.core.math;


public class LorentzFunction {
	
	public static double getValue(double x, double FWHM) {
		double C0 = 2 / (Math.PI * FWHM);
		double C1 = 4 / FWHM / FWHM;
		return C0 / ( 1 + C1 * x * x);
	}
	
	public static double[] getValues(double[] x, double x_m, double FWHM) {
		double C0 = 2 / (Math.PI * FWHM);
		double C1 = 4 / FWHM / FWHM;
		double[] values = new double[x.length];
    	for (int n = 0; n < x.length; n++) {
    		double x_n = x[n] - x_m;
    		values[n] = C0 / ( 1 + C1 * x_n * x_n);
    	}
		return values;
	}
	
}
