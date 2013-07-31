package org.rexpd.core.optimization;

import java.util.ArrayList;
import java.util.List;

import org.rexpd.core.base.IBase;

public class Solution {
	
	List<Parameter> parameters;

	public Solution(IBase optimizable) {
		parameters = new ArrayList<Parameter>();
		for (Parameter parameter : Optimizations.getOptimizableParameters(optimizable))
			if (parameter.isOptimizable())
				parameters.add(new Parameter(parameter));
	}

	public Solution(Solution startSolution) {
		parameters = new ArrayList<Parameter>();
		for (Parameter parameter : startSolution.getParameters())
			parameters.add(new Parameter(parameter));
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
		for (int np = 0; np < parameters.size(); np++)
			parameters.get(np).setValue(values[np]);
	}

}
