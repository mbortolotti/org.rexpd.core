/*
 * Created on 27-mag-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rietveldextreme.structure;

import java.util.ArrayList;
import java.util.List;

import com.rietveldextreme.utils.CCTBXNative;


/**
 * @author mauro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class SpaceGroup {
	
	public static final String SPACE_GROUP_TAG = "SpaceGroup";
	public static final String SPACE_GROUP_IT_TAG = "ITNumber";
	public static final String SPACE_GROUP_HM_TAG = "Hermann-Mauguin";
	public static final String SPACE_GROUP_HALL_TAG = "Hall";
	
	public static enum CRYSTAL_SYSTEM {
		TRICLINIC {
			@Override
			public String toString() { return "Triclinic"; }
		},
		MONOCLINIC {
			@Override
			public String toString() { return "Monoclinic"; }
		},
		ORTHOROMBIC {
			@Override
			public String toString() { return "Orthorombic"; }
		},
		TETRAGONAL {
			@Override
			public String toString() { return "Tetragonal"; }
		},
		TRIGONAL {
			@Override
			public String toString() { return "Trigonal"; }
		},
		HEXAGONAL {
			@Override
			public String toString() { return "Hexagonal"; }
		},
		CUBIC {
			@Override
			public String toString() { return "Cubic"; }
		};

		@Override
		public abstract String toString();
	}
	
	private int itNumber = 1;
	private String hall_symbol = " P 1";
	private List<String> hmSymbols = new ArrayList<String>();
	private String universal_hermann_mauguin = "P 1";
	private String schoenflies = "C1^1";
	
	
	public SpaceGroup(int number, String hall, List<String> hm_symbols, String uni_hm, String schoen) {
		itNumber = number;
		hall_symbol = hall;
		hmSymbols = hm_symbols;
		universal_hermann_mauguin = uni_hm;
		schoenflies = schoen;
	}
	
	public static final SpaceGroup fromHallSymbol(String hall) {
		return SpaceGroupTable.findSpaceGroupByHallSymbol(hall);
	}
	
	public static final SpaceGroup fromSymbol(String symbol) {
		if (symbol == null) 
			return null;
		String hall = CCTBXNative.sgSymbol2Hall(symbol);
		if (hall == null) 
			return null;
		return SpaceGroupTable.findSpaceGroupByHallSymbol(hall);
	}
	
	public static final SpaceGroup fromITNumber(int ITNumber, String extension) {
		String hall = CCTBXNative.itNumber2Hall(ITNumber, extension);
		return SpaceGroupTable.findSpaceGroupByHallSymbol(hall);
	}
	
	public static SpaceGroup fromCrystalSystem(CRYSTAL_SYSTEM system) {
		int itNumber = 1;
		switch (system) {
		case TRICLINIC:
			itNumber = 1;
			break;
		case MONOCLINIC:
			itNumber = 3;
			break;
		case ORTHOROMBIC:
			itNumber = 16;
			break;
		case TETRAGONAL:
			itNumber = 75;
			break;
		case TRIGONAL:
			itNumber = 143;
			break;
		case HEXAGONAL:
			itNumber = 168;
			break;
		case CUBIC:
			itNumber = 195;
			break;
		default:
			break;
		}
		return SpaceGroupTable.findSpaceGroupByHallSymbol(CCTBXNative.itNumber2Hall(itNumber, ""));
	}
	
	
	public String hall() {
		return hall_symbol;
	}
	
	public int number() {
		return itNumber;
	}
	
	public List<String> hmSymbols() {
		return hmSymbols;
	}

	public String hermann_mauguin() {
		return hmSymbols.get(0);
	}
	
	public String universal_hm() {
		return universal_hermann_mauguin;
	}
	
	public String schoenflies() {
		return schoenflies;
	}
	
	public CRYSTAL_SYSTEM getCrystalSystem() {
		if ((itNumber == 1) || (itNumber == 2))
			return CRYSTAL_SYSTEM.TRICLINIC;
		if ((itNumber >= 3) && (itNumber <= 15))
			return CRYSTAL_SYSTEM.MONOCLINIC;
		if ((itNumber >= 16) && (itNumber <= 74))
			return CRYSTAL_SYSTEM.ORTHOROMBIC;
		if ((itNumber >= 75) && (itNumber <= 142))
			return CRYSTAL_SYSTEM.TETRAGONAL;
		if ((itNumber >= 143) && (itNumber <= 167))
			return CRYSTAL_SYSTEM.TRIGONAL;
		if ((itNumber >= 168) && (itNumber <= 194))
			return CRYSTAL_SYSTEM.HEXAGONAL;
		if ((itNumber >= 195) && (itNumber <= 230))
			return CRYSTAL_SYSTEM.CUBIC;
		return CRYSTAL_SYSTEM.TRICLINIC;
	}
	
	
}
