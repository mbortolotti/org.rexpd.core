package org.rexpd.core.optimization;


import org.apache.commons.math3.analysis.DifferentiableMultivariateVectorFunction;
import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.rexpd.core.observer.MessageService;
import org.rexpd.core.optimization.OptimizationResult.EventType;

import static org.apache.commons.math3.util.FastMath.abs;

public class OptimizationAnalysisAdapter implements DifferentiableMultivariateVectorFunction {

	private OptimizationAnalysis optimizationAnalysis = null;
	private boolean centralDiffApproximation = false;
	private double eps = 1E-8;

	public OptimizationAnalysisAdapter(OptimizationAnalysis analysis) {
		optimizationAnalysis = analysis;
	}

	@Override
	public double[] value(double[] point) throws IllegalArgumentException {
		optimizationAnalysis.setParameterValues(point);
		double values[] = optimizationAnalysis.getCalculatedValues();
		MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.ITERATION_PERFORMED, "Iteration performed"));
		return values;
	}

	@Override
	public MultivariateMatrixFunction jacobian() {
		return new MultivariateMatrixFunction() {

			@Override
			public double[][] value(double[] point) throws IllegalArgumentException {
				if (centralDiffApproximation)
					return jacobianCentralDiffApprox(point);
				return jacobianForwardDiffApprox(point);
			}
		};
	}
	
	private double[][] jacobianForwardDiffApprox(double[] point) {
		int n_par = point.length;
		int n_obs = optimizationAnalysis.getTargets().length;
		optimizationAnalysis.setParameterValues(point);
		double[] y_p = optimizationAnalysis.getCalculatedValues();
		final double[][] jacobian = new double[n_obs][n_par];
		for (int np = 0; np < n_par; np++) {
			double temp = point[np];
			double step = eps * abs(temp);
			if (step == 0.0)
				step = eps;
			point[np] += step;
			optimizationAnalysis.setParameterValues(point);
			double[] y_p_h = optimizationAnalysis.getCalculatedValues();
			for (int nx = 0; nx < n_obs; nx++) {
				jacobian[nx][np] = (y_p_h[nx] - y_p[nx]) / step;
			}
			point[np] = temp;
		}
		return jacobian;
	}

	private double[][] jacobianCentralDiffApprox(double[] point) {
		int n_par = point.length;
		int n_obs = optimizationAnalysis.getTargets().length;
		final double[][] jacobian = new double[n_obs][n_par];
		for (int np = 0; np < n_par; np++) {
			double temp = point[np];
			double step = eps * abs(temp);
			if (step == 0.0)
				step = eps;
			point[np] -= step;
			optimizationAnalysis.setParameterValues(point);
			double[] y_m_h = optimizationAnalysis.getCalculatedValues();
			point[np] += step;
			optimizationAnalysis.setParameterValues(point);
			double[] y_p_h = optimizationAnalysis.getCalculatedValues();
			
			for (int nx = 0; nx < n_obs; nx++) {
				jacobian[nx][np] = (y_p_h[nx] - y_m_h[nx]) / 2 / step;
			}
			point[np] = temp;
		}
		return jacobian;
	}

}
