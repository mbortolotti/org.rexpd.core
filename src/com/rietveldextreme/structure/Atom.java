package com.rietveldextreme.structure;

import static java.lang.Math.PI;

import java.util.ArrayList;
import java.util.List;

import com.rietveldextreme.structure.PeriodicTable;
import com.rietveldextreme.optimization.Optimizable;
import com.rietveldextreme.optimization.Parameter;

/**
 * @version $Revision: 1.00 $, $Date: 28-gen-2004 10.42.10
 * @author Mauro Bortolotti
 * @since JDK1.1
 */

public class Atom extends StructureComposite {

	public static final String ATOM_TAG = "Atom";
	public static final String ATOMIC_NUMBER_TAG = "Z";
	public static final String ATOM_SYMBOL_TAG = "symbol";

	public static final String FRACT_COORDS_CHANGED = "FRACT_COORDS_CHANGED";
	public static final String POSITION_CHANGED = "POSITION_CHANGED";


	private ChemicalElement element;

	public double charge = 0.0;
	
	private Parameter x_fract;
	private Parameter y_fract;
	private Parameter z_fract;
	private Parameter occupancy;
	private Parameter isoUFactor;
	private AnisoUFactors anisoUFactors;
	Structure.UfactorsMode uFactorsMode;

	private boolean atomCoordinatesUsed = true;

	// rendering properties

	static final double SCALE_RADIUS = 2.0;
	public static final String X_FRACT = "X (fract.)";
	public static final String Y_FRACT = "Y (fract.)";
	public static final String Z_FRACT = "Z (fract.)";


	public Atom(StructureComponent parent, double x, double y, double z, int Z) {
		setComponentType(ATOM_TAG);
		setAtomicNumber(Z);
		setLabel(getElement().symbol);
		addNode(x_fract = new Parameter(this, X_FRACT, 0.0, 0.0, 1.0, false, true));
		addNode(y_fract = new Parameter(this, Y_FRACT, 0.0, 0.0, 1.0, false, true));
		addNode(z_fract = new Parameter(this, Z_FRACT, 0.0, 0.0, 1.0, false, true));
		addNode(occupancy = new Parameter(this, "Occupancy", 1.0, 0.0, 1.0, false, true));
		isoUFactor = new Parameter(this, "isotropic U factor", 0.050, 0.0, 1.0, false, true);
		anisoUFactors = new AnisoUFactors();
		uFactorsMode = Structure.UfactorsMode.UFACTOR_ISO;
		setParent(parent);
		setFractCoordinates(new Coord3d(x, y, z));
		//parent.addChild(this);
	}

	public Atom(StructureComponent parent, double x, double y, double z, String symbol) {
		this(parent, x, y, z, PeriodicTable.getZNumberBySymbol(symbol));
	}

	public Atom(StructureComponent parent, double x, double y, double z) {
		this(parent, x, y, z, 1);
	}

	public Atom(StructureComponent parent, int Z) {
		this(parent, 0.0, 0.0, 0.0, Z);
	}

	public Atom(StructureComponent parent) {
		this(parent, 0.0, 0.0, 0.0, 1);
	}

	public Atom(StructureComponent parent, String symbol) {
		this(parent, 0.0, 0.0, 0.0, symbol);
	}
	
	@Override
	public List<Optimizable> getNodes() {
		List<Optimizable> nodes = new ArrayList<Optimizable>();
		nodes.addAll(super.getNodes());
		if (uFactorsMode == Structure.UfactorsMode.UFACTOR_ISO)
			nodes.add(isoUFactor);
		else
			nodes.add(anisoUFactors);
		return nodes;
	}

	public void setParent(StructureComponent parent) {
		super.setParent(parent);
		// if the main structure is not the direct parent enables hierarchical framework
		if (parent != null)
			setAtomCoordinatesUsed(parent instanceof Structure);
		Structure structure = getParentStructure();
		if (structure != null)
			setUFactorsMode(structure.getUFactorsMode());
	}

	public void setType(ChemicalElement elem) {
		element = elem;
	}

	public ChemicalElement getElement() {
		return element;
	}

	public void setElement(ChemicalElement elem) {
		element = elem;
	}

	public double getOccupancy() {
		return occupancy.getValue();
	}

	public void setOccupancy(double o) {
		occupancy.setValue(o);
	}

