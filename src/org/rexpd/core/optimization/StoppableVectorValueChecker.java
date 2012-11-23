package org.rexpd.core.optimization;

import org.apache.commons.math3.optimization.PointVectorValuePair;
import org.apache.commons.math3.optimization.SimpleVectorValueChecker;

public class StoppableVectorValueChecker extends SimpleVectorValueChecker {
	
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
