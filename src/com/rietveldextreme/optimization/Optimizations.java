package com.rietveldextreme.optimization;

import java.util.ArrayList;
import java.util.List;

public abstract class Optimizations {

	/**
	 * Return a list containing all the parameters of a given optimizable instance
	 * 
	 * @param base the base obtimizable instance 
	 * @param fullTree descend the full hyerarchy looking for parameters
	 * @return
	 */
	public static List<Parameter> getParameters(Optimizable base, boolean fullTree) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		for(Optimizable child : base.getNodes()) {
			if (child instanceof Parameter)
				parameters.add((Parameter) child);
			if (fullTree)
				parameters.addAll(getParameters(child, fullTree));
		}
		return parameters;
	}

	public static List<Parameter> getParameters(Optimizable base) {
		return getParameters(base, true);
	}

	/**
	 * Return a list containing all the optimizable parameters of a given optimizable instance
	 * 
	 * @param base the base obtimizable instance 
	 * @param fullTree descend the full hyerarchy looking for optimizable parameters
	 * @return
	 */
	public static List<Parameter> getOptimizableParameters(Optimizable base, boolean fullTree) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		for(Parameter parameter : getParameters(base, fullTree))
			if (parameter.isOptimizable())
				parameters.add(parameter);
		return parameters;
	}

	public static List<Parameter> getOptimizableParameters(Optimizable base) {
		return getOptimizableParameters(base, true);
	}

	/**
	 * Check if the given optimizable object and its children are optimizable
	 * 
	 * @param base the base obtimizable instance 
	 * @param fullTree check if the whole parameter hierarchy is optimizable
	 * @return true if the given object is optimizable, false otherwise
	 */
	public static boolean isOptimizable(Optimizable base, boolean fullTree) {
		if (base instanceof Parameter)
			return ((Parameter) base).isOptimizable() && ((Parameter) base).isEnabled();
		for (Optimizable node : base.getNodes()) {
			if (fullTree) {
				if (!isOptimizable(node, true) && isEnabled(node, false))
					return false;
			}
			else {
				if (isOptimizable(node, false))
					return true;
			}
		}
		return fullTree;
	}

	/**
	 * Set the optimization state of this optimizable object and all its children
	 * 
	 * @param base the base obtimizable instance
	 * @param optimizable optimization state
	 */
	public static void setOptimizable(Optimizable base, boolean optimizable) {
		if (base instanceof Parameter)
			((Parameter) base).setOptimizable(optimizable);
		for (Optimizable node : base.getNodes())
			setOptimizable(node, optimizable);
	}
	
	/**
	 * Check if the given optimizable object and its children are enabled
	 * 
	 * @param opt the base obtimizable instance 
	 * @param fullTree check if the whole parameter hierarchy is enabled
	 * @return true if the given object is enabled, false otherwise
	 */
	public static boolean isEnabled(Optimizable base, boolean fullTree) {
		if (base instanceof Parameter)
			return ((Parameter) base).isEnabled();
		for (Optimizable node : base.getNodes()) {
			if (fullTree) {
				if (!isEnabled(node, true))
					return false;
			}
			else {
				if (isEnabled(node, false))
					return true;
			}
		}
		return fullTree;
	}

	/**
	 * Set the enablement state of this optimizable object and all its children
	 * 
	 * @param base the base obtimizable instance
	 * @param enabled enablement state
	 */
	public static void setEnabled(Optimizable base, boolean enabled) {
		if (base instanceof Parameter)
			((Parameter) base).setEnabled(enabled);
		for (Optimizable node : base.getNodes())
			setEnabled(node, enabled);
	}

	/**
	 *  Search the parent Optimizable for a child parameter with the given name
	 *  
	 * @param base the parent node to search for the given parameter
	 * @param name the parameter's name
	 * @param fullTree if true, descend the full ParameterNode tree
	 * @return the parameter with the given name or null if it doesn't exist
	 */
	public static Parameter getParameterByName(Optimizable base, String name, boolean fullTree) {
		for(Parameter parameter : getParameters(base, fullTree))
			if (parameter.getLabel().equals(name))
				return parameter;
		return null;
	}

	/**
	 *  Search the parent Optimizable for a child parameter with the given name
	 *  
	 * @param base the parent node to search for the given parameter
	 * @param name the parameter's name
	 * @return the parameter with the given name or null if it doesn't exist
	 */
	public static Parameter getParameterByName(Optimizable base, String name) {
		return getParameterByName(base, name, false);
	}

	/**
	 *  Search the parent ParameterNode for a child parameter with the given ID
	 *  
	 * @param base the parent node to search for the given parameter
	 * @param ID the parameter's ID
	 * @param fullTree if true, descend the full ParameterNode tree
	 * @return the parameter with the given ID or null if it doesn't exist
	 */
	public static Parameter getParameterByID(Optimizable base, String ID, boolean fullTree) {
		for(Parameter parameter : getParameters(base, fullTree))
			if (parameter.getUID().equals(ID))
				return parameter;
		return null;
	}

}
