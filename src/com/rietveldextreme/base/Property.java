package com.rietveldextreme.base;

import java.util.Collections;
import java.util.List;


public class Property extends AbstractBase {

	public static final String PROPERTY_TYPE = "Property";
	
	private String value = "";

	public Property(IBase parent, String label, String value) {
		super(parent);
		setLabel(label);
		setValue(value);
	}

	@ Override
	public String getType() {
		return PROPERTY_TYPE;
	}

	@ Override
	public List<? extends IBase> getNodes() {
		return Collections.emptyList();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String v) {
		value = v;
	}

}
