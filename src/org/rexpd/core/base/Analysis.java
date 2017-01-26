package org.rexpd.core.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class representing analysis context
 *
 */
public abstract class Analysis extends AbstractBase {
	
	public static final String ANALYSIS = "Analysis";

	public enum Events {
		MODEL_UPDATE_FULL,
		MODEL_UPDATE,
		ANALYSIS_STARTED,
		ANALYSIS_STEP_COMPLETED,
		ANALYSIS_FINISHED
	}
	
	@Override
	public String getClassID() {
		return ANALYSIS;
	}
	
	private boolean running = false;
	private AnalysisMonitor monitor = new DefaultAnalysisMonitor();

	public abstract int getStepsNumber();
	// TODO - change to ComputeException
	public abstract void update() throws AnalysisException;

	public abstract void run() throws AnalysisException;

	public abstract boolean canRun();
	
	/** Save analysis state **/
	public abstract Object saveTo();
	/** restore from state **/
	public abstract void restoreFrom(Object state);

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean run) {
		running = run;
	}
	
	public AnalysisMonitor getMonitor() {
		return monitor;
	}
	
	public void setMonitor(AnalysisMonitor m) {
		monitor = m;
	}

	@Override
	public List<? extends IBase> getNodes() {
		List<IBase> nodes = new ArrayList<IBase>();
		nodes.addAll(super.getNodes());
		nodes.addAll(getInputNodes());
		return nodes;
	}
	
	public List<BaseLink<? extends IBase>> getInputNodes() {
		return Collections.emptyList();
	}
	
	public List<? extends IBase> getOutputNodes() {
		return Collections.emptyList();
	}

}
