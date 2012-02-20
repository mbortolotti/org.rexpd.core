package com.rietveldextreme.serialization;



public interface Serializable {
	
	public String getType();
	public void setType(String type);
	
	public String getLabel();
	public void setLabel(String name);
	
	public String getUID();
	public void setUID(String id);

}
