package org.rexpd.core.base;

import java.util.List;



public interface IBase {
	
	public String getClassID();
	
	public String getType();
	public void setType(String type);
	
	public String getLabel();
	public void setLabel(String label);
	
	public String getUID();
	public void setUID(String id);
	
	public boolean isEnabled();
	public void setEnabled(boolean en);

	public boolean isVisible();
	public void setVisible(boolean vis);
	
	public IBase copy() throws InstantiationException;
//	public IBase getLink();
//	public void setLink(IBase link);
	
	/** TODO use generic bounded parameters **/
	public IBase getParentNode();
	public void setParentNode(IBase parent);
	public List<? extends IBase> getNodes();
	public void addNode(IBase node);
	public void addNode(int position, IBase node);
	public void removeNode(IBase node);

}
