package org.rexpd.core.optimization.simulatedannealing;

public class AnnealingScheduleGeometric extends AnnealingSchedule1 {

	@Override
	public double getTemperature(double t0, double tn, int i, int n) {
		return (t0 * Math.pow(tn / t0, (double) i / n));
	}

}
