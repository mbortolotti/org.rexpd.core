package org.rexpd.core.base;

public interface Serializer {
	
	void save(SerializableNode node);
	void load(SerializableNode node);

}
