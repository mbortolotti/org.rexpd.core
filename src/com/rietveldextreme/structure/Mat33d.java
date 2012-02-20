
package com.rietveldextreme.structure;

/**
 * @version $Revision: 1.00 $, $Date: 19-gen-2004 10.35.30
 * @author Mauro Bortolotti
 * @since JDK1.1
 */
public class Mat33d {
	
	public static void main(String[] args) {
		Mat33d m1 = new Mat33d(3, 8, 7, -2, 4, 1, 5, 3, 9);
		Mat33d m2 = new Mat33d(0, -5, 1, 2, 9, 3, -2, 1, 4);
		Vec3d v1 = new Vec3d(-3, 1, 5);
		System.out.println("Matrix:" + m1);	
		System.out.println("Transpose:" + m1.transpose());	
		System.out.println("Inverse:" + m1.inverse());	
		System.out.println("Product 1:" + Mat33d.Product(m1, v1));
		System.out.println("Product 2:" + Mat33d.Product(v1, m1));
		System.out.println("Product 3:" + Mat33d.Product(m1, m2));
		System.out.println("Product 4:" + Mat33d.Product(m2, m1));
	}
	
	/** 
	 * Construct a new 3 x 3 matrix
	 */
	public Mat33d() {
	}
	
	/**
	 * Construct a new 3 x 3 matrix based on the supplied element values
	 * 
	 * @param a00 [0][0] element value
	 * @param a01 [0][1] element value
	 * @param a02 [0][2] element value
	 * @param a10 [1][0] element value
	 * @param a11 [1][1] element value
	 * @param a12 [1][2] element value
	 * @param a20 [2][0] element value
	 * @param a21 [2][1] element value
	 * @param a22 [2][2] element value
	 */
	public Mat33d(double a00, double a01, double a02, double a10, double a11, double a12, double a20, double a21, double a22) {
		M[0][0] = a00;
		M[0][1] = a01;
		M[0][2] = a02;
		M[1][0] = a10;
		M[1][1] = a11;
		M[1][2] = a12;
		M[2][0] = a20;
		M[2][1] = a21;
		M[2][2] = a22;	
	}
	
	protected Object clone() {
		
		Mat33d cloned = new Mat33d();
		
		cloned.M[0][0] = M[0][0];
		cloned.M[0][1] = M[0][1];
		cloned.M[0][2] = M[0][2];
		cloned.M[1][0] = M[1][0];
		cloned.M[1][1] = M[1][1];
		cloned.M[1][2] = M[1][2];
		cloned.M[2][0] = M[2][0];
		cloned.M[2][1] = M[2][1];
		cloned.M[2][2] = M[2][2];
		
		return cloned;	
	}
	
	public String toString() {
		String sm =  this.getClass().getName() + "\n";
		sm += "[" +  M[0][0] + "][" + M[0][1] + "][" + M[0][2] + "]" + "\n";
		sm += "[" +  M[1][0] + "][" + M[1][1] + "][" + M[1][2] + "]" + "\n";
		sm += "[" +  M[2][0] + "][" + M[2][1] + "][" + M[2][2] + "]" + "\n";
		return sm;
	}
	
	/** 
	 * Calculates the matrix determinant
	 * @return The determinant double value
	 */
	public final double det() {
		double d1 = M[0][0] * ( M[1][1] * M[2][2] - M[1][2] * M[2][1]);
		double d2 = M[0][1] * ( M[1][2] * M[2][0] - M[1][0] * M[2][2]);
		double d3 = M[0][2] * ( M[1][0] * M[2][1] - M[1][1] * M[2][0]);
		return d1 + d2 + d3;
	}
		
	/** 
	 * Invert a matrix 
	 * @param mat the matrix to be inverted
	 * @return A reference to a new Mat33d object representing the inverted matrix
	 */
	public static final Mat33d inverse(final Mat33d mat) {
		
		double d = mat.det();
		
		Mat33d inv = new Mat33d();
		
		double i00 = ( mat.M[1][1]*mat.M[2][2] - mat.M[1][2]*mat.M[2][1] ) / d;
		double i10 = ( mat.M[1][2]*mat.M[2][0] - mat.M[1][0]*mat.M[2][2] ) / d;
		double i20 = ( mat.M[1][0]*mat.M[2][1] - mat.M[1][1]*mat.M[2][0] ) / d;
		double i01 = ( mat.M[2][1]*mat.M[0][2] - mat.M[2][2]*mat.M[0][1] ) / d;
		double i11 = ( mat.M[2][2]*mat.M[0][0] - mat.M[2][0]*mat.M[0][2] ) / d;
		double i21 = ( mat.M[2][0]*mat.M[0][1] - mat.M[2][1]*mat.M[0][0] ) / d;
		double i02 = ( mat.M[0][1]*mat.M[1][2] - mat.M[0][2]*mat.M[1][1] ) / d;
		double i12 = ( mat.M[0][2]*mat.M[1][0] - mat.M[0][0]*mat.M[1][2] ) / d;
		double i22 = ( mat.M[0][0]*mat.M[1][1] - mat.M[0][1]*mat.M[1][0] ) / d;
		
		inv.M[0][0] = i00;
		inv.M[1][0] = i10;
		inv.M[2][0] = i20;
		inv.M[0][1] = i01;
		inv.M[1][1] = i11;
		inv.M[2][1] = i21;
		inv.M[0][2] = i02;
		inv.M[1][2] = i12;
		inv.M[2][2] = i22;
		
		return inv;
	}
	

