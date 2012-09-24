package org.rexpd.core.optimization;

public interface FitnessContextVectorial {
	
	public double[] getTargets();

	public double[] getWeights();
	
	public double[] getCalculatedValues(double[] point);
	
	public double[] getParameterValues();

}