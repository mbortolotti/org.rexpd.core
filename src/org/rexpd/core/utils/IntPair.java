package org.rexpd.core.utils;

public class IntPair {

	private int min_value = 0;
	private int max_value = 0;

	public IntPair(int min, int max) {
		min_value = min;
		max_value = max;
	}

	public void setMin(int min) {
		min_value = min;
	}

	public void setMax(int max) {
		max_value = max;
	}

	public int getMin() {
		return min_value;
	}

	public int getMax() {
		return max_value;
	}
}
