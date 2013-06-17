package org.rexpd.core.base;

import java.util.List;



public interface IBase {
	
	public String getClassID();
	
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
	public void addNode(IBase node);
	
//	public boolean canAddNode(IBase node);
//	public void deleteNode(IBase node);
//	public boolean canDeleteNode(IBase node);

}
