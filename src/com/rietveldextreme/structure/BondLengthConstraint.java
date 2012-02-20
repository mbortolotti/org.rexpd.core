package com.rietveldextreme.structure;

import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

public class BondLengthConstraint extends CoordinatesTransformation {

	private RealMatrix constantTransformation = MatrixUtils.createRealIdentityMatrix(4);

	public BondLengthConstraint() {
		super();
		x_trans.setEnabled(true);
		y_trans.setEnabled(false);
		z_trans.setEnabled(false);
		omega1.setEnabled(false);
		omega2.setEnabled(false);
		omega3.setEnabled(false);
	}

	public void setTransformationMatrix(RealMatrix transformation) {
		double a03 = transformation.getEntry(0, 3);
		double a13 = transformation.getEntry(1, 3);
		double a23 = transformation.getEntry(2, 3);
		double x0 = Math.sqrt(a03 * a03 + a13 * a13 + a23 * a23);
		double[][] elementsT = { { 1, 0, 0, x0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		RealMatrix translation = MatrixUtils.createRealMatrix(elementsT);
		RealMatrix transInverse = new LUDecompositionImpl(translation).getSolver().getInverse();
		constantTransformation = transformation.multiply(transInverse);
		setX(x0);
	}

	public RealMatrix getTransformationMatrix() {
		double dx = x_trans.getValue();
		double[][] elementsT = { { 1, 0, 0, dx }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		RealMatrix translation = MatrixUtils.createRealMatrix(elementsT);
		return translation.multiply(constantTransformation);
	}

}
