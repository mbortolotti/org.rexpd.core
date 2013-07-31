package org.rexpd.core.optimization.simulatedannealing;

public class AnnealingScheduleLinear extends AnnealingSchedule1 {

	@Override
	public double getTemperature(double t0, double tn, int i, int n) {
		return t0 - (t0 - tn) * i / n;
	}

}
