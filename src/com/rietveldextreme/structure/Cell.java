
package com.rietveldextreme.structure;


import com.rietveldextreme.optimization.Parameter;
import com.rietveldextreme.optimization.ParameterCalculatorIdentity;
import com.rietveldextreme.structure.SpaceGroup.CRYSTAL_SYSTEM;

/**
 * @version $Revision: 1.00 $, $Date: 19-gen-2004 10.20.29 * @author Mauro Bortolotti * @since JDK1.1
 */

public class Cell extends StructureComposite {

	public static final String CELL_TAG = "Cell";

	public static final String CELL_A = "a";
	public static final String CELL_B = "b";
	public static final String CELL_C = "c";
	public static final String CELL_ALPHA = "alpha";
	public static final String CELL_BETA = "beta";
	public static final String CELL_GAMMA = "gamma";

	private Parameter A = null;
	private Parameter B = null;
	private Parameter C = null;
	private Parameter Alpha = null;
	private Parameter Beta = null;
	private Parameter Gamma = null;

	private double Volume = 0;
	private Mat33d OrthoMatrix = new Mat33d();
	private Mat33d FractMatrix = new Mat33d();


	public Cell(StructureComponent structure, double a, double b, double c, double alpha, double beta, double gamma) {
		super(structure);
		setComponentType(CELL_TAG);
		setLabel(CELL_TAG);
		addNode(A = new Parameter(this, CELL_A, a, 0.0, 100.0));
		addNode(B = new Parameter(this, CELL_B, b, 0.0, 100.0));
		addNode(C = new Parameter(this, CELL_C, c, 0.0, 100.0));
		addNode(Alpha = new Parameter(this, CELL_ALPHA, alpha, 50, 150.0));
		addNode(Beta = new Parameter(this, CELL_BETA, beta, 50, 150.0));
		addNode(Gamma = new Parameter(this, CELL_GAMMA, gamma, 50, 150.0));
	}

	public Cell(StructureComponent phase) {
		this(phase, 10.0, 10.0, 10.0, 90.0, 90.0, 90.0);
	}

	public void checkCellParameters(CRYSTAL_SYSTEM lattice) {
		switch (lattice) {
		case TRICLINIC: {
			break;
		}
		case MONOCLINIC: {
			if (getAlpha() != 90.0) {
				Beta.setEnabled(false);
				Gamma.setEnabled(false);
			} else if (getBeta() != 90.0) {
				Alpha.setEnabled(false);
				Gamma.setEnabled(false);
			} else if (getGamma() != 90.0) {
				Alpha.setEnabled(false);
				Beta.setEnabled(false);
			}
			break;
		}
		case ORTHOROMBIC: {
			Alpha.setValue(90.0);
			Beta.setValue(90.0);
			Gamma.setValue(90.0);
			Alpha.setEnabled(false);
			Beta.setEnabled(false);
			Gamma.setEnabled(false);
			break;
		}
		case TETRAGONAL: {
			B.setCalculator(new ParameterCalculatorIdentity(A));
			B.setEnabled(false);
			Alpha.setValue(90.0);
			Beta.setValue(90.0);
			Gamma.setValue(90.0);
			Alpha.setEnabled(false);
			Beta.setEnabled(false);
			Gamma.setEnabled(false);
			break;
		}
		case TRIGONAL: {
			if ((getA() != getB()) || (getA() != getC())) {
				B.setCalculator(new ParameterCalculatorIdentity(A));
				B.setEnabled(false);
				Alpha.setEnabled(false);
				Beta.setEnabled(false);
				Gamma.setValue(120.0);
				Gamma.setEnabled(false);
			} else {
				B.setCalculator(new ParameterCalculatorIdentity(A));
				C.setCalculator(new ParameterCalculatorIdentity(A));
				B.setEnabled(false);
				C.setEnabled(false);
			}
			break;
		}
		case HEXAGONAL: {
			B.setCalculator(new ParameterCalculatorIdentity(A));
			B.setEnabled(false);
			Alpha.setValue(90.0);
			Beta.setValue(90.0);
			Gamma.setValue(120.0);
			Alpha.setEnabled(false);
			Beta.setEnabled(false);
			Gamma.setEnabled(false);
			break;
		}
		case CUBIC: {
			B.setCalculator(new ParameterCalculatorIdentity(A));
			C.setCalculator(new ParameterCalculatorIdentity(A));
			B.setEnabled(false);
			C.setEnabled(false);
			Alpha.setValue(90.0);
			Beta.setValue(90.0);
			Gamma.setValue(90.0);
			Alpha.setEnabled(false);
			Beta.setEnabled(false);
			Gamma.setEnabled(false);
			break;
		}
		default: {
			break;
		}
		}
	}

