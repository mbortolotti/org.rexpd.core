package org.rexpd.core.optimization;

public abstract class AbstractRealFunction implements DiscreteRealFunction {

	public double[] values(double x[]) {
		double[] values = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			values[i] = value(x[i]);
		}
		return values;
	}
	
}
