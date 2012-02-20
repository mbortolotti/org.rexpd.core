
package com.rietveldextreme.structure;

/**
 * @version $Revision: 1.00 $, $Date: 16-gen-2004 10.44.56
 * @author Mauro Bortolotti
 * @since JDK1.1
 */

public class Coord3d extends Vec3d {
	
	public static void main(String[] args) {
		Coord3d c1 = new Coord3d(0, -1, 1);
		Coord3d c2 = new Coord3d(0, 0, 0);
		Coord3d c3 = new Coord3d(-1, 0, 0);
		Coord3d c4 = new Coord3d(0, 0, -1);
		System.out.println(Coord3d.distance(c1, c2));
		System.out.println(Coord3d.angle(c1, c2, c3));
		System.out.println(Coord3d.angle(c1, c2, c3, c4));
	}
	
	public Coord3d() {
		super();
	}
	
	public Coord3d(double x, double y, double z) {
		super(x, y, z);
	}
	
	public Coord3d(Vec3d v3d) {
		super(v3d.getX0(), v3d.getX1(), v3d.getX2());
	}
	
	public final double getX() {
		return getX0();
	}
	
	public final double getY() {
		return getX1();
	}
	
	public final double getZ() {
		return getX2();
	}
	
	public final void setX(double X) {
		setX0(X);
	}
	
	public final void setY(double Y) {
		setX0(Y);
	}
	
	public final void setZ(double Z) {
		setX0(Z);
	}
	
	public static double distance(final Coord3d c1, final Coord3d c2) {
		return Vec3d.norm(Vec3d.subtract(c2,c1));
	}
	
	public static double angle(final Coord3d c1, final Coord3d c2, final Coord3d c3) {
		return Math.acos(Vec3d.dotProduct(Vec3d.subtract(c1, c2).unit(), Vec3d.subtract(c3, c2).unit()));	
	}
	
	public static double angle(final Coord3d c1, final Coord3d c2, final Coord3d c3, final Coord3d c4) {
		Vec3d v12 = Vec3d.subtract(c1, c2);
		Vec3d v32 = Vec3d.subtract(c3, c2);
		Vec3d v43 = Vec3d.subtract(c4, c3);
		
		Vec3d n123 = Vec3d.unit(Vec3d.crossProduct(v12, v32)); // the normal to plane 123
		Vec3d n234 = Vec3d.unit(Vec3d.crossProduct(v43, v32)); // the normal to plane 234
		
		double angle = Math.acos(Vec3d.dotProduct(n123, n234)); // the dihedral angle value
		
		if (Vec3d.dotProduct(v32, Vec3d.crossProduct(n123, n234)) < 0) // check the angle sign
			return (-1)*angle;
		return angle;
	}

}
