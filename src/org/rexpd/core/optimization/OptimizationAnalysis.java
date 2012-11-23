package org.rexpd.core.optimization;

import org.rexpd.core.base.Analysis;


public abstract class OptimizationAnalysis extends Analysis {

	private OptimizationAlgorithm method = null;
	private OptimizationResult results = null;

	public OptimizationAnalysis () {
		method = new LevenbergMarquardtPJ();
	}
	
	public abstract double[] getTargets();

	public abstract double[] getWeights();
	
	public abstract double[] getCalculatedValues();

	@Override
	public void run() {
		setRunning(true);
		results = getOptimizationAlgorithm().minimize(this);
		setRunning(false);
	}

	public OptimizationAlgorithm getOptimizationAlgorithm() {
		return method;
	}

	public void setOptimizationMethod(OptimizationAlgorithm om) {
		method = om;
	}

	public OptimizationResult getOptimizationResults() {
		return results;
	}

}
