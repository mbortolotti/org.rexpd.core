
package com.rietveldextreme.structure;

import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

/**
 * @version $Revision: 1.00 $, $Date: 29-gen-2004 17.27.08 * @author Mauro Bortolotti * @since JDK1.1
 */

public class TorsionAngleConstraint extends CoordinatesTransformation {
	
	private RealMatrix constantTransformation = MatrixUtils.createRealIdentityMatrix(4);
	private RealMatrix preRotMatrix = MatrixUtils.createRealIdentityMatrix(4);
	private RealMatrix preTransMatrix = MatrixUtils.createRealIdentityMatrix(4);
	//private Matrix postRotMatrix = Matrix.identity(4, 4);
	
	public static void main(String args[]) {
		
	}
	
	public TorsionAngleConstraint() {
		super();
		x_trans.setEnabled(false);
		y_trans.setEnabled(false);
		z_trans.setEnabled(false);
		omega1.setEnabled(true);
		omega2.setEnabled(false);
		omega3.setEnabled(false);
	}

	public void setTransformationMatrix(RealMatrix transformation) {
		/*double a03 = transformation.get(0, 3);
		double a13 = transformation.get(1, 3);
		double a23 = transformation.get(2, 3);
		double x0 = Math.sqrt(a03 * a03 + a13 * a13 + a23 * a23);
		double[][] elementsT = { { 1, 0, 0, x0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		Matrix translation = new Matrix(elementsT);
		constantTransformation = transformation.times(translation.inverse());
		setX(x0);*/
		constantTransformation = transformation;
	}
	
	public void setPivotPoints(Coord3d point1, Coord3d point2) {
		
		double[][] trans = { { 1, 0, 0, -point1.getX() }, { 0, 1, 0, -point1.getY() }, { 0, 0, 1, -point1.getZ() }, { 0, 0, 0, 1 } };
		preTransMatrix = MatrixUtils.createRealMatrix(trans);
		
		Vec3d distance = Vec3d.subtract(point2, point1);
		System.out.println(point1);
		System.out.println(point2);
		
		double dx = distance.getX0();
		double dy = distance.getX1();
		double dz = distance.getX2();
		System.out.println("Distance: " + dx + " " + dy + " " + dz);
		
		//double gamma = Math.acos( dx / Math.sqrt( dx*dx + dy*dy ) );
		//double beta = Math.acos( dz / Math.sqrt( dx*dx + dy*dy + dz*dz) );
		
		double gamma = - Math.atan2(dy, dx);
		double beta = - Math.atan( dz / Math.sqrt( dx*dx + dy*dy) );
		
		//gamma = (gamma > 0) ? gamma : gamma + 2*Math.PI;
		//beta = (beta > 0) ? beta : beta + 2*Math.PI;
		
		System.out.println("gamma: " + gamma);
		System.out.println("beta: " + beta);
		
		/*double cg = dx / Math.sqrt( dx*dx + dy*dy );
		double sg = dy / Math.sqrt( dx*dx + dy*dy );
		double cb = dz / Math.sqrt( dx*dx + dy*dy + dz*dz );
		double sb = Math.sqrt( dx*dx + dy*dy ) / Math.sqrt( dx*dx + dy*dy + dz*dz );*/
		
		double cg = Math.cos(gamma);
		double sg = Math.sin(gamma);
		double cb = Math.cos(beta);
		double sb = Math.sin(beta);
		
		double[][] z_rot = { { cg, sg, 0, 0 }, { -sg, cg, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		double[][] y_rot = { { cb, 0, -sb, 0 }, { 0, 1, 0, 0 }, { sb, 0, cb, 0 }, { 0, 0, 0, 1 } };
		//double[][] zr_2 = {{cg, -sg, 0, 0}, {sg, cg, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
		//double[][] yr_2 = {{cb, 0, sb, 0}, {0, 1, 0, 0}, {-sb, 0, cb, 0}, {0, 0, 0, 1}};
		
		preRotMatrix = (MatrixUtils.createRealMatrix(y_rot)).multiply(MatrixUtils.createRealMatrix(z_rot));
		//postRotMatrix = (new Matrix(yr_2)).times(new Matrix(zr_2));
		System.out.println("a_rot: " + omega1);
		
	}

	public RealMatrix getTransformationMatrix() {
		double alpha = omega1.getValue();
		double sa = Math.sin(alpha);
		double ca = Math.cos(alpha);
		double[][] elementsR = { { 1, 0, 0, 0 }, { 0, ca, -sa, 0 }, { 0, sa, ca, 0 }, { 0, 0, 0, 1 } };
		//double[][] elementsR = { { ca, -sa, 0, 0 }, { sa, ca, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		RealMatrix rotation = MatrixUtils.createRealMatrix(elementsR);
		//return constantTransformation.times(preRotMatrix).times(rotation).times(preRotMatrix.inverse());
		//return preRotMatrix.times(rotation).times(postRotMatrix);
		RealMatrix rotInverse = new LUDecompositionImpl(preRotMatrix).getSolver().getInverse();

		return ((constantTransformation.multiply(preRotMatrix)).multiply(rotation)).multiply(rotInverse);
	}
	

}
