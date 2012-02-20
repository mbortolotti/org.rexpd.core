package com.rietveldextreme.optimization;

import java.util.ArrayList;
import java.util.List;

public class ParameterList extends AbstractOptimizable implements Optimizable {
	
	private List<Parameter> parameters = null;
	private String parameterLabel = null;
	
	
	public ParameterList() {
		this(null, "List", "Parameter", 1);
	}
	
	public ParameterList(Optimizable parent, String listLabel, String baseLabel, int npar) {
		super(parent);
		setLabel(listLabel);
		parameterLabel = baseLabel;
		parameters = new ArrayList<Parameter>();
		resetParameters(npar);
	}

	private void updateParameters() {
		for (int n = 0; n < parameters.size(); n++)
			parameters.get(n).setLabel(parameterLabel + n);
	}
	
	public Parameter getParameter(int position) {
		return parameters.get(position);
	}
	
	public int getSize() {
		return parameters.size();
	}

	public void addParameter() {
		parameters.add(new Parameter(this, parameterLabel));
		updateParameters();
	}
	
	public void resetParameters(int npar) {
		parameters.clear();
		for (int np = 0; np < npar; np++)
			parameters.add(new Parameter(this, parameterLabel));
		updateParameters();
	}

	public void removeParameter(int position) {
		if ((position < 0) || (position > parameters.size()))
			return;
		parameters.remove(position);
		updateParameters();
	}

	@Override
	public List<Optimizable> getNodes() {
		List<Optimizable> nodes = new ArrayList<Optimizable>();
		nodes.addAll(parameters);
		return nodes;
	}

}
