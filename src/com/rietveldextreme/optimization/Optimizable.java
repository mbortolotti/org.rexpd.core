package com.rietveldextreme.optimization;

import java.util.List;

import com.rietveldextreme.serialization.Serializable;


public interface Optimizable extends Serializable {
	
	public Optimizable getParentNode();
	
	public List<? extends Optimizable> getNodes();

}
