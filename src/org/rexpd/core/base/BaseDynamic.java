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
public class BaseDynamic<T extends IBase> extends AbstractBase {

	private BaseFactory<T> baseFactory = null;
	private T baseInstance = null;
	private String baseType = null;

	public BaseDynamic(BaseFactory<T> factory) {
		baseFactory = factory;
		if (baseFactory.getElements().length != 0) {
			String type = baseFactory.getElements()[0];
			T base;
			try {
				base = baseFactory.create(type);
				if (base != null) {
					baseInstance = base;
					baseType = type;
				}
			} catch (InstantiationException | IllegalAccessException e) {
				System.out.println("BaseDynamic: cannot instantiate factory object!");
				e.printStackTrace();
			}
		}
		else
			System.out.println("BaseDynamic: cannot instantiate factory object!");
	}

	public String[] getItemTypes() {
		return baseFactory.getElements();
	}

	public T getActiveItem() {
		return baseInstance;
	}

	public T setActiveItem(String type) {
		/** check if requested type is different from that of active instance **/
		if (!(type.equals(baseType))) {
			for (String string : baseFactory.getElements()) {
				if (type.equals(string)) {
					try {
						baseInstance = baseFactory.create(type);
						baseType = type;
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return baseInstance;
	}

	public T createItem(String type) throws InstantiationException, IllegalAccessException {
		return baseFactory.create(type);
	}

	public String getActiveType() {
		return baseType;
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
