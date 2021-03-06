package org.rexpd.core.optimization;


import static org.apache.commons.math3.util.FastMath.abs;

import org.apache.commons.math3.analysis.DifferentiableMultivariateVectorFunction;
import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.rexpd.core.optimization.OptimizationResult.EventType;

@Deprecated
public class OptimizationAnalysisAdapter implements DifferentiableMultivariateVectorFunction {

	private OptimizationContext optimizationContext = null;
	private boolean centralDiffApproximation = false;
	private double eps = 1E-8;
	private int evaluations = 0;

	public OptimizationAnalysisAdapter(OptimizationContext optimization) {
		optimizationContext = optimization;
	}

	@Override
	public double[] value(double[] point) throws IllegalArgumentException {
		optimizationContext.setParameterValues(point);
		double values[] = optimizationContext.getCalculatedValues();
		optimizationContext.updateContext(new OptimizationResult(EventType.ITERATION_PERFORMED, "Iteration " + ++evaluations + "... "));
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
		int n_obs = optimizationContext.getTargets().length;
		optimizationContext.setParameterValues(point);
		double[] y_p = optimizationContext.getCalculatedValues();
		final double[][] jacobian = new double[n_obs][n_par];
		for (int np = 0; np < n_par; np++) {
			double temp = point[np];
			double step = eps * abs(temp);
			if (step == 0.0)
				step = eps;
			point[np] += step;
			optimizationContext.setParameterValues(point);
			double[] y_p_h = optimizationContext.getCalculatedValues();
			for (int nx = 0; nx < n_obs; nx++) {
				jacobian[nx][np] = (y_p_h[nx] - y_p[nx]) / step;
			}
			point[np] = temp;
		}
		return jacobian;
	}

	private double[][] jacobianCentralDiffApprox(double[] point) {
		int n_par = point.length;
		int n_obs = optimizationContext.getTargets().length;
		final double[][] jacobian = new double[n_obs][n_par];
		for (int np = 0; np < n_par; np++) {
			double temp = point[np];
			double step = eps * abs(temp);
			if (step == 0.0)
				step = eps;
			point[np] -= step;
			optimizationContext.setParameterValues(point);
			double[] y_m_h = optimizationContext.getCalculatedValues();
			point[np] += step;
			optimizationContext.setParameterValues(point);
			double[] y_p_h = optimizationContext.getCalculatedValues();
			
			for (int nx = 0; nx < n_obs; nx++) {
				jacobian[nx][np] = (y_p_h[nx] - y_m_h[nx]) / 2 / step;
			}
			point[np] = temp;
		}
		return jacobian;
	}

}
