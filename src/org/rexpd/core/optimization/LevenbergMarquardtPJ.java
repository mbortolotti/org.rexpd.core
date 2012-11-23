package org.rexpd.core.optimization;

import java.util.List;

import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.apache.commons.math3.optimization.general.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.Precision;
import org.rexpd.core.observer.MessageService;
import org.rexpd.core.optimization.OptimizationResult.EventType;


public class LevenbergMarquardtPJ extends OptimizationAlgorithm {

	public static final String LEVENBERG_MARQUARDT = "Levenberg Marquardt";
	public static final int DEFAULT_ITERATIONS = 25;

	private StoppableVectorValueChecker valueChecker = null;
	
	private double stepBoundFactor = 100;			/** Positive input variable used in determining the initial step bound */
	private double costTolerance = 1E-4;			/** Desired relative error in the sum of squares. */
	private double parTolerance = 1E-6;				/** Desired relative error in the approximate solution parameters. */
	private double orthoTolerance = 1E-4;			/** Desired max orthogonality between the function vector and the columns of the jacobian. */
	private double threshold = Precision.SAFE_MIN;	/** Desired threshold for QR ranking */

	public LevenbergMarquardtPJ() {
		setIterationsPerStep(DEFAULT_ITERATIONS);
	}

	@Override
	public String getName() {
		return LEVENBERG_MARQUARDT;
	}

	@Override
	public OptimizationResult minimize(OptimizationAnalysis problem) {

		double[] params = problem.getParameterValues();
		double[] data = problem.getTargets();
		if (params == null || data == null)
			return null;

		OptimizationResult results = new OptimizationResult();
		
		try {
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_STARTED, "Starting " + getName() + "..."));
			
			List<Parameter> parameters = Optimizations.getOptimizableParameters(problem);
			
			if (parameters.size() < 1) {
				MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "No optimizable parameters selected!"));
				return results;
			}
			
			valueChecker = new StoppableVectorValueChecker();
			LevenbergMarquardtOptimizer optimizer = new LevenbergMarquardtOptimizer(
					stepBoundFactor,
					valueChecker,
					costTolerance, 
					parTolerance, 
					orthoTolerance,
					threshold);
			int maxEval = getIterationsPerStep();

			optimizer.optimize(maxEval, new OptimizationAnalysisAdapter(problem), problem.getTargets(), problem.getWeights(), problem.getParameterValues());
			
			double[] errors = optimizer.guessParametersErrors();
			for (int np = 0; np < parameters.size(); np++) {
				parameters.get(np).setError(errors[np]);
			}
			results.setParameters(parameters);
			results.setChiSquare(optimizer.getChiSquare());
			results.setType(EventType.OPTIMIZATION_FINISHED);
			results.setMessage("Optimization finished!");
			
			MessageService.getInstance().notifyObservers(results);
			
		} catch (ConvergenceException e) {
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "Unable to converge!"));
		} catch (SingularMatrixException e) {
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "Unable to converge!\n(Singular matrix encountered)"));
		} catch (TooManyEvaluationsException e) {
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "Unable to converge!\n(Maximum evaluation number reached)"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public void setStopRequested(boolean stop) {
		valueChecker.setStopRequested(stop);
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
