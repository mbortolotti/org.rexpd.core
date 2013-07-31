
package org.rexpd.core.optimization.simulatedannealing;

import org.apache.commons.math3.random.MersenneTwister;
import org.rexpd.core.optimization.OptimizationAlgorithm;
import org.rexpd.core.optimization.OptimizationAnalysis;
import org.rexpd.core.optimization.OptimizationResult;
import org.rexpd.core.optimization.Parameter;

import org.rexpd.core.optimization.Solution;



public class SimulatedAnnealing extends OptimizationAlgorithm {

	public static final String SIMULATED_ANNEALING = "Simulated Annealing";

	private OptimizationAnalysis problem = null;
	private MersenneTwister randomizer = null;
	//private AnnealingSchedule coolingSchedule = AnnealingSchedule.GEOMETRIC;

	private AnnealingSchedule1 annealingSchedule = new AnnealingScheduleGeometric();

	//protected int nParam;
	private double startWSS;
	int currentParameter = 0;

	//private double[] currSolution;			// current solution parameters
	//private double[] bestSolution;    		// best solution parameters

	//protected float[] lParamBound;
	//protected float[] uParamBound;

	private double currFitness;
	private double bestFitness;

	private static final int DEFAULT_STEPS = 10;
	private static final int DEFAULT_ITER = 10000;

	//private double temp_const;
	private double temp_start;
	private double temp_end;
	private boolean startRandom;

	public SimulatedAnnealing() {
		randomizer = new MersenneTwister();
		setStepsNumber(DEFAULT_STEPS);
		setIterationsPerStep(DEFAULT_ITER);
		setTStart(3000);
		setTEnd(0.000001);
		enableParamLimits(true);
	}	

	@Override
	public String getName() {
		return SIMULATED_ANNEALING;
	}

	@Override
	public OptimizationResult minimize(OptimizationAnalysis p) {

		problem = p;

		OptimizationResult result = new OptimizationResult();
		

		System.out.println("Temperature steps : " + getStepsNumber());
		System.out.println("Trial size : " + getIterationsPerStep());

		getAnnealingSchedule().initSchedule(getTStart(), getTEnd(), getStepsNumber());

		Solution startSolution = new Solution(problem);
		currentParameter = 0;

		double T0 = calibrateTemperature(problem, 0.8, 5000, 5);
		System.out.println("Starting temperature T0 = " + T0);
		double T1 = calibrateTemperature(problem, 1E-16, 5000, 5);
		System.out.println("final temperature T1 = " + T1);

		bestFitness = getScalarFitness(startSolution);

		if (getInitRandom()) {
			/** startSolution = mutateSolution(getTStart(), startSolution, lowerLimits, upperLimits);
			problem.setParameterValues(startSolution); **/

			startSolution = randomize(startSolution);
			//problem.setParameterValues(startSolution.getParameterValues());
		}
		
		Solution currentSolution = startSolution;
		Solution bestSolution = startSolution;

		currFitness = getScalarFitness(startSolution);

		System.out.println("currFitness: " + currFitness);

		while (getAnnealingSchedule().hasNext()) {
			double T = getAnnealingSchedule().nextValue();
			//System.out.println("Current temperature: " + T);
			for (int ni = 0; ni < getIterationsPerStep(); ni++) {
				if (hasStopRequested())
					return result;
				
				Solution newSolution = mutate(currentSolution);
				//problem.setParameterValues(newSolution.getParameterValues());
				double newFitness = getScalarFitness(newSolution);
				double prob = Math.exp( - (newFitness - currFitness) / (T));
				double random = getRandomValue();

				if (newFitness < currFitness || prob > random) {
					for (Parameter parameter : newSolution.getParameters()) 
						System.out.print(parameter.getLabel() + " = " + parameter.getValue() + "; ");
					System.out.println();
					System.out.println("currFitness = " + currFitness + "; newFitness = " + newFitness);

					currFitness = newFitness;
					currentSolution = newSolution;
					if (currFitness < bestFitness) {
						System.out.println("fitness improved :" + newFitness);
						bestFitness = currFitness;
						bestSolution = currentSolution;
					}
				}
			}
		}
		System.out.println("SA run finished");
		System.out.println("bestFitness = " + bestFitness);


		//startSolutionLoop();
		return new OptimizationResult();
	}

