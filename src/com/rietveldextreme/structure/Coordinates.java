package com.rietveldextreme.structure;

import com.rietveldextreme.optimization.AbstractOptimizable;
import com.rietveldextreme.optimization.Parameter;

public class Coordinates extends AbstractOptimizable {
	
	public double a1;
	public double a2;
	public double a3;
	
	private Parameter x_coord;
	private Parameter y_coord;
	private Parameter z_coord;
	
	public Coordinates(String label, double x, double y, double z) {
		setLabel(label);
		addNode(x_coord = new Parameter(this, "x", x));
		addNode(y_coord = new Parameter(this, "y", y));
		addNode(z_coord = new Parameter(this, "z", z));
	}
	
	public Coordinates() {
		this("Coordinates", 0.0, 0.0, 0.0);
	}
	
	public double getX() {
		return x_coord.getValue();
	}
	
	public double getY() {
		return y_coord.getValue();
	}
	
	public double getZ() {
		return z_coord.getValue();
	}

}
