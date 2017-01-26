package org.rexpd.core.optimization.simulatedannealing;

import org.rexpd.core.base.AbstractBase;
import org.rexpd.core.optimization.OptimizationContext;
import org.rexpd.core.optimization.Parameter;

public class TestProblemGoldstein extends AbstractBase implements OptimizationContext {
	
	Parameter X = null;
	Parameter Y = null;
	
	public TestProblemGoldstein() {
		addNode(X = new Parameter(this, "X", 0.0, -2.0, 2.0));
		addNode(Y = new Parameter(this, "Y", 0.0, -2.0, 2.0));
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
		double a = 1 + (x + y + 1) * (x + y + 1) * (19 - 14*x + 3*x*x - 14*y + 6*x*y + 3*y*y);
		double b = 30 + (2*x - 3*y) * (2*x - 3*y) * (18 - 32*x + 12*x*x + 48*y - 36*x*y + 27*y*y);
		double value[] = { a*b };
		return value;
	}

}
