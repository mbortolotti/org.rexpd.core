package com.rietveldextreme.optimization;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MaxEvaluationsExceededException;
import org.apache.commons.math.MaxIterationsExceededException;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;

import com.rietveldextreme.algorithms.LevenbergMarquardt;


public class LevenbergMarquardtPJ extends LevenbergMarquardt {

	public static final String LMPUREJAVA = "LMPureJava";
	
	String optResults = null;
	
	@Override
	public String getName() {
		return LMPUREJAVA;
	}

	@Override
	public String getResults() {
		return optResults;
	}

	@Override
	public int minimize(OptimizationProblem problem) {
		optResults = null;
		int iter = getIterationsPerStep();
		setCurrentStep(0);
		FitnessFunction function = problem.getFitnessFunction();
		
		LevenbergMarquardtOptimizer optimizer = new LevenbergMarquardtOptimizer();
		//optimizer.setMaxEvaluations(10);
		optimizer.setCostRelativeTolerance(1E-3);
		//optimizer.setParRelativeTolerance(1E-8);
		optimizer.setOrthoTolerance(1E-3);
		
		
		double[] params = function.getParameterValues();
		double[] data = function.getTargets();
		if (params == null || data == null)
			return -1;
		
		
		
		double[] covar = new double[params.length * params.length];
		
		notify(Events.OPTIMIZATION_STARTED);
		while (getCurrentStep() < getStepsNumber()) {
			if (hasStopRequested()) {
				notify(Events.OPTIMIZATION_FINISHED);
				setStopRequested(false);
				return iter;
			}
			try {
				optimizer.optimize(new FitnessFunctionAdapter(function), function.getTargets(), function.getWeights(), function.getParameterValues());
				System.out.println("optimizer.getEvaluations(): " + optimizer.getEvaluations());
				System.out.println("optimizer.getIterations(): " + optimizer.getIterations());
				System.out.println("optimizer.getChiSquare(): " + optimizer.getChiSquare());
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
			/**params = function.getParameterValues();
			iter = ReXNative.LMminimize(params, data, covar, getIterationsPerStep(), function);
			//ReXNative.LevenbergMarquardt(parameters, measurements.length, getIterationsPerCycle(), function);**/
					}
		/**RealMatrix covariance = MatrixUtils.createRealMatrix(params.length, params.length);
		for (int i = 0; i < params.length; i++) {
			for (int j = 0; j < params.length; j++) {
				covariance.setEntry(i, j, covar[i * params.length + j]);
			}
		}
		List<Parameter> parameters = function.getParameters();
		String newline = System.getProperty("line.separator");
		optResults = "Levenberg-Marquardt optimization" + newline + newline;
		optResults = optResults + "Refined parameters:" + newline;
		NumberFormat.getInstance().setMaximumFractionDigits(5);
		for (int np = 0; np < parameters.size(); np++) {
			String value = NumberFormat.getInstance().format(parameters.get(np).getValue());
			String sdev = NumberFormat.getInstance().format(Math.sqrt(covariance.getEntry(np, np)));
			optResults = optResults + parameters.get(np).getLabel() + " = " + value + " (\u03C3 = " + sdev + ")" + newline;
		}**/
		notify(Events.OPTIMIZATION_FINISHED);
		return iter;
	}


}