	private double getRandomValue() {
		return randomizer.nextDouble();
	}

	private double randomValue(double min, double max) {
		return min + (max - min) * randomizer.nextDouble();
	}

	private double calibrateTemperature(OptimizationAnalysis problem, double acceptance, int ntrials, int niter) {
		
		Solution start = new Solution(problem);

		double E0array[] = new double[ntrials];
		double E1array[] = new double[ntrials];

		double deltaESum = 0.0;
		/** Generate the sets of trial states and their neighbors **/
		for (int i = 0; i < ntrials; i++) {
			Solution trialState = randomize(start);
			double E0 = getScalarFitness(trialState);
			Solution neighbState = mutate(trialState);
			double E1 = getScalarFitness(neighbState);
			/** assume positive transition probabilities **/
			E0array[i] = Math.min(E0, E1);
			E1array[i] = Math.max(E0, E1);
			deltaESum += (E1array[i] - E0array[i]);
		}
		/** Approximate starting temperature **/
		double Ti = - deltaESum / ntrials / Math.log(acceptance);
		double Ti_1 = 0;
		
		for (int ni = 0; ni < niter; ni++) {
			double pi = probability(E0array, E1array, Ti);
			if (pi < 1E-16)
				pi = 1E-16;
			Ti_1 = Ti * Math.log(pi) / Math.log(acceptance);
			Ti = Ti_1;

		}
		return Ti;
	}

	private double probability(double[] E0array, double[] E1array, double temperature) {
		double EMaxSum = 0.0;
		double EMinSum = 0.0;
		for (int i = 0; i < E0array.length; i++) {
			double Emin = E0array[i];
			double Emax = E1array[i];
			//double Emin = Math.min(E0, E1);
			//double Emax = Math.max(E0, E1);
			EMaxSum += Math.exp( - Emax / temperature);
			EMinSum += Math.exp( - Emin / temperature);
		}
		if (EMinSum < 1E-16)
			EMinSum = 1E-16;
		return Math.pow(EMaxSum/EMinSum, 1.5);
	}

	private Solution mutate(Solution startSolution) {
		if (currentParameter >= startSolution.getParameters().size())
			return null;
		Solution newSolution = new Solution(startSolution);
		Parameter active = newSolution.getParameters().get(currentParameter);
		double minValue = active.getMinValue();
		double maxValue = active.getMaxValue();
		double newValue = randomValue(minValue, maxValue);
		active.setValue(newValue);
		if (currentParameter < newSolution.getParameters().size() - 1)
			currentParameter++;
		else
			currentParameter = 0;
		return newSolution;
	}

	private Solution randomize(Solution startSolution) {
		Solution newSolution = new Solution(startSolution);
		for (Parameter parameter : newSolution.getParameters()) {
			double minValue = parameter.getMinValue();
			double maxValue = parameter.getMaxValue();
			double newValue = randomValue(minValue, maxValue);
			parameter.setValue(newValue);
		}
		return newSolution;
	}

	private double getScalarFitness(Solution solution) {
		problem.setParameterValues(solution.getParameterValues());
		double scalarFitness = 0.0;
		double[] values = problem.getCalculatedValues();
		double[] targets = problem.getTargets();
		double[] weights = problem.getWeights();
		for (int nv = 0; nv < values.length; nv++) {
			double delta = (values[nv] - targets[nv]) / weights[nv];
			scalarFitness += delta * delta;
		}
		return Math.sqrt(scalarFitness) / values.length;
	}

	public AnnealingSchedule1 getAnnealingSchedule() {
		return annealingSchedule;
	}

	public void setAnnealingSchedule(AnnealingSchedule1 schedule) {
		annealingSchedule = schedule;
	}

	public double getTStart() {
		return temp_start;
	}

	public void setTStart(double TStart) {
		temp_start = TStart;
	}

	public double getTEnd() {
		return temp_end;
	}

	public void setTEnd(double TEnd) {
		temp_end = TEnd;
	}

	public boolean getInitRandom() {
		return startRandom;
	}

	public void setInitRandom(boolean random) {
		startRandom = random;
	}


}

