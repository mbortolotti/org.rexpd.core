package com.rietveldextreme.base;

public interface Serializer {
	
	void save(SerializableNode node);
	void load(SerializableNode node);

}
