package com.rietveldextreme.optimization;

import java.util.ArrayList;
import java.util.List;


public class OptimizationResults {

	private List<Parameter> parameters = null;
	private double chiSquare = 0.0;
	private String resultsLog = "";

	public OptimizationResults() {
		parameters = new ArrayList<Parameter>();
	}

	public OptimizationResults(List<Parameter> params, double chisq, String log) {
		setParameters(params);
		setChiSquare(chisq);
		setResultsLog(log);
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> params) {
		if (parameters == null)
			parameters = new ArrayList<Parameter>();
		else
			parameters.clear();
		for (Parameter parameter : params) {
			Parameter copy = new Parameter(parameter.getParentNode(), parameter.getLabel());
			copy.setValue(parameter.getValue());
			copy.setError(parameter.getError());
			parameters.add(copy);
		}
	}

	public double getChiSquare() {
		return chiSquare;
	}

	public void setChiSquare(double chisq) {
		chiSquare = chisq;
	}

	public String getResultsLog() {
		return resultsLog;
	}

	public void setResultsLog(String log) {
		resultsLog = log;
	}

}
