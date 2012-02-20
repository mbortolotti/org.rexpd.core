package com.rietveldextreme.structure;


public abstract class StructureComponentFactory {
	
	public static StructureComponent getStructureComponent(String type, StructureComponent parent) {
		if (type.equals(Cell.CELL_TAG))
			return new Cell((StructureComponent) parent);
		if (type.equals(Atom.ATOM_TAG))
			return new Atom(parent);
		if (type.equals(Bond.BOND_TAG))
			return new Bond(parent);
		if (type.equals(Fragment.FRAGMENT_TAG))
			return new Fragment(parent);
		return null;
	}

}
