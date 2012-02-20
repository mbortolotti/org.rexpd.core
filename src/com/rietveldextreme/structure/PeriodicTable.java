/*
 * AtomTable.java created on 5-mar-2004 Mesiano
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

package com.rietveldextreme.structure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.rietveldextreme.serialization.XML_IO;
import com.rietveldextreme.core.Config;

/**
 * @version $Revision: 1.00 $, $Date: 5-mar-2004 14.55.11
 * @author Mauro Bortolotti
 * @since JDK1.1
 */
public class PeriodicTable {

	static final String PERIODIC_TABLE_FILE = "data" + File.separator + "elements.xml";
	static final String ATOM_TAG = "ATOM";
	static final String SYMBOL_TAG = "SYMBOL";
	static final String NAME_TAG = "NAME";
	static final String ATOMIC_NUMBER_TAG = "ATOMIC_NUMBER";
	static final String ATOMIC_MASS_TAG = "MASS";
	static final String ATOMIC_RADIUS_TAG = "RADIUS";
	static final String ATOM_COLOR_RED_TAG = "COLOR_R";
	static final String ATOM_COLOR_GREEN_TAG = "COLOR_G";
	static final String ATOM_COLOR_BLUE_TAG = "COLOR_B";

	static ArrayList<ChemicalElement> ElementTable = new ArrayList<ChemicalElement>();


	static {
		try {
			String periodicTablePath = Config.getFullPath(PERIODIC_TABLE_FILE);
			XML_IO reader = new XML_IO(periodicTablePath);
			reader.parseDocument();
			NodeList elementList = reader.getDocument().getElementsByTagName(ATOM_TAG);
			System.out.println("PeriodicTable elements: " + elementList.getLength());
			for (int i = 0; i < elementList.getLength(); i++) {
				Element atom_element = (Element) elementList.item(i);
				ChemicalElement chemElem = new ChemicalElement();
				chemElem.symbol = XML_IO.getElementTextByTag(atom_element, SYMBOL_TAG);
				chemElem.name = XML_IO.getElementTextByTag(atom_element, NAME_TAG);
				chemElem.atomic_number = Integer.parseInt(XML_IO.getElementTextByTag(atom_element, ATOMIC_NUMBER_TAG));
				chemElem.mass = Double.parseDouble(XML_IO.getElementTextByTag(atom_element, ATOMIC_MASS_TAG));
				chemElem.radius = Double.parseDouble(XML_IO.getElementTextByTag(atom_element, ATOMIC_RADIUS_TAG));
				chemElem.RGBColor[0] = Integer.parseInt(XML_IO.getElementTextByTag(atom_element, ATOM_COLOR_RED_TAG));
				chemElem.RGBColor[1] = Integer.parseInt(XML_IO.getElementTextByTag(atom_element,ATOM_COLOR_GREEN_TAG));
				chemElem.RGBColor[2] = Integer.parseInt(XML_IO.getElementTextByTag(atom_element, ATOM_COLOR_BLUE_TAG));
				ElementTable.add(chemElem);
			}
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int getElementsNumber() {
		return ElementTable.size();
	}

	public static ChemicalElement getElement(int position) {
		return ElementTable.get(position);
	}

	public static int getZNumberBySymbol(String symbol) {
		ChemicalElement element = getElementBySymbol(symbol);
		if (element == null)
			return 1;
		return element.atomic_number;
	}

	public static ChemicalElement getElementByZNumber(int Z) {
		for (Iterator<ChemicalElement> it = ElementTable.iterator(); it.hasNext();) {
			ChemicalElement element = it.next();
			if (element.atomic_number == Z)
				return element;
		}
		return null;
	}

	public static ChemicalElement getElementBySymbol(String symbol) {
		symbol = symbol.trim();
		if (symbol.lastIndexOf("+0") != -1)
			symbol = symbol.substring(0, symbol.lastIndexOf("+0"));
		if (symbol.lastIndexOf("0") != -1)
			symbol = symbol.substring(0, symbol.lastIndexOf("0"));
		for (int ne = 0; ne < ElementTable.size(); ne++) {
			ChemicalElement element = ElementTable.get(ne);
			if (element.symbol.toLowerCase().equals(symbol.toLowerCase()))
				return element;
		}
		return null;
	}

	public static ChemicalElement parseElement(String symbol) {
		ChemicalElement element = null;
		while ((element = PeriodicTable.getElementBySymbol(symbol)) == null && symbol.length() > 1)
			symbol = symbol.substring(0, symbol.length() - 1);
		return element;
	}

}


