package org.rexpd.core.optimization.simulatedannealing;

import org.rexpd.core.base.AbstractBase;
import org.rexpd.core.optimization.OptimizationContext;
import org.rexpd.core.optimization.Parameter;

public class TestProblemBeale extends AbstractBase implements OptimizationContext {
	
	Parameter X = null;
	Parameter Y = null;
	
	public TestProblemBeale() {
		addNode(X = new Parameter(this, "X", 0.0, -4.5, 4.5));
		addNode(Y = new Parameter(this, "Y", 0.0, -4.5, 4.5));
	}

	@Override
	public double[] getTargets() {
		double[] targets = { 0.0 };
		return targets;
	}

	@Override
	public double[] getWeights() {
		double[] weights = { 1.0 };
		return weights;
	}

	@Override
	public double[] getCalculatedValues() {
		double x = X.getValue();
		double y = Y.getValue();
		double a = (1.5 - x + x*y);
		double b = (2.25 - x + x*y);
		double c = (1.625 - x + x*y*y);
		double value[] = { a*a + b*b + c*c };
		return value;
	}

}
