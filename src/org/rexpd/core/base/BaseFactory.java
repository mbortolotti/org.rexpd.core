package org.rexpd.core.base;

public interface BaseFactory {
	
	public String[] getElements();
	
	public IBase create(String type);

	public String getClassID();

}
