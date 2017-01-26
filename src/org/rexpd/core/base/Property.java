package org.rexpd.core.base;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;


public class Property<T> extends AbstractBase {

	public static final String PROPERTY_TYPE = "Property";
	
	private T valueT;
	
	public Property(IBase parent, T value, String label) {
		super(parent);
		setLabel(label);
		setValueT(value);
	}
	
	@ Override
	public String getType() {
		return PROPERTY_TYPE;
	}

	@ Override
	public List<? extends IBase> getNodes() {
		return Collections.emptyList();
	}
	
	public T getValueT() {
		return valueT;
	}

	public void setValueT(T value) {
		valueT = value;
	}
	
	@Override
	public String toString() {
		return valueT.toString();
	}
	
	//TODO: clean up / group exceptions together
	public void fromString(String value) {
		try {
			valueT = (T) valueT.getClass().getConstructor(new Class[] {String.class }).newInstance(value);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
