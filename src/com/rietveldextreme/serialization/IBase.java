package com.rietveldextreme.serialization;

import java.util.List;

import com.rietveldextreme.optimization.Optimizable;



public interface IBase {
	
	public String getType();
	public void setType(String type);
	
	public String getLabel();
	public void setLabel(String name);
	
	public String getUID();
	public void setUID(String id);
	
	public IBase getParentNode();
	
	public List<? extends Optimizable> getNodes();

}
