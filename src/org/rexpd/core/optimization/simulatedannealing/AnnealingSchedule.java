package org.rexpd.core.optimization.simulatedannealing;

public abstract class AnnealingSchedule {
	
	private static final double DEFAULT_TSTART = 1;
	private static final double DEFAULT_TEND = 1E-16;
	
	
	private static int currentStep = 0;
	private static double[] stepValues = null;
	
	public abstract double getTemperature(double t0, double tn, int i, int n);
	
	public boolean hasNext() {
		return (stepValues != null && currentStep < stepValues.length);
	}

	public double nextValue() {
		return stepValues[currentStep++];
	}

	public void initSchedule(double tStart, double tEnd, int nSteps) {
		if (tStart <= DEFAULT_TSTART)
			tStart = DEFAULT_TSTART;
		if (tEnd <= DEFAULT_TEND)
			tEnd = DEFAULT_TEND;
		currentStep = 0;
		stepValues = new double[nSteps];
		for (int i = 0; i < nSteps; i++)
			stepValues[i] = getTemperature(tStart, tEnd, i, nSteps);
	}

}
