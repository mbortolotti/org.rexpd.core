
package com.rietveldextreme.structure;

import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

import com.rietveldextreme.optimization.AbstractOptimizable;
import com.rietveldextreme.optimization.Optimizations;
import com.rietveldextreme.optimization.Parameter;

/**
 * @version $Revision: 1.00 $, $Date: 19-feb-2004 15.19.33
 * @author Mauro Bortolotti
 * @since JDK1.1
 */
public class CoordinatesTransformation extends AbstractOptimizable {
	
	public static final String TRANSFORMATION_TAG = "Transformation";
	public static final String X_TRANSLATION = "x translation";
	public static final String Y_TRANSLATION = "x translation";
	public static final String Z_TRANSLATION = "x translation";
	public static final String OMEGA1_ROTATION = "omega 1";
	public static final String OMEGA2_ROTATION = "omega 2";
	public static final String OMEGA3_ROTATION = "omega 3";
	
	
	protected Parameter x_trans;
	protected Parameter y_trans;
	protected Parameter z_trans;
	protected Parameter omega1;
	protected Parameter omega2;
	protected Parameter omega3;
	
	public CoordinatesTransformation() {
		setType(TRANSFORMATION_TAG);
		addNode(x_trans = new Parameter(this, X_TRANSLATION, 0.0, 0.0, 100));
		addNode(y_trans = new Parameter(this, Y_TRANSLATION, 0.0, 0.0, 100));
		addNode(z_trans = new Parameter(this, Z_TRANSLATION, 0.0, 0.0, 100));
		addNode(omega1 = new Parameter(this, OMEGA1_ROTATION, 0.0, 0.0, 360));
		addNode(omega2 = new Parameter(this, OMEGA2_ROTATION, 0.0, 0.0, 360));
		addNode(omega3 = new Parameter(this, OMEGA3_ROTATION, 0.0, 0.0, 360));
		Optimizations.setEnabled(this, false);
	}
	
	public void enable(boolean enabled) {
		Optimizations.setEnabled(this, enabled);
	}
	
	public void setTransformationMatrix(RealMatrix transformation) {
		
		double a00 = transformation.getEntry(0, 0);
		double a01 = transformation.getEntry(0, 1);
		double a02 = transformation.getEntry(0, 2);
		double a03 = transformation.getEntry(0, 3);
		double a12 = transformation.getEntry(1, 2);
		double a13 = transformation.getEntry(1, 3);
		double a22 = transformation.getEntry(2, 2);
		double a23 = transformation.getEntry(2, 3);
		
		double alpha = Math.atan(-a12/a22);
		double beta = Math.asin(a02);
		double gamma = Math.atan(-a01/a00);
		
		double sa = Math.sin(alpha);
		double ca = Math.cos(alpha);
		double sb = Math.sin(beta);
		double cb = Math.cos(beta);
		double sg = Math.sin(gamma);
		double cg = Math.cos(gamma);
		double x = cg * (a03 * cb + (a13 * sa - a23 * ca) * sb) + (a13 * ca + a23 * sa) * sg;
		double y = a23 * cg * sa - (a03 * cb + a13 * sa * sb) * sg + ca * (a13 * cg + a23 * sb * sg);
		double z = a23 * ca * cb - a13 * cb * sa + a03 * sb;
		
		setAlpha(alpha);
		setBeta(beta);
		setGamma(gamma);
		setX(x);
		setY(y);
		setZ(z);
	}
	
	public double getX() {
		return x_trans.getValue();
	}
	
	public double getY() {
		return y_trans.getValue();
	}
	
	public double getZ() {
		return z_trans.getValue();
	}
	
	public double getAlpha() {
		return omega1.getValue();
	}
	
	public double getBeta() {
		return omega2.getValue();
	}
	
	public double getGamma() {
		return omega3.getValue();
	}
	
	public void setX(double X) {
		x_trans.setValue(X);
	}
	
	public void setY(double Y) {
		y_trans.setValue(Y);
	}
	
	public void setZ(double Z) {
		z_trans.setValue(Z);
	}
	
	public void setAlpha(double A) {
		omega1.setValue(A);
	}
	
	public void setBeta(double B) {
		omega2.setValue(B);
	}
	
	public void setGamma(double C) {
		omega3.setValue(C);
	}
	
	public void setPosition(double X, double Y, double Z) {
		setX(X);
		setY(Y);
		setZ(Z);
	}
	
	public RealMatrix getTransformationMatrix() {
		
		double ca = Math.cos((Math.PI/180) * omega1.getValue());
		double sa = Math.sin((Math.PI/180) * omega1.getValue());
		double cb = Math.cos((Math.PI/180) * omega2.getValue());
		double sb = Math.sin((Math.PI/180) * omega2.getValue());
		double cc = Math.cos((Math.PI/180) * omega3.getValue());
		double sc = Math.sin((Math.PI/180) * omega3.getValue());
		double dx = x_trans.getValue();
		double dy = y_trans.getValue();
		double dz = z_trans.getValue();
		
		double[][] elementsA = { {1,0,0,0}, {0,ca,-sa,0}, {0,sa,ca,0}, {0,0,0,1} };
		double[][] elementsB = { {cb,0,sb,0}, {0,1,0,0}, {-sb,0,cb,0}, {0,0,0,1} };
		double[][] elementsC = { {cc,-sc,0,0}, {sc,cc,0,0}, {0,0,1,0}, {0,0,0,1} };
		double[][] elementsT = { {1,0,0,dx}, {0,1,0,dy}, {0,0,1,dz}, {0,0,0,1} };
		
		RealMatrix rotationA = MatrixUtils.createRealMatrix(elementsA);
		RealMatrix rotationB = MatrixUtils.createRealMatrix(elementsB);
		RealMatrix rotationC = MatrixUtils.createRealMatrix(elementsC);
		RealMatrix translation = MatrixUtils.createRealMatrix(elementsT);
		
		return translation.multiply(rotationA.multiply(rotationB.multiply(rotationC)));
		
	}
	
	public String toString() {
		RealMatrix m = getTransformationMatrix();
		return m.getEntry(0, 0) + " " + m.getEntry(0, 1) + " " + m.getEntry(0, 2) + " " + m.getEntry(0, 3) + "\n" +
		m.getEntry(1, 0) + " " + m.getEntry(1, 1) + " " + m.getEntry(1, 2) + " " + m.getEntry(1, 3) + "\n" +
		m.getEntry(2, 0) + " " + m.getEntry(2, 1) + " " + m.getEntry(2, 2) + " " + m.getEntry(2, 3) + "\n" +
		m.getEntry(3, 0) + " " + m.getEntry(3, 1) + " " + m.getEntry(3, 2) + " " + m.getEntry(3, 3);
	}
	
}
