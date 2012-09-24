package org.rexpd.core.utils;

public abstract class NumberUtils {

	public static double parseDouble(String value) {
		double val = 0.0D;
		if (value.charAt(0) == '.')
			value = "0" + value;
		int bracketOpen = value.indexOf("(");
		if (bracketOpen != -1)
			value = value.substring(0, bracketOpen);
		try {
			val = Double.parseDouble(value);
		} catch (Exception exception) {
		}
		return val;
	}

	public static DoublePair parseDoubleWithESD(String doubleString) {
		double val = 0.0D;
		double esd = 0.0D;
		String valString = "0.0";
		String esdString = "0.0";
		if (doubleString.charAt(0) == '.')
			doubleString = "0" + doubleString;
		int bracketOpen = doubleString.indexOf("(");
		int bracketClose = doubleString.indexOf(")");
		if (bracketOpen != -1 && bracketClose != -1) {
			valString = doubleString.substring(0, bracketOpen);
			esdString = doubleString.substring(bracketOpen + 1, bracketClose);
		}
		else {
			valString = doubleString;
		}
		try {
			val = Double.parseDouble(valString);
			esd = Double.parseDouble(esdString);
		} catch (Exception exception) {
		}
		return new DoublePair(val, esd);
	}

}
