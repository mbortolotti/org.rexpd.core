package org.rexpd.core.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public abstract class AbstractBase implements IBase {

	private String type = "";
	private String label = "";
	private UUID UID = null;
	private boolean enabled = true;
	private boolean visible = true; // TODO - consider pulling up to IBase

	private IBase parentNode = null;
	protected List<IBase> childNodes = null;

	public AbstractBase() {
		this(null);
	}

	public AbstractBase(IBase p) {
		parentNode = p;
		childNodes = new ArrayList<IBase>();
	}

	@Override
	public String getClassID() {
		return getClass().getSimpleName();
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String t) {
		type = t;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String l) {
		label = l;
	}

	@Override
	public String getUID() {
		if (UID == null)
			createUID();
		return UID.toString();
	}

	@Override
	public void setUID(String uid) {
		UID = UUID.fromString(uid);
	}

	public void createUID() {
		UID = UUID.randomUUID();
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean vis) {
		visible = vis;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean en) {
		enabled = en;
		// TODO - it is probably better to manage sub-elements enablement state from the GUI **/
//		/** Recursively set enablement state for all sub-nodes but parameters **/
//		for (IBase node : getNodes())
//			if (!(node instanceof Parameter))
//				node.setEnabled(en);
	}

	@Override
	public IBase copy() throws InstantiationException {
		try {
			IBase ccopy = null;
			for (Constructor<?> constr : getClass().getConstructors()) {
				/** look for no-argument constructor first **/
				if (constr.getParameterTypes().length == 0) {
					ccopy = (IBase) constr.newInstance();
					break;
				}
				/** alternatively, look for the IBase parent constructor **/ 
				else if (constr.getParameterTypes().length == 1 && IBase.class.isAssignableFrom(constr.getParameterTypes()[0])) {
					ccopy = (IBase) constr.newInstance(getParentNode());
					break;
				}
			}			
			if (ccopy == null)
				throw new InstantiationException("Class " + getClass().getName() + " has no usable constructors!");
			
			/** copy all original instance attributes except UID which is automatically generated **/ 
			ccopy.setParentNode(getParentNode());
			ccopy.setType(new String(getType()));
			ccopy.setLabel(new String(getLabel()));
			ccopy.setEnabled(isEnabled());
			for (IBase base : getNodes())
				addNode(base.copy());
			return ccopy;
		} catch (IllegalAccessException e) {
			throw new InstantiationException(e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new InstantiationException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new InstantiationException(e.getMessage());
		} catch (SecurityException e) {
			throw new InstantiationException(e.getMessage());
		}
	}

	@Override 
	public IBase getParentNode() {
		return parentNode;
	}

	@Override
	public void setParentNode(IBase parent) {
		parentNode = parent;
	}

	@Override
	public void addNode(IBase node) {
		childNodes.add(node);
	}
	@Override
	public void addNode(int position, IBase node) {
		childNodes.add(position, node);
	}

	@Override
	public void removeNode(IBase node) {
		childNodes.remove(node);
	}

	@Override
	public List<? extends IBase> getNodes() {
		List<IBase> temp = new ArrayList<IBase>();
		temp.addAll(childNodes);
		return temp;
	}

}