	public void setA(double a) {
		A.setValue(a);
	}

	public double getA() {
		return A.getValue();
	}

	public void setB(double b) {
		B.setValue(b);
	}

	public double getB() {
		return B.getValue();
	}

	public void setC(double c) {
		C.setValue(c);
	}

	public double getC() {
		return C.getValue();
	}

	public void setAlpha(double alpha) {
		Alpha.setValue(alpha);
	}

	public double getAlpha() {
		return Alpha.getValue();
	}

	public void setBeta(double beta) {
		Beta.setValue(beta);
	}

	public double getBeta() {
		return Beta.getValue();
	}

	public void setGamma(double gamma) {
		Gamma.setValue(gamma);
	}

	public double getGamma() {
		return Gamma.getValue();
	}

	private void computeVolume() {
		double c_a = Math.cos(getAlpha() * (Math.PI / 180));
		double c_b = Math.cos(getBeta() * (Math.PI / 180));
		double c_g = Math.cos(getGamma() * (Math.PI / 180));
		double powprod = c_a * c_a + c_b * c_b + c_g * c_g;
		Volume = getA() * getB() * getC() * Math.sqrt(1.0 - powprod + 2.0 * c_a * c_b * c_g);
	}

	public double getVolume() {
		computeVolume();
		return Volume;
	}

	private void computeOrthoMatrix() {
		double alpha = getAlpha() * (Math.PI / 180);
		double beta = getBeta() * (Math.PI / 180);
		double gamma = getGamma() * (Math.PI / 180);
		double V = getVolume();
		double m00 = getA();
		double m01 = getB() * Math.cos(gamma);
		double m02 = getC() * Math.cos(beta);
		double m10 = 0;
		double m11 = getB() * Math.sin(gamma);
		double m12 = (getC() / Math.sin(gamma)) * (Math.cos(alpha) - Math.cos(beta) * Math.cos(gamma));
		double m20 = 0;
		double m21 = 0;
		double m22 = V / (getA() * getB() * Math.sin(gamma));
		OrthoMatrix = new Mat33d(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}

	public final Mat33d getOrthoMatrix() {
		//if (cellChanged)
		computeOrthoMatrix();
		return OrthoMatrix;
	}

	private void computeFractMatrix() {
		double alpha = getAlpha() * (Math.PI / 180);
		double beta = getBeta() * (Math.PI / 180);
		double gamma = getGamma() * (Math.PI / 180);
		double V = getVolume();
		double m00 = 1 / getA();
		double m01 = -Math.cos(gamma) / (getA() * Math.sin(gamma));
		double m02 = ((getB() * getC()) / V) * ((Math.cos(alpha) * Math.cos(gamma) / Math.sin(gamma)) - (Math.cos(beta) / Math.sin(beta)));
		double m10 = 0;
		double m11 = 1 / (getB() * Math.sin(gamma));
		double m12 = ((getA() * getC()) / (V * Math.sin(gamma))) * ((Math.cos(beta) * Math.cos(gamma)) - Math.cos(alpha));
		double m20 = 0;
		double m21 = 0;
		double m22 = (getA() * getB() * Math.sin(gamma)) / V;
		FractMatrix = new Mat33d(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}

	public final Mat33d getFractMatrix() {
		//if (cellChanged)
		computeFractMatrix();
		return FractMatrix;
	}

}
