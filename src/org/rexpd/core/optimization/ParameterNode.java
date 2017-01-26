package org.rexpd.core.optimization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.rexpd.core.base.AbstractBase;
import org.rexpd.core.base.IBase;

public class ParameterNode extends AbstractBase {

	public static final String PARAMETER_NODE = "Parameter Node";

	/** Local base node object - needed to refresh the tree **/
	private IBase baseNode = null;
	/** Needs to manage its local nodes list **/ //- ??
	private List<IBase> nodeList = null;

	public ParameterNode(IBase base) {
		super(base.getParentNode());
		baseNode = base;
		nodeList = new ArrayList<IBase>();
		setUID(base.getUID());
		setLabel(base.getLabel());
		setEnabled(base.isEnabled());
		nodeList.addAll(cloneNodeTree(base));
		//buildNodeTree();
		
//		for (IBase base : parent.getNodes()) {
//			System.out.println("ParameterNode constructor: base = " + base.getLabel());
//			if (base instanceof Parameter)
//				addNode(((Parameter) base).cloneTo());
//			else
//				addNode(new ParameterNode(base));
//		}
	}

	@Override
	public String getType() {
		return PARAMETER_NODE;
	}

	@Override
	public void addNode(IBase node) {
		nodeList.add(node);
	}

	@Override
	public List<? extends IBase> getNodes() {
		List<IBase> temp = new ArrayList<IBase>();
		temp.addAll(nodeList);
		return temp;
	}

//	@Deprecated
//	private void buildNodeTree() {
//		nodeList.clear();
//		for (IBase base : baseNode.getNodes()) {
//			/** Avoid recursion - is this necessary? **/
//			if (base instanceof ParameterNode)
//				continue;
//			System.out.println("ParameterNode constructor: base = " + base.getLabel());
//			if (base instanceof Parameter)
//				nodeList.add(((Parameter) base).cloneTo());
//			else
//				nodeList.add(new ParameterNode(base));
//		}
//	}
	
	private static List<IBase> cloneNodeTree(IBase base) {
		List<IBase> nodes = new ArrayList<IBase>();
		for (IBase node : base.getNodes()) {
			/** Avoid recursion - is this necessary? **/
			if (node instanceof ParameterNode)
				continue;
			else if (node instanceof Parameter)
				nodes.add(((Parameter) node).cloneTo());
			else
				nodes.add(new ParameterNode(node));
		}
		return nodes;
	}

	/**
	 * refresh the node tree
	 */
	public void refreshSimple() {

		System.out.println("refreshSimple: baseNode = " + baseNode);
		nodeList.clear();
		nodeList.addAll(cloneNodeTree(baseNode));
		
//		/** retrieve the current parameters list **/
//		List<Parameter> currentParameterList = Optimizations.getParameters(this);
//		/** updates parameter nodes tree **/
//		updateTree();
//
//		List<Parameter> updatedParameterList = Optimizations.getParameters(getParentNode());
//
//		Iterator<Parameter> currentListIterator = currentParameterList.iterator();
//		Iterator<Parameter> updatedListIterator = updatedParameterList.iterator();
//
//		while (updatedListIterator.hasNext()) {
//			while (currentListIterator.hasNext()) {
//				Parameter currentParameter = currentListIterator.next();
//				Parameter updatedParameter = updatedListIterator.next();
//				if (currentParameter.getUID().equals(updatedParameter.getUID())) {
//					System.out.println(currentParameter.getLabel() + ": found match!!");
//				}
//			}
//		}
		
	}

	private void refreshFull(IBase base) {
		/** backup the old node tree **/
		List<IBase> oldNodeList = new ArrayList<IBase>();
		for (IBase node : nodeList)
			oldNodeList.add(node);
		
		/** update base node instance **/
		baseNode = base;
		/** refresh node tree **/
		refreshSimple();

		Iterator<? extends IBase> newListIt = nodeList.iterator();

		while (newListIt.hasNext()) {
			IBase newNode = newListIt.next();
			/** check if the node is already present in the current node list **/
			Iterator<? extends IBase> oldListIt = oldNodeList.iterator();
			while (oldListIt.hasNext()) {
				IBase oldNode = oldListIt.next();
				if (oldNode.getUID().equals(newNode.getUID())) {
					// TODO update enablement state etc
					System.out.println(oldNode.getLabel() + ": found match!!");
					/** if the node is a Parameter, update its enablement state **/
					if (newNode instanceof Parameter && oldNode instanceof Parameter)
						((Parameter) newNode).setOptimizable(((Parameter) oldNode).isOptimizable());
					/** if the node is a ParameterNode, perform a recursive call **/
					if (newNode instanceof ParameterNode && oldNode instanceof ParameterNode)
						((ParameterNode) newNode).refreshFull();
					/** when a node is found it is removed from the current list **/
					oldListIt.remove();
					//updatedNodesIt.remove();
					break;
				}
			}
			/** TODO: check if we have new ParameterNodes to refresh ? **/
		}
	}
	
	public void refreshFull() {
		
		/** backup the old node tree **/
		List<IBase> oldNodeList = new ArrayList<IBase>();
		for (IBase node : nodeList)
			oldNodeList.add(node);
		
		/** refresh node tree **/
		refreshSimple();

		Iterator<? extends IBase> newListIt = nodeList.iterator();

		while (newListIt.hasNext()) {
			IBase newNode = newListIt.next();
			/** check if the node is already present in the current node list **/
			Iterator<? extends IBase> oldListIt = oldNodeList.iterator();
			while (oldListIt.hasNext()) {
				IBase oldNode = oldListIt.next();
				if (oldNode.getUID().equals(newNode.getUID())) {
					// TODO update enablement state etc
					System.out.println(oldNode.getLabel() + ": found match!!");
					/** if the node is a Parameter, update its enablement state **/
					if (newNode instanceof Parameter && oldNode instanceof Parameter)
						((Parameter) newNode).setOptimizable(((Parameter) oldNode).isOptimizable());
					/** if the node is a ParameterNode, perform a recursive call **/
					if (newNode instanceof ParameterNode && oldNode instanceof ParameterNode)
						((ParameterNode) newNode).refreshFull();
					/** when a node is found it is removed from the current list **/
					oldListIt.remove();
					//updatedNodesIt.remove();
					break;
				}
			}
			/** TODO: check if we have new ParameterNodes to refresh ? **/
		}

		/** Remove remaining old nodes and add the updated ones **/
//		nodeList.clear();
//		for (IBase node : updatedList)
//			nodeList.add(node);

		// not needed
		//childNodes.clear();
		//		for (IBase base : getParentNode().getNodes()) {
		//			System.out.println("ParameterNode constructor: base = " + base.getLabel());
		//			if (base instanceof Parameter)
		//				childNodes.add(((Parameter) base).cloneTo());
		//			else
		//				childNodes.add(new ParameterNode(base));
		//		}

	}

}

