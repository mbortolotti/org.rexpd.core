package com.rietveldextreme.serialization;

import org.w3c.dom.Element;



public abstract class BaseIO {

	public static final String UID_TAG = "id";
	public static final String TYPE_TAG = "type";
	public static final String NAME_TAG = "name";
	public static final String LABEL_TAG = "label";

	public static void loadXML(IBase object, Element element) {
		String UID = element.getAttribute(UID_TAG);
		String name = element.getAttribute(NAME_TAG);
		String label = element.getAttribute(LABEL_TAG);
		if (!UID.equals("")) object.setUID(UID);
		if (!name.equals("")) object.setLabel(name);
		if (!label.equals("")) object.setLabel(label);
	}

	public static Element saveXML(IBase object, Element element) {
		element.setAttribute(UID_TAG, object.getUID());
		element.setAttribute(TYPE_TAG, object.getType());
		element.setAttribute(LABEL_TAG, object.getLabel());
		element.setAttribute(NAME_TAG, object.getLabel());
		return element;
	}

}
