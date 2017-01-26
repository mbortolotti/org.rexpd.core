package org.rexpd.core.optimization;

import java.util.List;

import org.rexpd.core.base.IBase;

public interface OptimizationContext extends IBase {

	public double[] getTargets();

	public double[] getWeights();

	public double[] getCalculatedValues();
	
	default public double[] getParameterValues() {
		List<Parameter> parameters = Optimizations.getOptimizableParameterList(this);
		double[] values = new double[parameters.size()];
		for (int np = 0; np < parameters.size(); np++)
			values[np] = parameters.get(np).getValue();
		return values;
	}

	default public void setParameterValues(double[] values) {
		List<Parameter> parameters = Optimizations.getOptimizableParameterList(this);
		for (int np = 0; np < parameters.size(); np++)
			parameters.get(np).setValue(values[np]);
	}
	
	default public void updateContext(OptimizationResult result) { }
	
	default public boolean hasStopRequested() { return false; }

}
