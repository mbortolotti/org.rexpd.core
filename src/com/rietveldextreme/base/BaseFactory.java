package com.rietveldextreme.base;

public interface BaseFactory {
	
	public String[] getElements();
	
	public IBase create(String type);

	public String getClassID();

}
