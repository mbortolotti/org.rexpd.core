package org.rexpd.core.optimization;

import java.util.Iterator;
import java.util.List;

import org.rexpd.core.base.Analysis;
import org.rexpd.core.base.AnalysisException;


@Deprecated
public abstract class OptimizationAnalysis extends Analysis implements OptimizationContext {
	
	private OptimizationAlgorithm method = null;
	private OptimizationResult results = null;
	private ParameterSet parameterSet = null;
	private boolean autoParamSelection = true;

	public OptimizationAnalysis () {
		method = new LevenbergMarquardtPJNEW();
	}

	public OptimizationAlgorithm getOptimizationAlgorithm() {
		return method;
	}

	public void setOptimizationMethod(OptimizationAlgorithm om) {
		method = om;
	}

	public boolean isAutoParamSelection() {
		return autoParamSelection;
	}

	public void setAutoParamSelection(boolean auto) {
		autoParamSelection = auto;
	}

	public OptimizationResult getOptimizationResults() {
		return results;
	}

	/** Analysis class implementation **/

	@Override
	public void run() {
		setRunning(true);
		results = getOptimizationAlgorithm().minimize(this);
		setRunning(false);
	}
	
	@Override
	public Object saveTo() {
		/** Analysis state is represented as the full set of the model's parameters **/
		ParameterSet parameterSet = new ParameterSet(this, true);
		return parameterSet;
	}

	@Override
	public void restoreFrom(Object state) {
		if (!(state instanceof ParameterSet))
			return; // should raise exception!!
		Iterator<Parameter> restoreSetIt = ((ParameterSet) state).getParameters().iterator();
		for (Parameter parameter : Optimizations.getParameters(this)) {
			while (restoreSetIt.hasNext()) {
				Parameter toRestore = restoreSetIt.next();
				if (toRestore.getUID().equals(parameter.getUID())) {
					parameter.restoreFrom(toRestore);
					restoreSetIt.remove();
					break;
				}
			}
		}
		try {
			update();
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // TODO is it really mandatory?
	}

	/** OptimizationContext interface implementation **/
	
	@Override
	public double[] getParameterValues() {
		// TODO implement manual parameter selection!!
		List<Parameter> parameters = Optimizations.getOptimizableParameterList(this);
		double[] values = new double[parameters.size()];
		for (int np = 0; np < parameters.size(); np++)
			values[np] = parameters.get(np).getValue();
		return values;
	}

	@Override
	public void setParameterValues(double[] values) {
		List<Parameter> parameters = Optimizations.getOptimizableParameterList(this);
		for (int np = 0; np < parameters.size(); np++)
			parameters.get(np).setValue(values[np]);
	}

}
