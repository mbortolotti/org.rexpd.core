/*
 * Created on Mar 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rietveldextreme.structure;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.rietveldextreme.core.Config;
import com.rietveldextreme.serialization.XML_IO;
import com.rietveldextreme.structure.SpaceGroup.CRYSTAL_SYSTEM;

/**
 * @author mauro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpaceGroupTable {

	private static final String SG_TABLE_FILE = "data" + File.separator + "spacegroups.xml";

	private static ArrayList<SpaceGroup> sg_entries;

	private static final String SG_ENTRY_TAG = "SPACE_GROUP";
	private static final String SG_NUMBER_TAG = "SG_NUMBER";
	private static final String SG_SCHOENFLIES_TAG = "SG_SCHOENFLIES";
	private static final String SG_HERMANN_MAUGUIN_TAG = "SG_HERMANN_MAUGUIN";
	private static final String SG_HERMANN_MAUGUIN_EXT_TAG = "SG_HERMANN_MAUGUIN_EXT";
	private static final String SG_HALL_TAG = "SG_HALL";

	static {
		try {
			String sgTablePath = Config.getFullPath(SG_TABLE_FILE);
			SpaceGroupTable.loadSpaceGroups(sgTablePath);
		} catch (ParserConfigurationException e) {
			System.err.println("Error while creating DocumentBuilder object");
			e.printStackTrace();
		} catch (SAXException e) {
			System.err.println("Error while parsing space group table file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Unable to open space group table file: " + SG_TABLE_FILE);
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Unknown error while parsing space group table file");
			e.printStackTrace();
		}
	}

	public static void loadSpaceGroups(String path) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File(path));
		Element root = (Element) document.getLastChild();
		NodeList sg_list = root.getElementsByTagName(SG_ENTRY_TAG);
		int n_spacegroups = sg_list.getLength();
		System.out.println("Loading space groups symbols... " + n_spacegroups + " space groups loaded");
		sg_entries = new ArrayList<SpaceGroup>();
		for (int nsg = 0; nsg < n_spacegroups; nsg++) {
			Element sgroup = (Element) sg_list.item(nsg);
			int itNumber = Integer.parseInt(XML_IO.getElementTextByTag(sgroup, SG_NUMBER_TAG));
			String hall = XML_IO.getElementTextByTag(sgroup, SG_HALL_TAG);
			String schoenflies = XML_IO.getElementTextByTag(sgroup, SG_SCHOENFLIES_TAG);
			String hm_symbols = XML_IO.getElementTextByTag(sgroup, SG_HERMANN_MAUGUIN_TAG);
			StringTokenizer hmtok = new StringTokenizer(hm_symbols, "=");
			List<String> hmSymbols = new ArrayList<String>();
			while (hmtok.hasMoreTokens())
				hmSymbols.add(hmtok.nextToken());
			String universal_hermann_mauguin = XML_IO.getElementTextByTag(sgroup, SG_HERMANN_MAUGUIN_EXT_TAG);

			SpaceGroup entry = new SpaceGroup(itNumber, hall, hmSymbols, universal_hermann_mauguin, schoenflies);
			sg_entries.add(entry);
		}
	}

	public static SpaceGroup findSpaceGroupByHallSymbol(String HallSymbol) {
		for (int nsg = 0; nsg < sg_entries.size(); nsg++)
			if (sg_entries.get(nsg).hall().equals(HallSymbol))
				return sg_entries.get(nsg);
		return null;
	}
	
	public static List<SpaceGroup> spaceGroupsList() {
		return Collections.unmodifiableList(sg_entries);
	}

	public static List<SpaceGroup> listBySymmetry(CRYSTAL_SYSTEM system) {
		List<SpaceGroup> groups = new ArrayList<SpaceGroup>();
		for (int nsg = 0; nsg < sg_entries.size(); nsg++)
			if (sg_entries.get(nsg).getCrystalSystem() == system)
				groups.add(sg_entries.get(nsg));
		return groups;
	}

	@Deprecated
	public static ArrayList<SpaceGroup> findSpaceGroupsByIntTableNumber(int IntTableNumber) {
		ArrayList<SpaceGroup> sglist = new ArrayList<SpaceGroup>();
		for (int nsg = 0; nsg < sg_entries.size(); nsg++)
			if (((SpaceGroup)sg_entries.get(nsg)).number() == IntTableNumber)
				sglist.add(sg_entries.get(nsg));
		return sglist;
	}

	@Deprecated
	public static ArrayList<SpaceGroup> findSpaceGroupsBySchoenfliesSymbol(String Schoenflies) {
		ArrayList<SpaceGroup> sglist = new ArrayList<SpaceGroup>();
		for (int nsg = 0; nsg < sg_entries.size(); nsg++)
			if (((SpaceGroup)sg_entries.get(nsg)).schoenflies().equals(Schoenflies))
				sglist.add(sg_entries.get(nsg));
		return sglist;
	}

	@Deprecated
	public static SpaceGroup findSpaceGroupsByHMSymbol(String HermannMauguin) {
		for (int nsg = 0; nsg < sg_entries.size(); nsg++) {
			List<String> hm_symbols = sg_entries.get(nsg).hmSymbols();
			for (int nhms = 0; nhms < hm_symbols.size(); nhms++)
				if ((hm_symbols.get(nhms)).equals(HermannMauguin))
					return sg_entries.get(nsg);
		}
		// deals with mistyped H-M entries
		for (int nsg = 0; nsg < sg_entries.size(); nsg++) {
			List<String> hm_symbols = sg_entries.get(nsg).hmSymbols();
			for (int nhms = 0; nhms < hm_symbols.size(); nhms++) {
				//System.out.println(hm_symbols.get(nhms).toLowerCase());
				//System.out.println(removeSpaces(HermannMauguin).toLowerCase());
				if (removeSpaces(hm_symbols.get(nhms)).toLowerCase().equals(removeSpaces(HermannMauguin).toLowerCase()))
					return sg_entries.get(nsg);
			}

		}
		return null;
	}

	@Deprecated
	public static SpaceGroup findSpaceGroupsByExtendedHMSymbol(String ExtendedHermannMauguin) {
		for (int nsg = 0; nsg < sg_entries.size(); nsg++)
			if ((sg_entries.get(nsg)).universal_hm().equals(ExtendedHermannMauguin))
				return sg_entries.get(nsg);
		for (int nsg = 0; nsg < sg_entries.size(); nsg++)
			if (removeSpaces((sg_entries.get(nsg)).universal_hm()).equals(ExtendedHermannMauguin))
				return sg_entries.get(nsg);
		return null;
	}

	@Deprecated
	public static String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = "";
		while (st.hasMoreElements())
			t += st.nextElement();
		return t;
	}

	public static void main(String[] args) {
		/*try {
			FileReader freader = new FileReader("sg.txt");
			BufferedReader breader = new BufferedReader(freader);
			PrintWriter writer = new PrintWriter(SG_TABLE_FILE_NEW);
			String line = null;
			int nsg = 0;
			writer.println("<?xml version=\"1.0\"?>");
			writer.println("<SPACE_GROUPS_TABLE>");
			while ((line = breader.readLine()) != null) {
				if (line.length() < 56)
					continue;
				String HMline = line.substring(26, 54).trim();
				StringTokenizer hmtok = new StringTokenizer(HMline, " =");
				String HM = hmtok.nextToken();
				if (hmtok.countTokens() > 0)
					HM = removeSpaces(hmtok.nextToken());
				SpaceGroup sg = findSpaceGroupsByExtendedHMSymbol(HM);
				if (sg != null) {
					writer.println("<SPACE_GROUP>");
					writer.println("\t<SG_NUMBER>" + sg.number + "</SG_NUMBER>");
					//writer.println(HM + ": sg found - Hall: " + sg.hall + " - " + nsg++);
					writer.println("\t<SG_SCHOENFLIES>" + sg.schoenflies + "</SG_SCHOENFLIES>");
					writer.println("\t<SG_HERMANN_MAUGUIN>" + sg.hermann_mauguin + " = " + HMline + "</SG_HERMANN_MAUGUIN>");
					writer.println("\t<SG_HERMANN_MAUGUIN_EXT>" + sg.extended_hermann_mauguin + "</SG_HERMANN_MAUGUIN_EXT>");
					writer.println("\t<SG_HALL>" + sg.hall + "</SG_HALL>");
					writer.println("</SPACE_GROUP>");
				}
				//System.out.println(HM + ": sg found - Hall: " + sg.hall + " - " + nsg++);
			}
			writer.println("</SPACE_GROUPS_TABLE>");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
