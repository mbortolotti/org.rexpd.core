package com.rietveldextreme.structure;

import com.rietveldextreme.optimization.AbstractOptimizable;
import com.rietveldextreme.optimization.Parameter;

public class AnisoUFactors extends AbstractOptimizable {
	
	public static final String ANISO_U_FACTORS = "Anisotropic thermal factors";
	
	public Parameter U11 = null;
	public Parameter U12 = null;
	public Parameter U13 = null;
	public Parameter U22 = null;
	public Parameter U23 = null;
	public Parameter U33 = null;
	
	public AnisoUFactors() {
		setLabel(ANISO_U_FACTORS);
		addNode(U11 = new Parameter(this, "U11", 0.05, 0.0, 100));
		addNode(U12 = new Parameter(this, "U12", 0.00, 0.0, 100));
		addNode(U13 = new Parameter(this, "U13", 0.00, 0.0, 100));
		addNode(U22 = new Parameter(this, "U22", 0.05, 0.0, 100));
		addNode(U23 = new Parameter(this, "U23", 0.00, 0.0, 100));
		addNode(U33 = new Parameter(this, "U33", 0.05, 0.0, 100));
	}

}
