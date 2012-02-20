/*
 * Created on 9-ago-2004
 *
 * TODO To change the  for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rietveldextreme.structure;

import com.rietveldextreme.utils.CCTBXNative;


public class EquivalentPositionCalculatorCCTBX extends EquivalentPositionCalculator {
	
	static {
		try {
			System.loadLibrary("rex");
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Failed to load rex native library");
		}
	}

	public Coord3d[] computeEquivalentFractionalCoordinates(Structure structure, double x, double y, double z) {
		double a = structure.getCell().getA();
		double b = structure.getCell().getB();
		double c = structure.getCell().getC();
		double alpha = structure.getCell().getAlpha();
		double beta = structure.getCell().getBeta();
		double gamma = structure.getCell().getGamma();
		String space_group_hall = structure.getSpaceGroup().hall();
		double[] eq_coords_array = CCTBXNative.computeEquivalentPositions(a, b, c, alpha, beta, gamma, space_group_hall, x, y, z);
		int n_eq_pos = eq_coords_array.length/3;
		Coord3d[] equivalentCoordinates = new Coord3d[n_eq_pos];
		for (int nc = 0; nc < n_eq_pos; nc++) {
			double x_eq = eq_coords_array[3*nc];
			double y_eq = eq_coords_array[3*nc + 1];
			double z_eq = eq_coords_array[3*nc + 2];
			equivalentCoordinates[nc] = new Coord3d(x_eq, y_eq, z_eq);
		}
		return equivalentCoordinates;
	}
	
	
}
