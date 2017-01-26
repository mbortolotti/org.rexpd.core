package org.rexpd.core.base;


/**
 * Factory method interface for instantiating IBase objects
 *
 * @param <T> generic IBase type 
 */
public interface BaseFactory<T extends IBase> {
	
	public String[] getElements();
	
	public T create(String type) throws InstantiationException, IllegalAccessException;

	public String getClassID();

}
