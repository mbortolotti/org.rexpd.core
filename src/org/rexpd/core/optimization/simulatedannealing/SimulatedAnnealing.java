
package org.rexpd.core.optimization.simulatedannealing;

import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;
import org.rexpd.core.observer.MessageService;
import org.rexpd.core.optimization.OptimizationAlgorithm;
import org.rexpd.core.optimization.OptimizationAnalysis;
import org.rexpd.core.optimization.OptimizationResult;
import org.rexpd.core.optimization.Optimizations;
import org.rexpd.core.optimization.Parameter;

import org.rexpd.core.optimization.Solution;
import org.rexpd.core.optimization.OptimizationResult.EventType;



public class SimulatedAnnealing extends OptimizationAlgorithm {

	public static final String SIMULATED_ANNEALING = "Simulated Annealing";

	//private OptimizationAnalysis problem = null;
	private MersenneTwister randomizer = null;

	private AnnealingSchedule annealingSchedule = new AnnealingScheduleGeometric();

	int currentParameter = 0;

	private double currFitness;
	private double bestFitness;

	private static final int DEFAULT_ITERATIONS = 10;
	private static final int DEFAULT_POPULATION = 10000;

	private static final int DEFAULT_PARAMETER_MULTIPLIER = 500;

	private static final double DEFAULT_T0 = 1E3;
	private static final double DEFAULT_T1 = 1E-8;
	private static final double DEFAULT_ACCEPTANCE_START = 0.8;
	private static final double DEFAULT_ACCEPTANCE_END = 1E-16;

	private int population;
	private double tempStart;
	private double tempEnd;
	private boolean startRandom = true;
	private boolean autoCalibration = true;

	public SimulatedAnnealing() {
		randomizer = new MersenneTwister();
		setIterations(DEFAULT_ITERATIONS);
		setPopulation(DEFAULT_POPULATION);
		setTStart(DEFAULT_T0);
		setTEnd(DEFAULT_T1);
		enableParamLimits(true);
	}	

	@Override
	public String getName() {
		return SIMULATED_ANNEALING;
	}

	@Override
	public OptimizationResult minimize(OptimizationAnalysis problem) {

		OptimizationResult results = new OptimizationResult();
		List<Parameter> parameters = Optimizations.getOptimizableParameters(problem);

		if (parameters.size() < 1) {
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "No optimizable parameters selected!"));
			return results;
		}

		Solution startSolution = new Solution(problem);
		bestFitness = getScalarFitness(problem, startSolution);
		currentParameter = 0;

		if (getInitRandom()) {
			startSolution = randomize(startSolution);
		}

		Solution currentSolution = startSolution;
		Solution bestSolution = startSolution;

		currFitness = getScalarFitness(problem, startSolution);

		System.out.println("currFitness: " + currFitness);

		MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_STARTED, "Starting Simulated Annealing..."));

		if (getAutoCalibration()) {
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_MESSAGE, "Performing automatic calibration..."));
			int populationSize = startSolution.getParameters().size() * DEFAULT_PARAMETER_MULTIPLIER;
			double T0 = calibrateTemperature(problem, DEFAULT_ACCEPTANCE_START, populationSize / 10, 5);
			double T1 = calibrateTemperature(problem, DEFAULT_ACCEPTANCE_END, populationSize / 10, 5);
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_MESSAGE, "...done!"));
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_MESSAGE, "Population size = " + populationSize));
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_MESSAGE, "Initial temperature T0 = " + T0));
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_MESSAGE, "Final temperature T1 = " + T1));
			setPopulation(populationSize);
			setTStart(T0);
			setTEnd(T1);
		}

		getAnnealingSchedule().initSchedule(getTStart(), getTEnd(), getIterations());

		while (getAnnealingSchedule().hasNext()) {
			double T = getAnnealingSchedule().nextValue();
			MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.ITERATION_PERFORMED, "Iteration performed"));
			//System.out.println("Current temperature: " + T);
			for (int ni = 0; ni < getPopulation(); ni++) {
				if (hasStopRequested()) {
					MessageService.getInstance().notifyObservers(new OptimizationResult(EventType.OPTIMIZATION_INTERRUPTED, "Optimization interrupted by user"));
					return results;
				}

				Solution newSolution = mutate(currentSolution);
				double newFitness = getScalarFitness(problem, newSolution);
				double prob = Math.exp( - (newFitness - currFitness) / (T));
				double random = getRandomValue();

				if (newFitness < currFitness || prob > random) {
					//for (Parameter parameter : newSolution.getParameters()) 
					//	System.out.print(parameter.getLabel() + " = " + parameter.getValue() + "; ");
					//System.out.println();
					//System.out.println("currFitness = " + currFitness + "; newFitness = " + newFitness);

					currFitness = newFitness;
					currentSolution = newSolution;
					if (currFitness < bestFitness) {
						bestFitness = currFitness;
						bestSolution = currentSolution;
					}
				}
			}
			problem.setSolution(bestSolution);
		}

		problem.setSolution(bestSolution);

		results.setParameters(bestSolution.getParameters());
		results.setFitness(bestFitness);
		results.setType(EventType.OPTIMIZATION_FINISHED);
		results.setMessage("Optimization finished!");
		MessageService.getInstance().notifyObservers(results);

		return results;
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
			double E0 = getScalarFitness(problem, trialState);
			Solution neighbState = mutate(trialState);
			double E1 = getScalarFitness(problem, neighbState);
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

	private double getScalarFitness(OptimizationAnalysis problem, Solution solution) {
		problem.setParameterValues(solution.getParameterValues());
		double scalarFitness = 0.0;
		double[] values = problem.getCalculatedValues();
		double[] targets = problem.getTargets();
		double[] weights = problem.getWeights();
		for (int nv = 0; nv < values.length; nv++) {
			double delta = (values[nv] - targets[nv]) / weights[nv];
			scalarFitness += delta * delta;
		}
		//return Math.sqrt(scalarFitness) / values.length;
		return Math.sqrt(scalarFitness);
	}

	public AnnealingSchedule getAnnealingSchedule() {
		return annealingSchedule;
	}

	public void setAnnealingSchedule(AnnealingSchedule schedule) {
		annealingSchedule = schedule;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int pop) {
		population = pop;
	}

	public double getTStart() {
		return tempStart;
	}

	public void setTStart(double TStart) {
		tempStart = TStart;
	}

	public double getTEnd() {
		return tempEnd;
	}

	public void setTEnd(double TEnd) {
		tempEnd = TEnd;
	}

	public boolean getInitRandom() {
		return startRandom;
	}

	public void setInitRandom(boolean random) {
		startRandom = random;
	}

	public boolean getAutoCalibration() {
		return autoCalibration;
	}

	public void setAutoCalibration(boolean autoCalib) {
		autoCalibration = autoCalib;
	}


}

