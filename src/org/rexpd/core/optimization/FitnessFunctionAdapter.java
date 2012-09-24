package org.rexpd.core.optimization;


import org.apache.commons.math3.analysis.DifferentiableMultivariateVectorFunction;
import org.apache.commons.math3.analysis.MultivariateMatrixFunction;

import static org.apache.commons.math3.util.FastMath.abs;

public class FitnessFunctionAdapter implements DifferentiableMultivariateVectorFunction {

	private FitnessContextVectorial fitnessContext = null;
	private boolean centralDiffApproximation = false;
	private double eps = 1E-8;

	public FitnessFunctionAdapter(FitnessContextVectorial context) {
		fitnessContext = context;
	}

	@Override
	public double[] value(double[] point) throws IllegalArgumentException {
		return fitnessContext.getCalculatedValues(point);
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
		int n_obs = fitnessContext.getTargets().length;
		double[] y_p = fitnessContext.getCalculatedValues(point);
		final double[][] jacobian = new double[n_obs][n_par];
		for (int np = 0; np < n_par; np++) {
			double temp = point[np];
			double step = eps * abs(temp);
			if (step == 0.0)
				step = eps;
			point[np] += step;
			double[] y_p_h = fitnessContext.getCalculatedValues(point);
			for (int nx = 0; nx < n_obs; nx++) {
				jacobian[nx][np] = (y_p_h[nx] - y_p[nx]) / step;
			}
			point[np] = temp;
		}
		return jacobian;
	}

	private double[][] jacobianCentralDiffApprox(double[] point) {
		int n_par = point.length;
		int n_obs = fitnessContext.getTargets().length;
		final double[][] jacobian = new double[n_obs][n_par];
		for (int np = 0; np < n_par; np++) {
			double temp = point[np];
			double step = eps * abs(temp);
			if (step == 0.0)
				step = eps;
			point[np] -= step;
			double[] y_m_h = fitnessContext.getCalculatedValues(point);
			point[np] += step;
			double[] y_p_h = fitnessContext.getCalculatedValues(point);
			
			for (int nx = 0; nx < n_obs; nx++) {
				jacobian[nx][np] = (y_p_h[nx] - y_m_h[nx]) / 2 / step;
			}
			point[np] = temp;
		}
		return jacobian;
	}

}
