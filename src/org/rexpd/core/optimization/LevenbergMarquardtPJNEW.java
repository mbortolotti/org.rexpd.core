package org.rexpd.core.optimization;

import java.util.List;

import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.exception.TooManyIterationsException;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer.Optimum;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.apache.commons.math3.util.Precision;
import org.rexpd.core.optimization.OptimizationResult.EventType;


public class LevenbergMarquardtPJNEW extends OptimizationAlgorithm {

	public static final String LEVENBERG_MARQUARDT = "Levenberg Marquardt";
	public static final int DEFAULT_EVALUATIONS = 1000;
	public static final int DEFAULT_ITERATIONS = 25;

	private double stepBoundFactor = 100;			/** Positive input variable used in determining the initial step bound */
	private double costTolerance = 1E-4;			/** Desired relative error in the sum of squares. */
	private double parTolerance = 1E-6;				/** Desired relative error in the approximate solution parameters. */
	private double orthoTolerance = 1E-4;			/** Desired max orthogonality between the function vector and the columns of the jacobian. */
	private double threshold = Precision.SAFE_MIN;	/** Desired threshold for QR ranking */

	private LeastSquaresBuilder builder = null;

	public LevenbergMarquardtPJNEW() {
	}

	@Override
	public String getName() {
		return LEVENBERG_MARQUARDT;
	}

	@Override
	public OptimizationResult minimize(OptimizationContext context) {

		double[] params = context.getParameterValues();
		double[] data = context.getTargets();
		if (params == null || data == null)
			return null;

		setIterations(DEFAULT_ITERATIONS);
		OptimizationResult results = new OptimizationResult();

		try {
			context.updateContext(new OptimizationResult(EventType.OPTIMIZATION_STARTED, "Starting " + getName() + "..."));

			List<Parameter> parameters = Optimizations.getOptimizableParameterList(context);

			if (parameters.size() < 1) {
				context.updateContext(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "No optimizable parameters selected!"));
				return results;
			}

			LevenbergMarquardtOptimizer optimizer = new LevenbergMarquardtOptimizer(
					stepBoundFactor,
					costTolerance, 
					parTolerance, 
					orthoTolerance,
					threshold);
			
			builder = new LeastSquaresBuilder()
			.checkerPair(new StoppableVectorValueChecker(context))
			.maxEvaluations(DEFAULT_EVALUATIONS)
			.maxIterations(DEFAULT_ITERATIONS);

			LeastSquaresProblem problem = builder
					.maxIterations(getIterations())
					.model(new OptimizationContextAdapter(context))
					.target(context.getTargets())
					.weight(new DiagonalMatrix(context.getWeights()))
					.start(context.getParameterValues()).build();

			Optimum leastSquaresResult = optimizer.optimize(problem);

			RealVector errors = leastSquaresResult.getSigma(threshold);
			for (int np = 0; np < parameters.size(); np++) {
				parameters.get(np).setError(errors.getEntry(np));
			}
			
			results.setParameters(parameters);
			results.setFitness(leastSquaresResult.getCost());
			results.setType(EventType.OPTIMIZATION_FINISHED);
			results.setMessage("Optimization finished!");

			context.updateContext(results);

		} catch (ConvergenceException e) {
			context.updateContext(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "Unable to converge!"));
		} catch (SingularMatrixException e) {
			context.updateContext(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "Unable to converge!\n(Singular matrix encountered)"));
		} catch (TooManyEvaluationsException e) {
			context.updateContext(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "Unable to converge!\n(Maximum evaluation number reached)"));
		} catch (TooManyIterationsException e) {
			context.updateContext(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "Unable to converge!\n(Maximum iteration count exceeded)"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

	public double getCostRelativeTolerance() {
		return costTolerance;
	}

	public void setCostRelativeTolerance(double crt) {
		costTolerance = crt;
	}

	public double getParRelativeTolerance() {
		return parTolerance;
	}

	public void setParRelativeTolerance(double prt) {
		parTolerance = prt;
	}

	public double getOrthoTolerance() {
		return orthoTolerance;
	}

	public void setOrthoTolerance(double ot) {
		orthoTolerance = ot;
	}


}
