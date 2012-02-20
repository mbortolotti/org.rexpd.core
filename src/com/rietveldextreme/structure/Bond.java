
package com.rietveldextreme.structure;


/**
 * @version $Revision: 1.00 $, $Date: 28-gen-2004 10.42.39 * @author Mauro Bortolotti * @since JDK1.1
 */

public class Bond extends StructureComposite {
	
	public static final String BOND_TAG = "Bond";
	public static final String ATOM1_TAG = "Atom1";
	public static final String ATOM2_TAG = "Atom2";

	private Atom atom1 = null;
	private Atom atom2 = null;

	public Bond(StructureComponent parent, Atom atom1, Atom atom2) {
		super(parent);
		setComponentType(BOND_TAG);
		this.atom1 = atom1;
		this.atom2 = atom2;
		setLabel(atom1.getLabel() + " - " + atom2.getLabel());
	}
	
	public Bond(StructureComponent parent) {
		super(parent);
		atom1 = new Atom(parent);
		atom2 = new Atom(parent);
		setComponentType(BOND_TAG);
	}
	
	public Atom getAtom1() {
		return atom1;
	}
	
	public Atom getAtom2() {
		return atom2;
	}

	public void setAtom1(Atom a1) {
		atom1 = a1;
	}
	
	public void setAtom2(Atom a2) {
		atom2 = a2;
	}
	
	public Atom getOther(Atom atom) {
		if (atom == atom1) return atom2;
		if (atom == atom2) return atom1;
		return null;
	}
	
	public boolean contains(Atom atom) {
		return (atom == atom1 || atom == atom2);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		Bond other = (Bond) obj;
		if (((other.atom1 == atom1) && (other.atom2 == atom2)) 
			|| ((other.atom1 == atom2) && (other.atom2 == atom1)))
			return true;
		return false;
	}

	
}
