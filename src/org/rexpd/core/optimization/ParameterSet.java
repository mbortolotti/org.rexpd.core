package org.rexpd.core.optimization;

import java.util.ArrayList;
import java.util.List;

import org.rexpd.core.base.IBase;

public class ParameterSet {
	
	List<Parameter> parameters;
	
	public ParameterSet(IBase optimizable, boolean allParameters) {
		parameters = new ArrayList<Parameter>();
		for (Parameter parameter : Optimizations.getParameters(optimizable))
			if (allParameters || parameter.isOptimizable())
				parameters.add(parameter.cloneTo());
	}

	public ParameterSet(IBase optimizable) {
		this(optimizable, false);
	}

	public ParameterSet(ParameterSet startSolution) {
		parameters = new ArrayList<Parameter>();
		for (Parameter parameter : startSolution.getParameters())
			parameters.add(parameter.cloneTo());
	}

	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public double[] getParameterValues() {
		double[] values = new double[parameters.size()];
		for (int np = 0; np < parameters.size(); np++)
			values[np] = parameters.get(np).getValue();
		return values;
	}

	public void setParameterValues(double[] values) {
		// TODO check array sizes!!
		for (int np = 0; np < parameters.size(); np++)
			parameters.get(np).setValue(values[np]);
	}

}
