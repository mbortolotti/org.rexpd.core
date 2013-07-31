package org.rexpd.core.optimization.simulatedannealing;

public class AnnealingScheduleExponential extends AnnealingSchedule1 {
	
	private double alpha = 0.5;

	@Override
	public double getTemperature(double t0, double tn, int i, int n) {
		return Math.pow(alpha, i) * t0;
	}

}
