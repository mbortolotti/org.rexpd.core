package com.rietveldextreme.optimization;

import java.util.ArrayList;
import java.util.List;

import com.rietveldextreme.serialization.AbstractBase;
import com.rietveldextreme.serialization.IBase;


public abstract class AbstractOptimizable extends AbstractBase implements Optimizable {
	
	public AbstractOptimizable() {
		this(null);
	}
	
	public AbstractOptimizable(IBase parent) {
		super(parent);
	}

//	@Override
//	public List<? extends Optimizable> getNodes() {
//		List<Optimizable> temp = new ArrayList<Optimizable>();
//		temp.addAll(nodes);
//		return temp;
//	}

}
