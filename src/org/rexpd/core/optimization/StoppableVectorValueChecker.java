package org.rexpd.core.optimization;

import org.apache.commons.math3.optim.PointVectorValuePair;
import org.apache.commons.math3.optim.SimpleVectorValueChecker;

public class StoppableVectorValueChecker extends SimpleVectorValueChecker {
	
	private static final double relativeThreshold = 1e-6;
	private static final double absoluteThreshold = 1e-6;
	private static final int maxIter = 1000;
	
	private OptimizationContext optimizationContext = null;
	
	public StoppableVectorValueChecker(OptimizationContext context) {
		super(relativeThreshold, absoluteThreshold, maxIter);
		optimizationContext = context;
	}

	@Override
	public boolean converged(int iteration, PointVectorValuePair previous,
			PointVectorValuePair current) {
		return super.converged(iteration, previous, current) || optimizationContext.hasStopRequested();
	}

}
