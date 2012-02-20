
package com.rietveldextreme.structure;

import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;



/**
 * @version $Revision: 1.00 $, $Date: 28-gen-2004 17.27.47
 * @author Mauro Bortolotti
 * @since JDK1.1
 */
public class Fragment extends StructureComposite {
	
	public static final String FRAGMENT_TAG = "Fragment";
	
	private double Radius = 0.0;

	public Fragment(StructureComponent parent) {
		super(parent);
		setComponentType(FRAGMENT_TAG);
	}
	
	public double getRadius() {
		return Radius;
	}
	
	public void setRadius(double r) {
		Radius = r;
	}
	
	public void setPivotPoint(Coord3d pivot) {
		Coord3d oldPivot = getPosition();
		Vec3d delta = pivot.subtract(oldPivot);
		double[][] trans = { 
				{ 1, 0, 0, delta.getX0() }, 
				{ 0, 1, 0, delta.getX1() }, 
				{ 0, 0, 1, delta.getX2() }, 
				{ 0, 0, 0, 1 } };
		// the new absolute coordinates matrix
		RealMatrix absCoordsMatrix = getDirectTransformation().multiply(MatrixUtils.createRealMatrix(trans));
		for (int nc = 0; nc < getChildrenNumber(); nc++) {
			StructureComponent component = getChild(nc);
			RealMatrix absInverse = new LUDecompositionImpl(absCoordsMatrix).getSolver().getInverse();
			RealMatrix relCoordsMatrix = absInverse.multiply(component.getDirectTransformation());
			component.getCoordinatesTransformation().setTransformationMatrix(relCoordsMatrix);
		}
		getCoordinatesTransformation().setTransformationMatrix(absCoordsMatrix);
	}

}

