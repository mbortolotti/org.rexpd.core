package com.rietveldextreme.optimization;


public abstract class RealFunctions {
	
	public static double[] getValues(RealFunction function, double x[]) {
		double[] values = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			values[i] = function.value(x[i]);
		}
		return values;
	}
	
	public static double[] getValues(RealFunction function, double x_min, double x_max, int n_steps) {
		double x_step = (x_max - x_min) / n_steps;
		double x[] = new double[n_steps];
		for (int i = 0; i < n_steps; i++) {
			x[i] = x_min + i * x_step;
		}
		return getValues(function, x);
	}
	
	public static double[] getValues(RealFunction function, double x_min, double x_max, double x_step) {
		int n_steps = (int) ((x_max - x_min) / x_step);
		double x[] = new double[n_steps];
		for (int i = 0; i < n_steps; i++) {
			x[i] = x_min + i * x_step;
		}
		return getValues(function, x);
	}

}
