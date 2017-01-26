package org.rexpd.core.optimization;

import java.util.ArrayList;
import java.util.List;

import org.rexpd.core.base.IBase;



@Deprecated // functionality should be moved inside OptimizationAnalysis
public abstract class FitnessFunction implements OptimizationContextVectorial {

	private IBase optimizable;

	//private double[] X;

	//private String name = "Function";

	//private boolean LOG_ENABLED = false;

	//private PrintWriter writer = null;
	
//	private long init_time_ms = 0;
//	private int iteration = 0;
//	private double bestFitness = 1E32;
//	private ArrayList<Double> iterations;
//	private ArrayList<Double> values;
	
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
		return Optimizations.getOptimizableParameterList(optimizable);
	}
	
	@Override
	public double[] getParameterValues() {
		List<Parameter> parameters = getParameters();
		double[] values = new double[parameters.size()];
		for (int np = 0; np < parameters.size(); np++)
			values[np] = parameters.get(np).getValue();
		return values;
	}

	@Override
	public void setParameterValues(double[] values) {
		List<Parameter> parameters = getParameters();
		for (int np = 0; np < parameters.size(); np++)
			parameters.get(np).setValue(values[np]);
	}

}
