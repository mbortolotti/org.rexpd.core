package com.rietveldextreme.serialization;

import org.w3c.dom.Element;



public abstract class SerializableIO {

	public static final String UID_TAG = "id";
	public static final String LABEL_TAG = "name";

	public static void loadXML(Serializable object, Element element) {
		String UID = element.getAttribute(UID_TAG);
		String Label = element.getAttribute(LABEL_TAG);
		if (!UID.equals("")) object.setUID(UID);
		if (!Label.equals("")) object.setLabel(Label);
	}

	public static Element saveXML(Serializable object, Element element) {
		element.setAttribute(UID_TAG, object.getUID());
		element.setAttribute(LABEL_TAG, object.getLabel());
		return element;
	}

}
