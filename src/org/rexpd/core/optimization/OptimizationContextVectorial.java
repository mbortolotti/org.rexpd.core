package org.rexpd.core.optimization;

@Deprecated
public interface OptimizationContextVectorial {
	
	public double[] getTargets();

	public double[] getWeights();
	
	public double[] getCalculatedValues();
	
	public double[] getParameterValues();

	public void setParameterValues(double[] values);

}
