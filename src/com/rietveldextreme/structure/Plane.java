package com.rietveldextreme.structure;

public class Plane {
	
	double a = 1.0;
	double b = 1.0;
	double c = 1.0;
	double d = 1.0;
	
	public Plane (double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public Plane (Vec3d x1, Vec3d x2, Vec3d x3) {
		Vec3d normal = Vec3d.crossProduct((x2.subtract(x1)), (x3.subtract(x1)));
		normal = normal.scalarProduct(1/normal.norm());
		a = normal.getX0();
		b = normal.getX1();
		c = normal.getX2();
		d = - normal.dotProduct(x1);
	}
	
	public Vec3d getNormal() {
		Vec3d normal = new Vec3d(a, b, c);
		normal = normal.scalarProduct(1/normal.norm());
		return normal;
	}
	
	public Vec3d getNormalVector() {
		return new Vec3d(a, b, c);
	}
	
	public double distance(Coord3d point) {
		double x0 = point.getX();
		double y0 = point.getY();
		double z0 = point.getZ();
		return (d + a*x0 + b*y0 + c*z0) / Math.sqrt(a*a + b*b + c*c);
	}
	
	public double dihedralAngle(Plane second) {
		double cos_angle = Vec3d.dotProduct(this.getNormal(), second.getNormal());
		return Math.acos(cos_angle);
	}
	
	public double getA() {
		return a;
	}
	
	public double getB() {
		return b;
	}
	
	public double getC() {
		return c;
	}
	
	public double getD() {
		return d;
	}
	
	public void setA(double a) {
		this.a = a;
	}
	
	public void setB(double b) {
		this.b = b;
	}
	
	public void setC(double c) {
		this.c = c;
	}
	
	public void setD(double d) {
		this.d = d;
	}
	
	public String toString() {
		return a + "x + " + b + "y + " + c + "z + " + d + " = 0";
	}
	
	/*public double[] getConstants() {
		double[] constants = new double[4];
		constants[0] = a;
		constants[1] = b;
		constants[2] = c;
		constants[3] = d;
		return constants;
	}*/

}
