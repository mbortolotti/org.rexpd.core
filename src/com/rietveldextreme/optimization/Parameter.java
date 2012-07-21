package com.rietveldextreme.optimization;

import java.util.Collections;
import java.util.List;

import com.rietveldextreme.serialization.AbstractBase;
import com.rietveldextreme.serialization.IBase;



public class Parameter extends AbstractBase {

	public static final String PARAMETER_TYPE = "Parameter";
	public static final double MIN_VALUE_DEFAULT = -1E8;
	public static final double MAX_VALUE_DEFAULT = 1E8;

	private double value = 0.0;
	private double error = 0.0;
	private double maxValue = 0.0;
	private double minValue = 0.0;
	private boolean optimizable = false;
	private ParameterCalculator calculator = null;

	public Parameter(IBase parent, String label, double value, double min, double max, boolean optimizable, boolean enabled) {
		super(parent);
		setLabel(label);
		setValue(value);
		setMinValue(min);
		setMaxValue(max);
		setOptimizable(optimizable);
		setEnabled(enabled);
	}

	public Parameter(IBase parent, String label, double value, double min_value, double max_value) {
		this(parent, label, value, min_value, max_value, false, true);
	}

	public Parameter(IBase parent, String label, double value) {
		this(parent, label, value, MIN_VALUE_DEFAULT, MAX_VALUE_DEFAULT, false, true);
	}

	public Parameter(IBase parent, String name) {
		this(parent, name, 0.0);
	}

	@ Override
	public List<? extends IBase> getNodes() {
		return Collections.emptyList();
	}

	@ Override
	public String getType() {
		return PARAMETER_TYPE;
	}

	@Override
	public void setEnabled(boolean en) {
		super.setEnabled(en);
		if (!isEnabled())
			setOptimizable(false);
	}

	public double getValue() {
		if (calculator != null)
			return calculator.getValue();
		return value;
	}

	public void setValue(double v) {
		if (calculator != null)
			return;
		value = v;
	}

	public double getError() {
		return error;
	}

	public void setError(double err) {
		error = err;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double max) {
		maxValue = max;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double min) {
		minValue = min;
	}

	public boolean isOptimizable() {
		return optimizable && isEnabled();
	}

	public void setOptimizable(boolean opt) {
		if (isEnabled())
			optimizable = opt;
	}

	public void setCalculator(ParameterCalculator calc) {
		calculator = calc;
	}
	
	public ParameterCalculator getCalculator() {
		return calculator;
	}

}


