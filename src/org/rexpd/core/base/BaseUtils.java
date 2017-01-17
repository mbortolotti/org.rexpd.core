package org.rexpd.core.base;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseUtils {

	/**
	 * Should be put in IBase
	 * @param base
	 * @param type
	 * @return
	 */
	public static final List<? extends IBase> getNodes(IBase base, Class<? extends IBase> type, boolean fulltree, boolean updateParents, boolean skipDisabled) {
		List<IBase> nodes = new ArrayList<IBase>();
		for (IBase child : base.getNodes()) {
			/** skip links as we are only interested in real instances **/
			if (child instanceof BaseLink)
				continue;
			if (type.isAssignableFrom(child.getClass()) && (!skipDisabled || child.isEnabled())) {
				if (updateParents)
					child.setParentNode(base);
				nodes.add(child);
			}
			if (fulltree)
				nodes.addAll(getNodes(child, type, fulltree, updateParents, skipDisabled));
		}
		return nodes;
	}
	
	/**
	 * Utility method to get all children of a specific IBase object using default arguments
	 * @param base IBase parent object
	 * @param type class type to llok for
	 * @return
	 */
	public static final List<? extends IBase> getNodes(IBase base, Class<? extends IBase> type) {
		return getNodes(base, type, true, true, true);
	}

	/**
	 * Should be put in IBase
	 * @param base
	 * @param type
	 * @return
	 */
	public static final List<BaseLink<IBase>> getLinks(IBase base, Class<? extends IBase> type, boolean fulltree) {
		List<BaseLink<IBase>> nodes = new ArrayList<BaseLink<IBase>>();
		for (IBase child : base.getNodes()) {
			if (child instanceof BaseLink) {
				if (type.isAssignableFrom(((BaseLink<?>) child).getBaseType()))
					nodes.add((BaseLink<IBase>) child);
			}
			if (fulltree)
				nodes.addAll(getLinks(child, type, fulltree));
		}
		return nodes;
	}
	
	/**
	 * Should be put in IBase
	 * @param base
	 * @param type
	 * @return
	 */
	public static final List<BaseLink<IBase>> getLinks(IBase base, String linkedID, boolean fulltree) {
		List<BaseLink<IBase>> nodes = new ArrayList<BaseLink<IBase>>();
		for (IBase child : base.getNodes()) {
			if (child instanceof BaseLink) {
				if (((BaseLink<?>) child).getLinkID().equals(linkedID))
					nodes.add((BaseLink) child);
			}
			if (fulltree)
				nodes.addAll(getLinks(child, linkedID, fulltree));
		}
		return nodes;
	}

	/**
	 * Should be put in IBase
	 * @param base
	 * @param type
	 * @return
	 */
	public static final IBase getNodeByID(IBase base, String ID, boolean fulltree) {
		for (IBase child : base.getNodes()) {
			if (child.getUID().equals(ID))
				return child;
			if (fulltree) {
				IBase node = getNodeByID(child, ID, fulltree);
				if (node != null)
					return node;
			}
		}
		return null;
	}
	
	/**
	 * Should be put in IBase
	 * @param base
	 * @param type
	 * @return
	 */
	public static final IBase getNodeByLabel(IBase base, String label, boolean fulltree) {
		for (IBase child : base.getNodes()) {
			if (child.getLabel().equals(label))
				return child;
			if (fulltree) {
				IBase node = getNodeByID(child, label, fulltree);
				if (node != null)
					return node;
			}
		}
		return null;
	}
	
	public static final IBase findParent(IBase base, IBase child) {
		if (base == child)
			return null;
		for (IBase node : base.getNodes()) {
			/** check if it is a direct child of the base object **/
			if (node == child)
				return base;
			/** else descend each subnode hierarchy **/
			IBase parent = findParent(node, child);
			if (parent != null)
				return parent;
		}
		return null;
	}

}
