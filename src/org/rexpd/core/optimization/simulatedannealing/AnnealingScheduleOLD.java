package org.rexpd.core.optimization.simulatedannealing;


public enum AnnealingScheduleOLD {

	CONSTANT {
		@Override
		public void init(double tempStart, double tempEnd, int nSteps) {
			resetValues(nSteps);
			for (int i = 0; i < nSteps; i++)
				stepValues[i] = tempStart;
		}
	},

	LINEAR {
		@Override
		public void init(double tempStart, double tempEnd, int nSteps) {
			resetValues(nSteps);
			for (int i = 0; i < nSteps; i++)
				stepValues[i] = tempStart - (tempStart - tempEnd) * ((double) i / nSteps);
		}
	},

	GEOMETRIC {
		@Override
		public void init(double tempStart, double tempEnd, int nSteps) {
			resetValues(nSteps);
			double alpha = Math.pow(tempEnd / tempStart, 1.0 / nSteps);
			System.out.println("alpha: " + alpha);
			for (int i = 0; i < nSteps; i++)
				stepValues[i] = Math.pow(alpha, i) * tempStart;
		}
	},

	EXPONENTIAL {
		@Override
		public void init(double tempStart, double tempEnd, int nSteps) {
			resetValues(nSteps);
			for (int i = 0; i < nSteps; i++)
				stepValues[i] = (float) (tempStart * Math.pow(tempEnd / tempStart, (double) i / nSteps));
		}
	};

	public boolean hasNext() {
		return (stepValues != null && currentStep < stepValues.length);
	}

	public double nextValue() {
		return stepValues[currentStep++];
	}

	public abstract void init(double tempStart, double tempEnd, int nSteps);
	
	public void resetValues(int nSteps) {
		stepValues = new double[nSteps];
		currentStep = 0;
	}

	private static int currentStep = 0;
	private static double[] stepValues = null;

}