	public void setUFactorsMode(Structure.UfactorsMode mode) {
		uFactorsMode = mode;
	}

	public double getIsoUFactor() {
		return isoUFactor.getValue();
	}

	public void setIsoUFactor(double U) {
		isoUFactor.setValue(U);
	}
	
	public void setIsoBFactor(double B) {
		isoUFactor.setValue(B / (8 * PI * PI) );
	}

	public AnisoUFactors getAnisoUFactors() {
		return anisoUFactors;
	}

	public int getAtomicNumber() {
		return element.atomic_number;
	}

	public void setAtomicNumber(int Z) {
		ChemicalElement element = PeriodicTable.getElementByZNumber(Z);
		if (element != null)
			setType(element);
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}

	public double getCharge() {
		return charge;
	}

	public Structure getParentStructure() {
		StructureComponent current_component = this;
		while ((current_component = current_component.getParent()) != null)
			if (current_component instanceof Structure)
				return (Structure) current_component;
		return null;
	}

	public void setAtomCoordinatesUsed(boolean useAtomCoords) {
		if (isAtomCoordinatesUsed() == useAtomCoords)
			return; // no need to do anything
		if (useAtomCoords) { // currently using hierarchical framework - asked to switch to local
			Coord3d ortho = getOrthoCoordinates();
			setOrthoCoordinates(ortho);
			x_fract.setEnabled(true);
			y_fract.setEnabled(true);
			z_fract.setEnabled(true);
		} else { // currently using local coordinates - asked to switch to hierarchical framework
			Coord3d ortho = getOrthoCoordinates();
			super.getCoordinatesTransformation().setPosition(ortho.getX(), ortho.getY(), ortho.getZ());
			x_fract.setEnabled(false);
			y_fract.setEnabled(false);
			z_fract.setEnabled(false);
		}
		atomCoordinatesUsed = useAtomCoords;
	}

	public boolean isAtomCoordinatesUsed() {
		return atomCoordinatesUsed;
	}

	public CoordinatesTransformation getCoordinatesTransformation() {
		if (isAtomCoordinatesUsed()) { // we need to manually update the transformation matrix
			Coord3d ortho = getOrthoCoordinates();
			CoordinatesTransformation transformation = super.getCoordinatesTransformation();
			transformation.setPosition(ortho.getX(), ortho.getY(), ortho.getZ());
		}
		return super.getCoordinatesTransformation();
	}

	public void setOrthoCoordinates(Coord3d coordinates) {
		if (!atomCoordinatesUsed)
			return; // coordinates are managed by the hierarchical framework
		Structure phase = getParentStructure();
		if (phase == null)
			return;
		Coord3d fractCoordinates = phase.computeFractCoordinates(coordinates);
		setXFract(fractCoordinates.getX());
		setYFract(fractCoordinates.getY());
		setZFract(fractCoordinates.getZ());
	}

	public Coord3d getOrthoCoordinates() {
		if (atomCoordinatesUsed) {
			double x_fract = getXFract();
			double y_fract = getYFract();
			double z_fract = getZFract();
			if (getParentStructure() == null)
				return new Coord3d(0.0, 0.0, 0.0);
			return getParentStructure().computeOrthoCoordinates(x_fract, y_fract, z_fract);
		}
		return getPosition(getParentStructure());
	}

	public double getXFract() {
		return x_fract.getValue();
	}

	public double getYFract() {
		return y_fract.getValue();
	}

	public double getZFract() {
		return z_fract.getValue();
	}

	public void setXFract(double x) {
		x_fract.setValue(x);
	}

	public void setYFract(double y) {
		y_fract.setValue(y);
	}

	public void setZFract(double z) {
		z_fract.setValue(z);
	}

	public void setFractCoordinates(Coord3d coordinates) {
		if (!atomCoordinatesUsed)
			return; // coordinates are managed by the hierarchical framework
		setXFract(coordinates.getX());
		setYFract(coordinates.getY());
		setZFract(coordinates.getZ());
		// fractCoordinatesChanged = true;
	}

	public Coord3d getFractCoordinates() {
		if (atomCoordinatesUsed)
			return new Coord3d(getXFract(), getYFract(), getZFract());
		Coord3d ortho = getPosition(getParentStructure());
		return getParentStructure().computeFractCoordinates(ortho);
	}

	@Override
	public String toString() {
		return "atom symbol = " + getType() + " label = " + getLabel();
	}
	

}
