package org.rexpd.core.optimization;

import java.util.ArrayList;
import java.util.List;

import org.rexpd.core.base.IBase;


public abstract class Optimizations {

	
	/**
	 * Returns a list containing all the sub-nodes of a given IBase instance
	 * by default, parameter instances are cloned
	 * 
	 * @param base the base node instance 
	 * @param fullTree descend the full hierarchy looking for nodes
	 * @return
	 */
	public static List<IBase> getNodeTree(IBase base) {
		List<IBase> nodes = new ArrayList<IBase>();
		for(IBase node : base.getNodes()) {
			if (node instanceof Parameter)
				nodes.add(((Parameter) node).cloneTo());
			else
				nodes.add(new ParameterNode(node));
		}
		return nodes;
	}
	
	
	/**
	 * Return a list containing all the parameters of a given optimizable instance
	 * 
	 * @param base the base optimizable instance 
	 * @param fullTree descend the full hierarchy looking for parameters
	 * @return
	 */
	public static List<Parameter> getParameterList(IBase base, boolean fullTree) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		for(IBase child : base.getNodes()) {
			if (child instanceof Parameter)
				parameters.add((Parameter) child);
			if (fullTree)
				parameters.addAll(getParameterList(child, fullTree));
		}
		return parameters;
	}

	public static List<Parameter> getParameters(IBase base) {
		return getParameterList(base, true);
	}

	/**
	 * Return a list containing all the optimizable parameters of a given optimizable instance
	 * 
	 * @param base the base obtimizable instance 
	 * @param fullTree descend the full hierarchy looking for optimizable parameters
	 * @return
	 */
	public static List<Parameter> getOptimizableParameterList(IBase base, boolean fullTree) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		for(Parameter parameter : getParameterList(base, fullTree))
			if (parameter.isOptimizable())
				parameters.add(parameter);
		return parameters;
	}

	public static List<Parameter> getOptimizableParameterList(IBase base) {
		return getOptimizableParameterList(base, true);
	}

	/**
	 * Check if the given optimizable object and its children are optimizable
	 * 
	 * @param base the base obtimizable instance 
	 * @param fullTree check if the whole parameter hierarchy is optimizable
	 * @return true if the given object is optimizable, false otherwise
	 */
	public static boolean isOptimizable(IBase base, boolean fullTree) {
		if (base instanceof Parameter)
			return ((Parameter) base).isOptimizable() && ((Parameter) base).isEnabled();
		for (IBase node : base.getNodes()) {
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
	public static void setOptimizable(IBase base, boolean optimizable) {
		if (base instanceof Parameter)
			((Parameter) base).setOptimizable(optimizable);
		for (IBase node : base.getNodes())
			setOptimizable(node, optimizable);
	}
	
	/**
	 * Check if the given optimizable object and its children are enabled
	 * 
	 * @param opt the base obtimizable instance 
	 * @param fullTree check if the whole parameter hierarchy is enabled
	 * @return true if the given object is enabled, false otherwise
	 */
	public static boolean isEnabled(IBase base, boolean fullTree) {
		if (base instanceof Parameter)
			return ((Parameter) base).isEnabled();
		for (IBase node : base.getNodes()) {
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
	public static void setEnabled(IBase base, boolean enabled) {
		if (base instanceof Parameter)
			((Parameter) base).setEnabled(enabled);
		for (IBase node : base.getNodes())
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
	public static Parameter getParameterByName(IBase base, String name, boolean fullTree) {
		for(Parameter parameter : getParameterList(base, fullTree))
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
	public static Parameter getParameterByName(IBase base, String name) {
		return getParameterByName(base, name, false);
	}

	/**
	 *  Search the parent ParameterNode for a child parameter with the given ID
	 *  
	 * @param base the parent node to search for the given parameter
	 * @param id the parameter's ID
	 * @param fullTree if true, descend the full ParameterNode tree
	 * @return the parameter with the given ID or null if it doesn't exist
	 */
	public static Parameter getParameterByID(IBase base, String id, boolean fullTree) {
		for(Parameter parameter : getParameterList(base, fullTree))
			if (parameter.getUID().equals(id))
				return parameter;
		return null;
	}

}
