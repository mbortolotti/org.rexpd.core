package com.rietveldextreme.structure;

public class BoundingBox {
	
	public BoundingBox() {
	}

	public BoundingBox(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.zMin = zMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.zMax = zMax;
	}
	
	public double xMin = 0.0;
	public double yMin = 0.0;
	public double zMin = 0.0;
	public double xMax = 10.0;
	public double yMax = 10.0;
	public double zMax = 10.0;

}
