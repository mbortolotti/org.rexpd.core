package org.rexpd.core.base;

import java.util.List;

/**
 * @author mauro
 * 
 * Acts as a dynamic allocator for an IBase object of a certain type, 
 * which can be replaced at runtime with another IBase instance
 * as long as the latter is assignable from the original object
 *
 */
public class BaseDynamic extends AbstractBase {

	private BaseFactory baseFactory = null;
	private IBase baseInstance = null;
	
	public BaseDynamic(BaseFactory factory) {
		baseFactory = factory;
		if (baseFactory.getElements().length != 0) {
			IBase base = baseFactory.create(baseFactory.getElements()[0]);
			if (base != null) {
				baseInstance = base;
				System.out.println("BaseDynamic: base.getClass().getSimpleName(): " + base.getClass().getSimpleName());
			}
		}
		else
			System.out.println("BaseDynamic: cannot instantiate factory object!");
	}

	public String[] getItems() {
		return baseFactory.getElements();
	}
	
	public IBase setActiveItem(String type) {
		System.out.println();
		System.out.println("Setting " + type + " item in " + getClassID() + " factory... ");
		for (String string : baseFactory.getElements()) {
			if (type.equals(string)) {
				System.out.println("Selected " + type + " type in " + getClassID() + " factory!");
				baseInstance = baseFactory.create(type);
				return baseInstance;
			}
		}
		System.out.println("ProxyBase.setActiveItem(type): error: type " + type + " not found!");
		return null;
	}

	public IBase getActiveItem() {
		return baseInstance;
	}

	@Override
	public String getClassID() {
		return baseFactory.getClassID();
	}

	@Override
	public String getType() {
		return getActiveItem().getType();
	}

	@Override
	public String getLabel() {
		return getActiveItem().getLabel();
	}

	@Override
	public void setLabel(String l) {
		getActiveItem().setLabel(l);
	}

	@Override
	public String getUID() {
		return getActiveItem().getUID();
	}

	@Override
	public void addNode(IBase node) {
		getActiveItem().addNode(node);
	}

	@Override
	public List<? extends IBase> getNodes() {
		return getActiveItem().getNodes();
	}


}
