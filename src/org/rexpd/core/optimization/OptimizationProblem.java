package org.rexpd.core.optimization;

import org.rexpd.core.observer.Observable;


public abstract class OptimizationProblem extends Observable {

	private FitnessFunction function = null;
	private OptimizationAlgorithm method = null;
	private OptimizationResults results = null;
	private boolean running = false;

	public OptimizationProblem (FitnessFunction f, OptimizationAlgorithm om) {
		function = f;
		method = om;
	}

	public OptimizationAlgorithm getOptimizationMethod() {
		return method;
	}

	public void setOptimizationMethod(OptimizationAlgorithm om) {
		method = om;
	}

	public FitnessFunction getFitnessFunction() {
		return function;
	}

	public void setFitnessFunction (FitnessFunction function){
		this.function = function;
	}

	public OptimizationResults getOptimizationResults() {
		return results;
	}

	public void solve() {
		running = true;
		//getFitnessFunction().startLog();
		results = getOptimizationMethod().minimize(this);
		//getFitnessFunction().stopLog();
		running = false;
	}

	public boolean isRunning() {
		return running;
	}

}
