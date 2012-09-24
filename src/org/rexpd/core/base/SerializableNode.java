package org.rexpd.core.base;

import java.util.List;
import java.util.Map;

public class SerializableNode {

	private List<SerializableNode> children;
	private Map<String, String> properties;
	
	private String nodeName;
	private String nodeValue;

	public String getName() {
		return nodeName;
	}
	
	public void setName(String name) {
		nodeName = name;
	}
	
	public String getValue() {
		return nodeValue;
	}
	
	public void setValue(String value) {
		nodeValue = value;
	}
	
	public String getProperty(String property) {
		return properties.get(property);
	}
	
	public void setProperty(String property, String value) {
		properties.put(property, value);
	}

	public SerializableNode addChild() {
		SerializableNode node = new SerializableNode();
		children.add(node);
		return node;
	}
	
	public List<SerializableNode> getChildren() {
		return children;
	}
	
	public Map<String, String> getProperties() {
		return properties;
	}

}
