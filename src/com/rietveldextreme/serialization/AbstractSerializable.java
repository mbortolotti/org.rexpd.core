package com.rietveldextreme.serialization;

import java.util.UUID;



public class AbstractSerializable implements Serializable {
	
	
	private String type;
	private String label;
	private String ID = null;
	
	public AbstractSerializable() {
		createUID();
		setType("");
		setLabel("");
	}
	
	public String getType() {
		return type;
	}

	public void setType(String t) {
		type = t;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String l) {
		label = l;
	}
	
	public String getUID() {
		if (ID == null || ID.equals(""))
			createUID();
		return ID;
	}
	
	public void setUID(String uid) {
		ID = uid;
	}
	
	private void createUID() {
		ID = UUID.randomUUID().toString();
	}

}
