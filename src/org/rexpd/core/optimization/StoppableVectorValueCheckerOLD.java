package org.rexpd.core.optimization;

import org.apache.commons.math3.optimization.PointVectorValuePair;
import org.apache.commons.math3.optimization.SimpleVectorValueChecker;

@Deprecated
public class StoppableVectorValueCheckerOLD extends SimpleVectorValueChecker {
	
	private static final double relativeThreshold = 1e-6;
	private static final double absoluteThreshold = 1e-6;
	private static final int maxIter = -1;
	
	public StoppableVectorValueCheckerOLD() {
		super(relativeThreshold, absoluteThreshold, maxIter);
	}

	private boolean stopRequested = false;

	@Override
	public boolean converged(int iteration, PointVectorValuePair previous,
			PointVectorValuePair current) {
		return super.converged(iteration, previous, current) || stopRequested;
	}

	public void setStopRequested(boolean stop) {
		stopRequested = stop;
	}

}
