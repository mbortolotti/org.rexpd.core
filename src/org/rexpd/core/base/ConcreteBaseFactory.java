package org.rexpd.core.base;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConcreteBaseFactory<T extends IBase> implements BaseFactory<T> {

	private String classID;
	private static final Map<String, Class<? extends IBase>> factoryMap = 
			new LinkedHashMap<String, Class<? extends IBase>>();
	
	public ConcreteBaseFactory(String ID) {
		classID = ID;
	}
	
	public void add(String type, Class<? extends IBase> clazz) {
		factoryMap.put(type, clazz);
	}

	@Override
	public String[] getElements() {
		// HACK - consider a more elegant solution...
		return factoryMap.keySet().toArray(new String[0]);
	}

	@Override
	public T create(String type) throws InstantiationException, IllegalAccessException {
		Class<? extends IBase> model = factoryMap.get(type);
		if (model != null)
			return (T) model.newInstance();
		throw new InstantiationException("Unable to create object of type " + type);
	}

	@Override
	public String getClassID() {
		return classID;
	}

}
