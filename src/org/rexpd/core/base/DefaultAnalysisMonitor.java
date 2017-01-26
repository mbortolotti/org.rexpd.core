package org.rexpd.core.base;

public class DefaultAnalysisMonitor implements AnalysisMonitor {
	

	private boolean canceled = false;

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void setCanceled(boolean value) {
		canceled = value;
	}

}
