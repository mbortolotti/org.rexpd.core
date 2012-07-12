package com.rietveldextreme.serialization;

import java.util.List;



public interface IBase {
	
	public String getType();
	public void setType(String type);
	
	public String getLabel();
	public void setLabel(String name);
	
	public String getUID();
	public void setUID(String id);
	
	public boolean isEnabled();
	public void setEnabled(boolean en);
	
	public IBase getParentNode();
	public List<? extends IBase> getNodes();

}
