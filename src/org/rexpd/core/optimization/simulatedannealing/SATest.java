package org.rexpd.core.optimization.simulatedannealing;

import org.rexpd.core.optimization.OptimizationContext;
import org.rexpd.core.optimization.Optimizations;

public class SATest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int steps = 100;
		double t0 = 100;
		double tn = 1;
		
		AnnealingScheduleLinear linear = new AnnealingScheduleLinear();
		AnnealingScheduleGeometric geometric = new AnnealingScheduleGeometric();
		AnnealingScheduleExponential exponential = new AnnealingScheduleExponential();
		
		linear.initSchedule(t0, tn, steps);
		
		for (int i = 0; i < steps; i++) {
			
			double t_linear = linear.getTemperature(t0, tn, i, steps);
			double t_geom = geometric.getTemperature(t0, tn, i, steps);
			double t_exp = exponential.getTemperature(t0, tn, i, steps);
			
			System.out.println(i + "\t" + t_linear + "\t" + t_geom + "\t" + t_exp);
		}
		
		OptimizationContext problem = new TestProblemBeale();
		Optimizations.setOptimizable(problem, true);
		
		SimulatedAnnealing sa = new SimulatedAnnealing();
		sa.minimize(problem);

	}

}
