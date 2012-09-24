package org.rexpd.core.optimization;


public class ParameterCalculatorIdentity implements ParameterCalculator {

	private Parameter master = null;
	
	public ParameterCalculatorIdentity(Parameter m) {
		master = m;
	}

	public double getValue() {
		return master.getValue();
	}

}
