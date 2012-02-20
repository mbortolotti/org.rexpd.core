package com.rietveldextreme.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Structure extends StructureComposite {

	public static enum UfactorsMode {
		UFACTOR_ISO {
			public String toString() {
				return "Isotropic";
			}
		},
		UFACTOR_ANISO {
			public String toString() {
				return "Anisotropic";
			}
		}
	}

	public static boolean HierarchicalFrameworkEnabled = false;

	protected Cell cell = null;
	protected SpaceGroup spaceGroup = SpaceGroup.fromSymbol("P 1");

	BoundingBox boundingBox = new BoundingBox(0.0 - BB_TOL, 0.0 - BB_TOL, 0.0 - BB_TOL, 1.0 + BB_TOL, 1.0 + BB_TOL, 1.0 + BB_TOL);
	private Structure.UfactorsMode uFactorMode = Structure.UfactorsMode.UFACTOR_ISO;
	private String description = "";
	private String nameCommon = "";
	private String formulaSum = "";
	static final double BB_TOL = 0.01; // bounding box tolerance

	public Structure(StructureComponent parent) {
		super(parent);
		setCell(new Cell(this));
		setSpaceGroup(SpaceGroup.fromSymbol("P 1"));
	}
	
	public Structure(Cell cell, SpaceGroup space_group) {
		super(null);
		setCell(cell);
		setSpaceGroup(space_group);
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
		Mat33d orthoMatrix = cell.getOrthoMatrix();
		Vec3d topBBVertex = Mat33d.Product(orthoMatrix, new Vec3d(1.0, 1.0, 1.0));
		boundingBox = new BoundingBox(0.0 - BB_TOL, 0.0 - BB_TOL, 0.0 - BB_TOL, 
				topBBVertex.getX0() + BB_TOL, 1.0 + BB_TOL, 1.0 + BB_TOL);
	}

	public SpaceGroup getSpaceGroup() { 
		return spaceGroup;
	}

	public void setSpaceGroup(SpaceGroup sgroup) { 
		if (sgroup == null)
			return;
		spaceGroup = sgroup;
		cell.checkCellParameters(spaceGroup.getCrystalSystem());
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public final Coord3d computeOrthoCoordinates(double x_fract, double y_fract,
			double z_fract) {
		Mat33d orthoMatrix = getCell().getOrthoMatrix();
		Vec3d orthoCoords = Mat33d.Product(orthoMatrix, new Coord3d(x_fract, y_fract, z_fract));
		return new Coord3d(orthoCoords);
	}

	public final Coord3d computeFractCoordinates(double x_ortho, double y_ortho,
			double z_ortho) {
		Mat33d fractMatrix = getCell().getFractMatrix();
		Vec3d fractCoords = Mat33d.Product(fractMatrix, new Coord3d(x_ortho, y_ortho, z_ortho));
		return new Coord3d(fractCoords);
	}

	public final Coord3d computeOrthoCoordinates(Coord3d fractCoords) {
		return computeOrthoCoordinates(fractCoords.getX(), fractCoords.getY(), fractCoords.getZ());
	}

	public final Coord3d computeFractCoordinates(Coord3d orthoCoords) {
		return computeFractCoordinates(orthoCoords.getX(), orthoCoords.getY(), orthoCoords.getZ());
	}

	public List<Atom> getAtomListASU() {
		return getAtomList();
	}

	public static List<Atom> getAtomList(StructureComponent base) {
		List<StructureComponent> componentList = base.getFullComponentList();
		List<Atom> atomList = new ArrayList<Atom>();
		for (StructureComponent component : componentList)
			if (component instanceof Atom)
				atomList.add((Atom) (component));
				return atomList;
	}

	public List<Atom> getAtomList() {
		return getAtomList(this);
	}

	public static List<Bond> getBondsList(StructureComponent base) {
		List<StructureComponent> componentList = base.getFullComponentList();
		List<Bond> bondList = new ArrayList<Bond>();
		for (StructureComponent component : componentList)
			if (component instanceof Bond)
				bondList.add((Bond) (component));
				return bondList;
	}

	public List<Bond> getBondListASU() {
		return getBondsList(this);
	}

	public List<Fragment> getFragmentListASU() {
		List<StructureComponent> componentList = getFullComponentList();
		List<Fragment> fragmentList = new ArrayList<Fragment>();
		for (Iterator<StructureComponent> it = componentList.iterator(); it.hasNext();) {
			StructureComponent component = (StructureComponent) it.next();
			if (component instanceof Fragment)
				fragmentList.add((Fragment) (component));
		}
		return fragmentList;
	}

	/**
	 * Given a base coordinate set, populates the crystal bounding box with the equivalent positions
	 * @param basePosition
	 * @param btmVertex
	 * @param topVertex
	 * @return
	 */
	public final ArrayList<Coord3d> expandCoordinates(Coord3d basePosition, Coord3d btmVertex, Coord3d topVertex) {
		ArrayList<Coord3d> expandedCoordinates = new ArrayList<Coord3d>();
		int n_cell_xmin = (int) Math.floor(btmVertex.getX());
		int n_cell_xmax = (int) Math.ceil(topVertex.getX());
		int n_cell_ymin = (int) Math.floor(btmVertex.getY());
		int n_cell_ymax = (int) Math.ceil(topVertex.getY());
		int n_cell_zmin = (int) Math.floor(btmVertex.getZ());
		int n_cell_zmax = (int) Math.ceil(topVertex.getZ());
		for (int n_cellx = n_cell_xmin; n_cellx <= n_cell_xmax; n_cellx++){
			for (int n_celly = n_cell_ymin; n_celly <= n_cell_ymax; n_celly++){
				for (int n_cellz = n_cell_zmin; n_cellz <= n_cell_zmax; n_cellz++){
						double x_base = basePosition.getX();
						double y_base = basePosition.getY();
						double z_base = basePosition.getZ();
						double x_new = x_base + (double)n_cellx;
						double y_new = y_base + (double)n_celly;
						double z_new = z_base + (double)n_cellz;
						if ((x_new < btmVertex.getX()) || (x_new > topVertex.getX()) || (y_new < btmVertex.getY())
							|| (y_new > topVertex.getY()) || (z_new < btmVertex.getZ()) || (z_new > topVertex.getZ()))
						continue;
					expandedCoordinates.add(new Coord3d(x_new, y_new, z_new));
				}
			}
		}
		return expandedCoordinates;
	}

	/**
	 * Populates the given crystal volume with the symmetrically equivalent atoms
	 * 
	 * @param btmVertex the bottom vertex coordinates of the volume of interest
	 * @param topVertex the top vertex coordinates of the volume of interest
	 * @return
	 */
	public ArrayList<Atom> getAtomListExpanded(Coord3d btmVertex, Coord3d topVertex) {
		ArrayList<Atom> atomListFull = new ArrayList<Atom>();
		List<Atom> atomListASU = getAtomListASU();
		for (int na = 0; na < atomListASU.size(); na++) {
			Atom atom = atomListASU.get(na);
			int Z = atom.getAtomicNumber();
			Coord3d fractCoords = atom.getFractCoordinates();
			EquivalentPositionCalculator calculator = EquivalentPositionCalculatorFactory.getDefaultCalculator();
			Coord3d[] fractCoordsEq = calculator.computeEquivalentFractionalCoordinates(this, fractCoords.getX(),
					fractCoords.getY(), fractCoords.getZ());
			// Fill the crystal volume of interest
			for (int nep = 0; nep < fractCoordsEq.length; nep++) {
				ArrayList<Coord3d> fractCoordsExp = expandCoordinates(fractCoordsEq[nep], btmVertex, topVertex);
				for (int nexp = 0; nexp < fractCoordsExp.size(); nexp++) {
					double x_fract_eq = fractCoordsExp.get(nexp).getX();
					double y_fract_eq = fractCoordsExp.get(nexp).getY();
					double z_fract_eq = fractCoordsExp.get(nexp).getZ();
					Atom eq_atom = new Atom(this, x_fract_eq, y_fract_eq, z_fract_eq, Z);
					atomListFull.add(eq_atom);
				}
			}
		}
		return atomListFull;
	}

	public ArrayList<Atom> getAtomListExpanded() {
		Coord3d btmBBVertex = new Coord3d(0.0 - BB_TOL, 0.0 - BB_TOL, 0.0 - BB_TOL); // bounding box lower corner
		Coord3d topBBVertex = new Coord3d(1.0 + BB_TOL, 1.0 + BB_TOL, 1.0 + BB_TOL); // bounding box upper corner
		return getAtomListExpanded(btmBBVertex, topBBVertex);
	}

	public ArrayList<Atom> getAtomListFull() {
		List<Atom> atomListASU = getAtomListASU();
		ArrayList<Atom> atomListFull = new ArrayList<Atom>();
		for (Atom baseAtom : atomListASU) {
			EquivalentPositionCalculator epCalculator = EquivalentPositionCalculatorFactory.getDefaultCalculator();
			int Z = baseAtom.getAtomicNumber();
			Coord3d fc = baseAtom.getFractCoordinates();
			Coord3d[] equivalentCoordinates = epCalculator.computeEquivalentFractionalCoordinates(this, fc.getX(), fc.getY(), fc.getZ());
			for (int nep = 0; nep < equivalentCoordinates.length; nep++) {
				double x_fract_eq = equivalentCoordinates[nep].getX();
				double y_fract_eq = equivalentCoordinates[nep].getY();
				double z_fract_eq = equivalentCoordinates[nep].getZ();
				Atom eq_atom = new Atom(this, x_fract_eq, y_fract_eq, z_fract_eq, Z);
				atomListFull.add(eq_atom);
			}
		}
		return atomListFull;
	}

	/**
	 * Normalize orthogonal coordinates so that 0 <= x < a, 0 <= y < b, 0 <= z < c
	 * @param baseCoordinates
	 * @return
	 */
	public final Coord3d constrainInsideCellOrtho(Coord3d baseCoordinates) {
		double x_coord = baseCoordinates.getX();
		double y_coord = baseCoordinates.getY();
		double z_coord = baseCoordinates.getZ();
		while(x_coord < 0.0)
			x_coord += getCell().getA();
		while(x_coord >= getCell().getA())
			x_coord -= getCell().getA();
		while(y_coord < 0.0)
			y_coord += getCell().getB();
		while(y_coord >= getCell().getB())
			y_coord -= getCell().getB();
		while(z_coord < 0.0)
			z_coord += getCell().getC();
		while(z_coord >= getCell().getC())
			z_coord -= getCell().getC();
		return new Coord3d(x_coord, y_coord, z_coord);
	}

	/** 
	 * Populates the given crystal volume with the symmetrically equivalent structural components
	 * Mainly used for 3-d rendering and force field calculations
	 * 
	 * @param btmVertex
	 * @param topVertex
	 * @return
	 */
	public ArrayList<ASUBlock> getStructureExpanded(Coord3d btmVertex, Coord3d topVertex) {
	
		List<Atom> atomListASU = getAtomListASU();
		List<Bond> bondListASU = getBondListASU();
		List<Fragment> fragmentListASU = getFragmentListASU();
		ArrayList<EquivalentAtoms> eqAtomsList = new ArrayList<EquivalentAtoms>();
		ArrayList<ASUBlock> asuBlocks = new ArrayList<ASUBlock>();
	
		// Given the space group symmetry, generate the full cell atom list
		for (int na = 0; na < atomListASU.size(); na++) {
			Atom asuAtom = atomListASU.get(na);
			EquivalentAtoms equivalentAtoms = new EquivalentAtoms(asuAtom);
			eqAtomsList.add(equivalentAtoms);
		}
	
		// check out if it is possible to draw the whole molecule
		for (int na = 0; na < atomListASU.size(); na++) {
			if (eqAtomsList.get(na).getEquivalentAtomsNumber() != eqAtomsList.get(0).getEquivalentAtomsNumber())
				return asuBlocks;
		}
	
		int nASUBlocks = (atomListASU.size() == 0) ? 1 : eqAtomsList.get(0).getEquivalentAtomsNumber();
		for (int nasu = 0; nasu < nASUBlocks; nasu++) {
			ASUBlock asuBlock = new ASUBlock();
			
			/** Fill the ASU blocks with the equivalent atoms **/
			
			for (int na = 0; na < atomListASU.size(); na++)
				asuBlock.getAtomList().add(eqAtomsList.get(na).getAtom(nasu));
			Coord3d old_center = Structure.getGeometricalCenter(asuBlock.getAtomList());
			Coord3d new_center = constrainInsideCellOrtho(old_center);
			//Coordinates new_center = old_center;
			double x_offset = new_center.getX() - old_center.getX();
			double y_offset = new_center.getY() - old_center.getY();
			double z_offset = new_center.getZ() - old_center.getZ();
			//System.out.println("old_center: " + old_center);
			//System.out.println("new_center: " + new_center);
			for (int na = 0; na < asuBlock.getAtomList().size(); na++) {
				Atom atom = asuBlock.getAtomList().get(na);
				Coord3d old_coords = atom.getPosition();
				double x_new = old_coords.getX() + x_offset;
				double y_new = old_coords.getY() + y_offset;
				double z_new = old_coords.getZ() + z_offset;
				atom.setOrthoCoordinates(new Coord3d(x_new, y_new, z_new));
			}
			
			/** Fill the ASU block with the equivalent bonds **/
			
			for (int nb = 0; nb < bondListASU.size(); nb++) {
				Bond bondASU = bondListASU.get(nb);
				Atom atom1ASU = bondASU.getAtom1();
				Atom atom2ASU = bondASU.getAtom2();
				int atom1Index = -1;
				int atom2Index = -1;
				for (int na = 0; na < atomListASU.size(); na++) {
					if (atomListASU.get(na) == atom1ASU)
						atom1Index = na;
					else if (atomListASU.get(na) == atom2ASU)
						atom2Index = na;
				}
				if ((atom1Index == -1) || (atom2Index == -1))
					continue;
				Atom atom1 = asuBlock.getAtomList().get(atom1Index);
				Atom atom2 = asuBlock.getAtomList().get(atom2Index);
				asuBlock.getBondList().add(new Bond(null, atom1, atom2));
			}
			
			/** Fill the ASU block with the equivalent fragments **/
			
			for (int nf = 0; nf < fragmentListASU.size(); nf++) {
				Fragment fragmentASU = fragmentListASU.get(nf);
				List<Atom> fragmentAtomListASU = getAtomList(fragmentASU);
				int[] atomIndices = new int[fragmentAtomListASU.size()];
				for (int nfa = 0; nfa < fragmentAtomListASU.size(); nfa++)
					for (int na = 0; na < atomListASU.size(); na++)
						if (fragmentAtomListASU.get(nfa) == atomListASU.get(na))
							atomIndices[nfa] = na;
				ArrayList<Atom> fragmentAtomList = new ArrayList<Atom>();
				Fragment fragment = new Fragment(null);
				for (int nfa = 0; nfa < fragmentAtomListASU.size(); nfa++)
					fragmentAtomList.add(asuBlock.getAtomList().get(atomIndices[nfa]));
				Coord3d fragmentCenter = Structure.getGeometricalCenter(fragmentAtomList);
				double fragmentRadius = Structure.getMeanMolecularRadius(fragmentAtomList);
				fragment.setPosition(fragmentCenter);
				fragment.setRadius(fragmentRadius);
				asuBlock.getFragmentList().add(fragment);
			}
			asuBlocks.add(asuBlock);
		}
	
		return asuBlocks;
	}

	public void setUFactorsMode(Structure.UfactorsMode mode) {
		uFactorMode = mode;
		for (Atom atom : getAtomListASU())
			atom.setUFactorsMode(mode);
	}

	public Structure.UfactorsMode getUFactorsMode() {
		return uFactorMode;
	}

	public double getDensity() {
		double mass = 0.0;
		ArrayList<Atom> atoms = getAtomListFull();
		for (int na = 0; na < atoms.size(); na++)
			mass += atoms.get(na).getElement().mass;
		return mass/0.6022/getCell().getVolume();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		description = desc;
	}

	public String getNameCommon() {
		return nameCommon;
	}

	public void setNameCommon(String name) {
		nameCommon = name;
	}

	public String getFormulaSum() {
		return formulaSum;
	}

	public void setFormulaSum(String formula) {
		formulaSum = formula;
	}

	public void setHierarchicalFrameworkEnabled(boolean enabled) {
		HierarchicalFrameworkEnabled = enabled;
		if (HierarchicalFrameworkEnabled) {
			for (Iterator<Atom> it = getAtomListASU().iterator(); it.hasNext();)
				it.next().setAtomCoordinatesUsed(false);
		}
	}

	public void checkBonds() {
		for (Bond bond : getBondListASU()) {
			StructureComponent comp1 = findChildComponentByName(bond.getAtom1().getLabel());
			if (comp1 != null && comp1 instanceof Atom)
				bond.setAtom1((Atom) comp1);
			StructureComponent comp2 = findChildComponentByName(bond.getAtom2().getLabel());
			if (comp2 != null && comp2 instanceof Atom)
				bond.setAtom2((Atom) comp2);
		}
	}

	public boolean isHierarchicalFrameworkEnabled() {
		return HierarchicalFrameworkEnabled;
	}

	public static Coord3d getGeometricalCenter(List<Atom> atoms) {
		double x_c = 0.0;
		double y_c = 0.0;
		double z_c = 0.0;
		int natoms = atoms.size();
		for (int na = 0; na < natoms; na++) {
			Coord3d coords = atoms.get(na).getPosition();
			x_c += coords.getX();
			y_c += coords.getY();
			z_c += coords.getZ();
		}
		return new Coord3d(x_c / natoms, y_c / natoms, z_c / natoms);
	}

	public static double getMeanMolecularRadius(List<Atom> atoms) {
		double mmr = 0.0;
		Coord3d geoCenter = getGeometricalCenter(atoms);
		int ncomponents = atoms.size();
		for (int na = 0; na < ncomponents; na++) {
			Coord3d coords = atoms.get(na).getPosition();
			mmr += Coord3d.distance(geoCenter, coords);
		}
		return mmr / ncomponents;
	}

	/**
	 *  Returns a List of all atoms connected to the given atom
	 *
	 *@param  atom  Base atom
	 *@return       List containing all connected atoms
	 */
	public static List<Atom> getConnectedAtomList(StructureComponent base, Atom atom)
	{
		List<Atom> atomsList = new ArrayList<Atom>();
		List<Bond> bondsList = getBondsList(base);
		for (Bond bond : bondsList)
			if (bond.contains(atom)) atomsList.add(bond.getOther(atom));
				return atomsList;
	}

	/**
	 *  Returns a List of all Bonds connected to the given atom
	 *
	 *@param  atom  Base atom
	 *@return       List containing all connected bonds
	 */
	public static List<Bond> getConnectedBondList(StructureComponent base, Atom atom)
	{
		List<Bond> allBondsList = getBondsList(base);
		List<Bond> bondsList = new ArrayList<Bond>();
		for (Bond bond : allBondsList)
			if (bond.contains(atom)) bondsList.add(bond);
				return bondsList;
	} 

	private static List<Bond> traverse(StructureComponent base, Atom atom, List<Bond> bondList) {
		List<Bond> connectedBonds = getConnectedBondList(base, atom);
		for (Bond aBond : connectedBonds) {
			if (bondList.contains(aBond))
				continue;
			bondList.add(aBond);
			Atom nextAtom = aBond.getOther(atom);
			if (getConnectedAtomList(base, nextAtom).size() == 1)
				continue;
			traverse(base, nextAtom, bondList);
		}
		return bondList;
	}

	public static List<Molecule> splitMolecules(StructureComponent base) {
		List<Molecule> molecules = new ArrayList<Molecule>();
		List<Atom> atomList = getAtomList(base);
		for (Atom atom : atomList) {
			List<Bond> traversed = new ArrayList<Bond>();
			traverse(base, atom, traversed);
			Molecule molecule = new Molecule(base);
			for (Bond bond : traversed) {
				molecule.addChild(bond);
				molecule.addChild(bond.getAtom1());
				molecule.addChild(bond.getAtom2());
			}
			if (molecule.getComponentIterator().hasNext())
				molecules.add(molecule);
		}
		for (int nm = 0; nm < molecules.size(); nm++) {
			System.out.println("Molecule n. " + nm);
			for (Iterator<StructureComponent> it = molecules.get(nm).getComponentIterator(); it.hasNext();)
				System.out.println("\t" + it.next());
		}

		return molecules;
	}
	
}