	/** 
	 * Invert the matrix
	 * @return A reference to a new Mat33d object representing the inverted matrix
	 */
	public final Mat33d inverse() {	
		return inverse(this);
	}
	
	/**
	 * Transposes a matrix
	 * @param mat The matrix to be transposed
	 * @return A reference to a new Mat33d object representing the tranposed matrix
	 */
	public static final Mat33d transpose(final Mat33d mat) {
		
		Mat33d trs = new Mat33d();
		
		double t00 = mat.M[0][0];
		double t01 = mat.M[1][0];
		double t02 = mat.M[2][0];
		double t10 = mat.M[0][1];
		double t11 = mat.M[1][1];
		double t12 = mat.M[2][1];
		double t20 = mat.M[0][2];
		double t21 = mat.M[1][2];
		double t22 = mat.M[2][2];
		
		trs.M[0][0] = t00;
		trs.M[0][1] = t01;
		trs.M[0][2] = t02;
		trs.M[1][0] = t10;
		trs.M[1][1] = t11;
		trs.M[1][2] = t12;
		trs.M[2][0] = t20;
		trs.M[2][1] = t21;
		trs.M[2][2] = t22;
		
		return trs;
	}
	
	/**
	 * Transposes the matrix
	 * 
	 * @return A reference to a new Mat33d object representing the tranposed matrix
	 */
	public final Mat33d transpose() {
		return transpose(this);
	}
	
	/** 
	 * Multiply a 3x3 matrix with a 3-elements vector
	 * 
	 * @param m the matrix
	 * @param v the vector
	 * @return a 3-elements vector
	 */
	public static final Vec3d Product(final Mat33d m, final Vec3d v) {
		double v0 = v.getX0();
		double v1 = v.getX1();
		double v2 = v.getX2();
		double p1 = m.M[0][0]*v0 + m.M[0][1]*v1 + m.M[0][2]*v2;
		double p2 = m.M[1][0]*v0 + m.M[1][1]*v1 + m.M[1][2]*v2;
		double p3 = m.M[2][0]*v0 + m.M[2][1]*v1 + m.M[2][2]*v2;
		return new Vec3d(p1, p2, p3);
	}
	
	/**
	 * Multiply a 3-elements vector with a 3x3 matrix 
	 * 
	 * @param v the vector
	 * @param m the matrix
	 * @return a 3-elements vector
	 */
	public static final Vec3d Product(final Vec3d v, final Mat33d m) {
		double v0 = v.getX0();
		double v1 = v.getX1();
		double v2 = v.getX2();
		double p1 = m.M[0][0]*v0 + m.M[1][0]*v1 + m.M[2][0]*v2;
		double p2 = m.M[0][1]*v0 + m.M[1][1]*v1 + m.M[2][1]*v2;
		double p3 = m.M[0][2]*v0 + m.M[1][2]*v1 + m.M[2][2]*v2;
		return new Vec3d(p1, p2, p3);	
	}
	
	/**
	 * Multiply two 3x3 matrices
	 * 
	 * @param m1 the first matrix 
	 * @param m2 the second matrix
	 * @return a 3x3 matrix
	 */
	public static final Mat33d Product(final Mat33d m1, final Mat33d m2) {
		double p00 = m1.M[0][0]*m2.M[0][0] + m1.M[0][1]*m2.M[1][0] + m1.M[0][2]*m2.M[2][0];
		double p01 = m1.M[0][0]*m2.M[0][1] + m1.M[0][1]*m2.M[1][1] + m1.M[0][2]*m2.M[2][1];
		double p02 = m1.M[0][0]*m2.M[0][2] + m1.M[0][1]*m2.M[1][2] + m1.M[0][2]*m2.M[2][2];
		double p10 = m1.M[1][0]*m2.M[0][0] + m1.M[1][1]*m2.M[1][0] + m1.M[1][2]*m2.M[2][0];
		double p11 = m1.M[1][0]*m2.M[0][1] + m1.M[1][1]*m2.M[1][1] + m1.M[1][2]*m2.M[2][1];
		double p12 = m1.M[1][0]*m2.M[0][2] + m1.M[1][1]*m2.M[1][2] + m1.M[1][2]*m2.M[2][2];
		double p20 = m1.M[2][0]*m2.M[0][0] + m1.M[2][1]*m2.M[1][0] + m1.M[2][2]*m2.M[2][0];
		double p21 = m1.M[2][0]*m2.M[0][1] + m1.M[2][1]*m2.M[1][1] + m1.M[2][2]*m2.M[2][1];
		double p22 = m1.M[2][0]*m2.M[0][2] + m1.M[2][1]*m2.M[1][2] + m1.M[2][2]*m2.M[2][2];
		return new Mat33d(p00, p01, p02, p10, p11, p12, p20, p21, p22);
		
	}
	
	private double[][] M = {{0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}};

}
