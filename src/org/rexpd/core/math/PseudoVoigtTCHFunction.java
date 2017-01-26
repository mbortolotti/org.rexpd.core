package org.rexpd.core.math;

import static org.apache.commons.math3.util.FastMath.pow;
import static org.apache.commons.math3.util.FastMath.abs;

public class PseudoVoigtTCHFunction {

	/** Max allowed FWHM value **/
	private static final double FWHM_MAX = 300;
	/** Max allowed FWHM^5 value corresponding to about 300° FWHM **/
	private static final double FWHM5_MAX = 2E12;

	private static final double A = 2.69269;
	private static final double B = 2.42843;
	private static final double C = 4.47163;
	private static final double D = 0.07842;

	private static final double E1 = 1.36603;
	private static final double E2 = -0.47719;
	private static final double E3 = 0.11116;

	public static double[] getValues(double[] x, double x_m, double fwhm_g, double fwhm_l) {
		double[] values = new double[x.length];
		double[] G = GaussFunction.getValues(x, x_m, fwhm_g);
		double[] L = LorentzFunction.getValues(x, x_m, fwhm_l);
		double fwhm = getFWHM(fwhm_g, fwhm_l);
		double q = fwhm_l / fwhm;
		double eta = E1 * q + E2 * q * q + E3 * q * q * q;
		for (int n = 0; n < x.length; n++) {
			values[n] = eta * L[n] + (1 - eta) * G[n];
		}
		return values;
	}

	public static double getFWHM(double fwhm_g, double fwhm_l) {
		if (fwhm_g > FWHM_MAX)
			fwhm_g = FWHM_MAX;
		if (fwhm_l > FWHM_MAX)
			fwhm_l = FWHM_MAX;
		double fwhm5 =  (pow(fwhm_g, 5.0) + A * pow(fwhm_g, 4.0) * fwhm_l + B * pow(fwhm_g, 3.0) * pow(fwhm_l, 2.0) + 
				C * pow(fwhm_g, 2.0) * pow(fwhm_l, 3.0) + D * fwhm_g * pow(fwhm_l, 4.0) + pow(fwhm_l, 5.0));
		if (fwhm5 > FWHM5_MAX)
			fwhm5 = FWHM5_MAX;
		return pow(abs(fwhm5), 0.2);
	}
}
