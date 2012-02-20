
package com.rietveldextreme.structure;

/**
 * @version $Revision: 1.00 $, $Date: 14-gen-2004 16.53.34
 * @author Mauro Bortolotti
 * @since JDK1.1
 */
public class Vec3d {

	private double X[] = {0, 0, 0};
	
	public Vec3d() {
		this.X[0] = 0;
		this.X[1] = 0;
		this.X[2] = 0;	
	}
	
	public Vec3d(double x0, double x1, double x2) {
		this.X[0] = x0;
		this.X[1] = x1;
		this.X[2] = x2;	
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vec3d v_e = (Vec3d) obj;
		if ((X[0] == v_e.X[0]) && (X[1] == v_e.X[1]) && (X[2] == v_e.X[2]))
			return true;
		return false;
	}

	public void setX0(double x0) {
		this.X[0] = X[0];
	}

	public final double getX0() {
		return X[0];
	}

	public void setX1(double x1) {
		this.X[1] = X[1];
	}

	public final double getX1() {
		return X[1];
	}

	public void setX2(double x2) {
		this.X[2] = X[2];
	}

	public final double getX2() {
		return X[2];
	}
	
	public static Vec3d scalarProduct(double scalar, Vec3d v1) {
		double v_x0 = scalar*v1.X[0];
		double v_x1 = scalar*v1.X[1];
		double v_x2 = scalar*v1.X[2];
		return new Vec3d(v_x0, v_x1, v_x2);
	}
	
	public static double dotProduct(final Vec3d v1, final Vec3d v2) {
		double v = v1.X[0]*v2.X[0] + v1.X[1]*v2.X[1] + v1.X[2]*v2.X[2];
		return v;
	}
	
	public static Vec3d crossProduct(final Vec3d v1, final Vec3d v2) {
		double v_x1 = v1.X[1]*v2.X[2]-v2.X[1]*v1.X[2];
		double v_x2 = v1.X[2]*v2.X[0]-v2.X[2]*v1.X[0];
		double v_x3 = v1.X[0]*v2.X[1]-v2.X[0]*v1.X[1];
		
		return new Vec3d(v_x1, v_x2, v_x3);
	}
	
	public Vec3d scalarProduct(double scalar) {
		X[0] *= scalar;
		X[1] *= scalar;
		X[2] *= scalar;
		return this;
	}

	public double dotProduct(final Vec3d v1) {
		double v = this.X[0]*v1.X[0] + this.X[1]*v1.X[1] + this.X[2]*v1.X[2];
		return v;
	}
	
	public double angle(final Vec3d v1) {
		return angle(v1, this);
	}

	public static double angle(final Vec3d v1, final Vec3d v2) {
		double v = v1.X[0]*v2.X[0] + v1.X[1]*v2.X[1] + v1.X[2]*v2.X[2];
		double cos_a = v / (v1.norm() * v2.norm());
		return Math.acos(cos_a);
	}

	public Vec3d crossProduct(final Vec3d v1) {
		double v_x0 = this.X[1]*v1.X[2]-v1.X[1]*this.X[2];
		double v_x1 = this.X[2]*v1.X[0]-v1.X[2]*this.X[0];
		double v_x2 = this.X[0]*v1.X[1]-v1.X[0]*this.X[1];
		return new Vec3d(v_x0, v_x1, v_x2);
	}
	
	public static double norm(Vec3d v) {
		double n = Math.sqrt(v.X[0]*v.X[0] + v.X[1]*v.X[1] + v.X[2]*v.X[2]);
		return n;
	}
	
	public double norm() {
		return norm(this);
	}
	
	public static Vec3d unit(Vec3d v) {
		double n = norm(v);
		double v_x0 = v.X[0]/n;
		double v_x1 = v.X[1]/n;
		double v_x2 = v.X[2]/n;
		return new Vec3d(v_x0, v_x1, v_x2);
	}
	
	public Vec3d unit() {
		return unit(this);
	}
	
	public static Vec3d add(final Vec3d v1, final Vec3d v2) {
		double x0 = v1.X[0] + v2.X[0];
		double x1 = v1.X[1] + v2.X[1];
		double x2 = v1.X[2] + v2.X[2];
		return new Vec3d(x0, x1, x2);
	}
	
	public Vec3d add(final Vec3d v) {
		return add(this, v);	
	}
	
	public static Vec3d subtract(final Vec3d v1, final Vec3d v2) {
		double x0 = v1.X[0] - v2.X[0];
		double x1 = v1.X[1] - v2.X[1];
		double x2 = v1.X[2] - v2.X[2];
		return new Vec3d(x0, x1, x2);
	}
	
	public Vec3d subtract(final Vec3d v) {
		return subtract(this, v);	
	}
	
	public Vec3d invert() {
		X[0] *= -1;
		X[1] *= -1;
		X[2] *= -1;
		return this;
	}

	public String toString() {	
		String sv =  this.getClass().getName() + "\n";
		sv += "[" +  X[0] + "][" + X[1] + "][" + X[2] + "]" + "\n";
		return sv;
	}

}
