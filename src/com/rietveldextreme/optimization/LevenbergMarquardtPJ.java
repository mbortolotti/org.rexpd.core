package com.rietveldextreme.optimization;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MaxEvaluationsExceededException;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;


public class LevenbergMarquardtPJ extends OptimizationAlgorithm {

	public static final String LMPUREJAVA = "Levenberg Marquardt";

	private LevenbergMarquardtOptimizer optimizer = null;


	private double costTolerance = 1E-4;	/** Desired relative error in the sum of squares. */
	private double parTolerance = 1E-6;		/** Desired relative error in the approximate solution parameters. */
	private double orthoTolerance = 1E-4;	/** Desired max orthogonality between the function vector and the columns of the jacobian. */

	private OptimizationResults results = null;

	public LevenbergMarquardtPJ() {

		optimizer = new LevenbergMarquardtOptimizer();

	}

	@Override
	public String getName() {
		return LMPUREJAVA;
	}

	@Override
	public OptimizationResults minimize(OptimizationProblem problem) {
		int iter = getIterationsPerStep();
		setCurrentStep(0);
		FitnessFunction function = problem.getFitnessFunction();

		optimizer.setMaxEvaluations(getIterationsPerStep());
		optimizer.setCostRelativeTolerance(costTolerance);
		optimizer.setParRelativeTolerance(parTolerance);
		optimizer.setOrthoTolerance(orthoTolerance);


		double[] params = function.getParameterValues();
		double[] data = function.getTargets();
		if (params == null || data == null)
			return null;

		notify(Events.OPTIMIZATION_STARTED);

		OptimizationResults results = new OptimizationResults();
		String newline = System.getProperty("line.separator");
		String log = "Levenberg-Marquardt optimization started..." + newline;
		DecimalFormat dfChiSquare = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US));

		while (getCurrentStep() < getStepsNumber()) {
			if (hasStopRequested()) {
				notify(Events.OPTIMIZATION_FINISHED);
				setStopRequested(false);
				log = log + "Optimization interrupted by user!" + newline;
				results.setResultsLog(log);
				return results;
			}
			try {
				optimizer.optimize(new FitnessFunctionAdapter(function), function.getTargets(), function.getWeights(), function.getParameterValues());
				List<Parameter> parameters = function.getParameters();
				double[] errors = optimizer.guessParametersErrors();
				for (int np = 0; np < parameters.size(); np++) {
					parameters.get(np).setError(errors[np]);
				}
				results.setParameters(parameters);
				results.setChiSquare(optimizer.getChiSquare());
				log = log + "iteration " + (getCurrentStep() + 1) +  " - Chi square: " 
						+ dfChiSquare.format(optimizer.getChiSquare()) + newline;
				setCurrentStep(getCurrentStep() + 1);
				notify(Events.CYCLE_COMPLETED);
			} catch (OptimizationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FunctionEvaluationException e) {
				if (e.getCause() instanceof MaxEvaluationsExceededException)
					notify(Events.CYCLE_COMPLETED);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		notify(Events.OPTIMIZATION_FINISHED);
		log = log + "Optimization finished!" + newline;
		results.setResultsLog(log);
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
