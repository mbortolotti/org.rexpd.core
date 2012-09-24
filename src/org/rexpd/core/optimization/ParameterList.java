package org.rexpd.core.optimization;

import java.util.ArrayList;
import java.util.List;

import org.rexpd.core.base.AbstractBase;
import org.rexpd.core.base.IBase;


public class ParameterList extends AbstractBase {
	
	private List<Parameter> parameters = null;
	private String parameterLabel = null;
	
	public ParameterList() {
		this(null, "List", "Parameter", 1);
	}
	
	public ParameterList(IBase parent, String listLabel, String baseLabel, int npar) {
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
	public List<? extends IBase> getNodes() {
		List<IBase> nodes = new ArrayList<IBase>();
		nodes.addAll(parameters);
		return nodes;
	}

}
