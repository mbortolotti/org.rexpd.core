package org.rexpd.core.optimization;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.rexpd.core.base.IBase;



@Deprecated
public abstract class FitnessFunction implements FitnessContextVectorial {

	private IBase optimizable;

	private double[] X;

	private String name = "Function";

	public boolean LOG_ENABLED = false;

	private PrintWriter writer = null;
	long init_time_ms = 0;
	protected int iteration = 0;
	protected double bestFitness = 1E32;
	protected ArrayList<Double> iterations;
	protected ArrayList<Double> values;
	
	public FitnessFunction(IBase opti) {
		optimizable = opti;
	}
	
	@Deprecated
	public void setOptimizable(IBase opti) {
		optimizable = opti;
	}

	public abstract double[] getValues();

	public abstract double[] getTargetValues();

	/** public void startLog() {
		iteration = 0;
		bestFitness = 1E32;
		iterations = new ArrayList<Double>();
		values = new ArrayList<Double>();
		try {
			writer = new PrintWriter("FitnessFunction.txt");
			init_time_ms = System.currentTimeMillis();
			iteration++;
			LOG_ENABLED = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeLog(double value) {
		if (LOG_ENABLED)
			writer.printf("%d\t%d\t%f\n", iteration++, System.currentTimeMillis() - init_time_ms, value);
	}

	public void stopLog() {
		if (LOG_ENABLED)
			writer.close();
		LOG_ENABLED = false;
	} **/
	

	@Override
	public double[] getTargets() {
		return getTargetValues();
	}

	public List<Parameter> getParameters() {
		return Optimizations.getOptimizableParameters(optimizable);
	}
	
	public double[] getParameterValues() {
		List<Parameter> parameters = getParameters();
		double[] values = new double[parameters.size()];
		for (int np = 0; np < parameters.size(); np++)
			values[np] = parameters.get(np).getValue();
		return values;
	}

	public void setParameterValues(double[] values) {
		List<Parameter> parameters = getParameters();
		for (int np = 0; np < parameters.size(); np++)
			parameters.get(np).setValue(values[np]);
	}

}
