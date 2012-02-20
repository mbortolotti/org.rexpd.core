package com.rietveldextreme.optimization;

import com.rietveldextreme.optimization.OptimizationProblem;
import com.rietveldextreme.utils.Observer.Observable;



public abstract class OptimizationAlgorithm extends Observable {

	public enum Events {
		CYCLE_COMPLETED, 
		ITERATION_PERFORMED, 
		FITNESS_IMPROVED, 
		OPTIMIZATION_STARTED, 
		OPTIMIZATION_FINISHED
	}

	private int iterationsPerCycle = 20;
	private int stepsNumber = 5;
	private int currentStep = 0;
	private boolean paramLimitsEnabled = false;
	private boolean stopRequested = false;

	public abstract String getName();

	public abstract String getResults();

	public abstract int minimize(OptimizationProblem p);

	public int getStepsNumber() {
		return stepsNumber;
	}

	public void setStepsNumber(int steps) {
		stepsNumber = steps;
	}

	public int getCurrentStep() {
		return currentStep;
	}
	
	public void setCurrentStep(int step) {
		currentStep = step;
	}

	public int getIterationsPerStep() {
		return iterationsPerCycle;
	}

	public void setIterationsPerStep(int iterations) {
		iterationsPerCycle = iterations;
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
