package org.rexpd.core.optimization;


import static org.apache.commons.math3.util.FastMath.abs;

import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.fitting.leastsquares.MultivariateJacobianFunction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;
import org.rexpd.core.optimization.OptimizationResult.EventType;

public class OptimizationContextAdapter implements MultivariateJacobianFunction {

	private OptimizationContext optimizationContext = null;
	private boolean centralDiffApproximation = false;
	private double eps = 1E-8;
	private int evaluations = 0; 

	public OptimizationContextAdapter(OptimizationContext optimization) {
		optimizationContext = optimization;
	}

	@Override
	public Pair<RealVector, RealMatrix> value(RealVector point) {
		double pointArray[] = point.toArray();		
		double[] value = getModelFunction().value(pointArray);
		double[][] jacobian = getModelFunctionJacobian().value(pointArray);
		return new Pair<RealVector, RealMatrix>(new ArrayRealVector(value), new Array2DRowRealMatrix(jacobian));
	}
	
	public MultivariateVectorFunction getModelFunction() {
		return new MultivariateVectorFunction() {
			@Override
			public double[] value(double[] point) throws IllegalArgumentException {
				optimizationContext.setParameterValues(point);
				double values[] = optimizationContext.getCalculatedValues();
				optimizationContext.updateContext(new OptimizationResult(EventType.ITERATION_PERFORMED, "Iteration " + ++evaluations + "... "));
				return values;
			}
		};
	}

	public MultivariateMatrixFunction getModelFunctionJacobian() {
		return new MultivariateMatrixFunction() {
			@Override
			public double[][] value(double[] point) {
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
