package com.rietveldextreme.algorithms;

import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

import com.rietveldextreme.optimization.FitnessFunction;
import com.rietveldextreme.optimization.OptimizationAlgorithm;
import com.rietveldextreme.optimization.OptimizationProblem;
import com.rietveldextreme.optimization.Parameter;
import com.rietveldextreme.utils.ReXNative;


public class LevenbergMarquardt extends OptimizationAlgorithm {

	public static final String LEVENBERG_MARQUARDT = "Levenberg Marquardt";
	
	String optResults = null;
	
	@Override
	public String getName() {
		return LEVENBERG_MARQUARDT;
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
		double[] params = function.getParameterValues();
		double[] data = function.getTargetValues();
		if (params == null || data == null)
			return -1;
		double[] covar = new double[params.length * params.length];
		//dispatchMessage(new Message(this, Events.OPTIMIZATION_STARTED.toString(), new Integer(0)));
		notify(Events.OPTIMIZATION_STARTED);
		while (getCurrentStep() < getStepsNumber()) {
			if (hasStopRequested()) {
				//dispatchMessage(new Message(this, Events.OPTIMIZATION_FINISHED.toString(), new Integer(iter)));
				notify(Events.OPTIMIZATION_FINISHED);
				setStopRequested(false);
				return iter;
			}
			params = function.getParameterValues();
			iter = ReXNative.LMminimize(params, data, covar, getIterationsPerStep(), function);
			//ReXNative.LevenbergMarquardt(parameters, measurements.length, getIterationsPerCycle(), function);
			setCurrentStep(getCurrentStep() + 1);
			//dispatchMessage(new Message(this, Events.CYCLE_COMPLETED.toString(), new Integer(getCurrentStep())));
			notify(Events.CYCLE_COMPLETED);
		}
		RealMatrix covariance = MatrixUtils.createRealMatrix(params.length, params.length);
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
		}
		//dispatchMessage(new Message(this, Events.OPTIMIZATION_FINISHED.toString(), new Integer(iter)));
		notify(Events.OPTIMIZATION_FINISHED);
		return iter;
	}


}
