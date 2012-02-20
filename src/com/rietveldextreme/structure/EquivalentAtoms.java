package com.rietveldextreme.structure;


import java.util.ArrayList;


public class EquivalentAtoms {
	
	private Atom baseAtom = null;
	private ArrayList<Atom> equivalentAtoms = null;
	
	public EquivalentAtoms(Atom baseAtom) {
		this.baseAtom = baseAtom;
		equivalentAtoms = new ArrayList<Atom>();
		EquivalentPositionCalculator epCalculator = EquivalentPositionCalculatorFactory.getDefaultCalculator();
		int Z = baseAtom.getAtomicNumber();
		Structure phase = baseAtom.getParentStructure();
		Coord3d fc = baseAtom.getFractCoordinates();
		Coord3d[] equivalentCoordinates = epCalculator.computeEquivalentFractionalCoordinates(phase, fc.getX(), fc.getY(), fc.getZ());
		for (int nep = 0; nep < equivalentCoordinates.length; nep++) {
			double x_fract_eq = equivalentCoordinates[nep].getX();
			double y_fract_eq = equivalentCoordinates[nep].getY();
			double z_fract_eq = equivalentCoordinates[nep].getZ();
			Atom eq_atom = new Atom(phase, x_fract_eq, y_fract_eq, z_fract_eq, Z);
			equivalentAtoms.add(eq_atom);
		}
	}
	
	public Atom getBaseAtom() {
		return baseAtom;
	}
	
	public Atom getAtom(int position) {
		return equivalentAtoms.get(position);
	}

	public int getEquivalentAtomsNumber() {
		return equivalentAtoms.size();
	}

}
