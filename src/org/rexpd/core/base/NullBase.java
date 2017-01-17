package org.rexpd.core.base;

import java.util.Collections;
import java.util.List;

public class NullBase extends AbstractBase {
	
	Class<? extends IBase> baseType;
	
	public NullBase(Class<? extends IBase> type) {
		super(null);
		baseType = type;
	}

	public Class<?> getBaseType() {
		return baseType;
	}
	
	@Override
	public String getClassID() {
		return baseType.getSimpleName();
	}

	@Override
	public void addNode(IBase node) {
		// do nothing!!
	}

	@Override
	public List<? extends IBase> getNodes() {
		return Collections.emptyList();
	}

}
