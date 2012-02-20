package com.rietveldextreme.serialization;

public interface Serializer {
	
	void save(SerializableNode node);
	void load(SerializableNode node);

}
