package org.rexpd.core.optimization;

import java.util.List;

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

	public double[] getParameterValues() {
		List<Parameter> parameters = Optimizations.getOptimizableParameters(this);
		double[] values = new double[parameters.size()];
		for (int np = 0; np < parameters.size(); np++)
			values[np] = parameters.get(np).getValue();
		return values;
	}

	public void setParameterValues(double[] values) {
		List<Parameter> parameters = Optimizations.getOptimizableParameters(this);
		for (int np = 0; np < parameters.size(); np++)
			parameters.get(np).setValue(values[np]);
	}
	
	public void setSolution(Solution solution) {
		setParameterValues(solution.getParameterValues());
		update();
	}

}
