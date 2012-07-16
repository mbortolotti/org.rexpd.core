package com.rietveldextreme.serialization;

import java.util.List;

/**
 * @author mauro
 * 
 * Acts as a proxy for an IBase object of a certain type, 
 * which can be replaced at runtime
 *
 */
public class BaseProxy extends AbstractBase {
	
	private IBase baseInstance = null;
	
	public BaseProxy(String type) {
		setType(type);
	}
	
	public void setBase(IBase base) {
		if (base.getType().equals(getType()))
			baseInstance = base;
		// TODO should raise exception otherwise
	}
	
	public IBase getBase() {
		return baseInstance;
	}

	@Override
	public String getLabel() {
		return baseInstance.getLabel();
	}

	@Override
	public void setLabel(String l) {
		baseInstance.setLabel(l);
	}

	@Override
	public void addNode(IBase node) {
		baseInstance.addNode(node);
	}

	@Override
	public List<? extends IBase> getNodes() {
		return baseInstance.getNodes();
	}
	

}
