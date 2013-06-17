package org.rexpd.core.base;

import java.util.List;

import org.rexpd.core.optimization.Optimizations;
import org.rexpd.core.optimization.Parameter;

public abstract class Analysis extends AbstractBase {

	public enum Events {
		MODEL_UPDATED
	}

	private boolean running = false;

	public abstract void update();

	public abstract void run();

	public abstract boolean canRun();

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean run) {
		running = run;
	}

	public double[] getParameterValues() {
		List<Parameter> parameters = Optimizations.getOptimizableParameters(this);
		double[] values = new double[parameters.size()];
		for (int np = 0; np < parameters.size(); np++)
			values[np] = parameters.get(np).getValue();
		return values;
	}

	public void setParameterValues(double[] values) {
		List<Parameter> parameters = Optimizations.getOptimizableParameters(this);
		for (int np = 0; np < parameters.size(); np++)
			parameters.get(np).setValue(values[np]);
	}

}
