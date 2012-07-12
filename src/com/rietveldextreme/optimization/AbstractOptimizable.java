package com.rietveldextreme.optimization;

import com.rietveldextreme.serialization.AbstractBase;
import com.rietveldextreme.serialization.IBase;


public abstract class AbstractOptimizable extends AbstractBase implements Optimizable {
	
	public AbstractOptimizable() {
		this(null);
	}
	
	public AbstractOptimizable(IBase parent) {
		super(parent);
	}
	
}
