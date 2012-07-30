/*
 * XMLUtils.java created on 25-feb-2004 Mesiano
 *
 * Copyright (c) 2004 Mauro Bortolotti All Rights Reserved.
 *
 * This software is the research result of Luca Lutterotti and it is
 * provided as it is as confidential and proprietary information.
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with the author.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */

package com.rietveldextreme.base;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * @version $Revision: 1.00 $, $Date: 25-feb-2004 16.35.24
 * @author Mauro Bortolotti
 * @since JDK1.1
 */

public class XML_IO {

	public static final String NAME_TAG = "name";
	public static final String TYPE_TAG = "type";
	public static final String VALUE_TAG = "value";
	public static final String ACTIVE_TAG = "active";
	public static final String ENABLED_TAG = "enabled";

	private Document document = null;
	private String fileName = null;

	public XML_IO(String name) {
		fileName = name;
		// parse();
	}

	public void parseDocument() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse(fileName);
	}

	public void createDocument(String namespaceURI, String qualifiedName, DocumentType doctype)
			throws ParserConfigurationException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		DOMImplementation implementation = builder.getDOMImplementation();
		document = implementation.createDocument(namespaceURI, qualifiedName, doctype);
	}

	public void writeDocument() throws TransformerFactoryConfigurationError, TransformerException {
		Source source = new DOMSource(document);
		File file = new File(fileName);
		Result result = new StreamResult(file);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty("indent", "yes");
		transformer.transform(source, result);
	}

	public Document getDocument() {
		return document;
	}

	public static String getElementText(Element element) throws SAXException {
		NodeList list = element.getChildNodes();
		if (list == null)
			throw (new SAXException("XML Reader: getElementText: node list empty!"));
		Node node = list.item(0);
		if (node.getNodeType() != Node.TEXT_NODE)
			throw (new SAXException("XML Reader: getElementText: first node is not a text node!"));
		return ((Text) node).getData();
	}

	public static String getElementTextByTag(Element parent, String TAG) throws SAXException {
		NodeList elementList = parent.getElementsByTagName(TAG);
		if (elementList == null)
			throw (new SAXException("XML Reader: getElementTextByTag: node list empty!"));
		Element element = (Element) elementList.item(0);
		return getElementText(element);
	}

	/**
     * Returns the first descendant Element with the given tag name.
     * @param name The name of the tag to match on. The special value "*" 
     *   matches all tags.
     * @return The first matching Element node or null if not found.
     */
	public static Element getFirstElementByTagName(Element parent, String tag) {
		NodeList elementList = parent.getElementsByTagName(tag);
		if (elementList == null || elementList.getLength() == 0)
			return null;
		Node firstChild = elementList.item(0);
		if (firstChild instanceof Element)
			return (Element) firstChild;
		return null;
	}

	public void DisplayFeatures() {

		DOMImplementation implementation = document.getImplementation();
		System.out.println("Feature Core 2.0: " + implementation.hasFeature("Core", "2.0"));
		System.out.println("Feature XML 2.0: " + implementation.hasFeature("XML", "2.0"));
		System.out.println("Feature Views 2.0: " + implementation.hasFeature("Views", "2.0"));
		System.out.println("Feature StyleSheets 2.0: " + implementation.hasFeature("StyleSheets", "2.0"));
		System.out.println("Feature CSS 2.0: " + implementation.hasFeature("CSS", "2.0"));
		System.out.println("Feature CSS2 2.0: " + implementation.hasFeature("CSS2", "2.0"));
		System.out.println("Feature UIEvents 2.0: " + implementation.hasFeature("UIEvents", "2.0"));
		System.out.println("Feature MouseEvents 2.0: " + implementation.hasFeature("MouseEvents", "2.0"));
		System.out.println("Feature MutationEvents 2.0: " + implementation.hasFeature("MutationEvents", "2.0"));
		System.out.println("Feature HTMLEvents 2.0: " + implementation.hasFeature("HTMLEvents", "2.0"));
		System.out.println("Feature Range 2.0: " + implementation.hasFeature("Range", "2.0"));
		System.out.println("Feature Traversal 2.0: " + implementation.hasFeature("Traversal", "2.0"));

	}

}
