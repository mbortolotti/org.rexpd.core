package org.rexpd.core.optimization;


public abstract class OptimizationAlgorithm {

	private int iterations = 10;
	private int currentIteration = 0;
	private boolean paramLimitsEnabled = false;
	private boolean stopRequested = false;

	public abstract String getName();

	public abstract OptimizationResult minimize(OptimizationAnalysis p);

	public int getCurrentIteration() {
		return currentIteration;
	}
	
	public void setCurrentIteration(int iteration) {
		currentIteration = iteration;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iter) {
		iterations = iter;
	}
	
	public void enableParamLimits(boolean enable) {
		paramLimitsEnabled = enable;
	}
	
	public boolean parameterLimitsEnabled() {
		return paramLimitsEnabled;
	}

	public void setStopRequested(boolean stop) {
		stopRequested = stop;
	}

	public boolean hasStopRequested() {
		return stopRequested;
	}

}
