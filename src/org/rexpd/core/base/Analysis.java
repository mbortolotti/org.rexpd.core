package org.rexpd.core.base;



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

}
