package org.rexpd.core.math;


public class PseudoVoigtFunction {

	public static double[] getValues(double[] x, double x_m, double FWHM, double eta) {
		double[] values = new double[x.length];
		double[] G = GaussFunction.getValues(x, x_m, FWHM);
		double[] L = LorentzFunction.getValues(x, x_m, FWHM);
		for (int n = 0; n < x.length; n++) {
			values[n] = (1 - eta) * G[n] + eta * L[n];
		}
		return values;
	}

}
