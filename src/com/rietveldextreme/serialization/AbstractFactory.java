package com.rietveldextreme.serialization;

public interface AbstractFactory {
	
	public IBase create(String type);

}
