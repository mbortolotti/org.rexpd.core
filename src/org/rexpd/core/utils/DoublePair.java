package org.rexpd.core.utils;

public class DoublePair {
	
	private double min_value = 0.0;
	private double max_value = 0.0;
	
	public DoublePair(double min, double max) {
		min_value = min;
		max_value = max;
	}
	
	public void setMin(double min) {
		min_value = min;
	}
	
	public void setMax(double max) {
		max_value = max;
	}
	
	public double getMin() {
		return min_value;
	}
	
	public double getMax() {
		return max_value;
	}

}
