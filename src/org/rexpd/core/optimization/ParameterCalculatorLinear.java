package org.rexpd.core.optimization;

import java.util.Map;
import java.util.Map.Entry;


public class ParameterCalculatorLinear implements ParameterCalculator {

	private double constantTerm = 0.0;
	private Map<Parameter, Double> parameters;

	public double getValue() {
		double value = 0;
		for (Entry<Parameter, Double> entry : parameters.entrySet())
			value += entry.getValue().doubleValue() * entry.getKey().getValue();
		return value;
	}

}
