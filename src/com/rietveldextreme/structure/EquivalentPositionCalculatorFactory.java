/*
 * Created on 11-ago-2004
 *
 * TODO To change the  for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rietveldextreme.structure;


/**
 * @author mauro *  * TODO To change the template for this generated type comment go to * Window - Preferences - Java - Code Generation - Code and Comments
 */

public class EquivalentPositionCalculatorFactory {
	
	public static final String CCTBX_CALCULATOR = "CCTBX_CALCULATOR";

	public static EquivalentPositionCalculator createCalculator(String TYPE) {
		if (TYPE == CCTBX_CALCULATOR)
			return new EquivalentPositionCalculatorCCTBX();
		return new EquivalentPositionCalculatorCCTBX();
	}
	
	public static EquivalentPositionCalculator getDefaultCalculator() {
		return createCalculator(CCTBX_CALCULATOR);
	}
}


