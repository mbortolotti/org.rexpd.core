package org.rexpd.core.optimization;

import java.util.ArrayList;
import java.util.List;


public class OptimizationResult {

	public enum EventType {

		CYCLE_COMPLETED, 
		ITERATION_PERFORMED, 
		FITNESS_IMPROVED, 
		OPTIMIZATION_STARTED, 
		OPTIMIZATION_FINISHED,
		OPTIMIZATION_INTERRUPTED;

	}

	private List<Parameter> parameters = null;
	private double chiSquare = 0.0;
	private String message = "";
	private EventType eventType = EventType.CYCLE_COMPLETED;

	public OptimizationResult(EventType type, String msg) {
		eventType = type;
		message = msg;
		parameters = new ArrayList<Parameter>();
	}
	
	public OptimizationResult() {
		this(EventType.CYCLE_COMPLETED, "");
	}

	public EventType getType() {
		return eventType;
	}

	public void setType(EventType type) {
		eventType = type;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String msg) {
		message = msg;
	}

}
