package com.rietveldextreme.structure;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

import com.rietveldextreme.optimization.AbstractOptimizable;
import com.rietveldextreme.optimization.Optimizable;
import com.rietveldextreme.serialization.IBase;

/**
 * @version $Revision: 1.00 $, $Date: 28-gen-2004 10.08.13
 * @author Mauro Bortolotti
 * @since JDK1.1
 */
public abstract class StructureComponent extends AbstractOptimizable {

	public static final String STRUCTURE_COMPONENT_TAG = "StructureComponent";
	
	private StructureComponent parentComponent = null;
	private CoordinatesTransformation transformation = null;
	private List<Optimizable> optNodes = null;
	private String componentType = "";
	
	public StructureComponent() {
		this(null);
	}
	
	public StructureComponent(StructureComponent parent) {
		optNodes = new ArrayList<Optimizable>();
		transformation = new CoordinatesTransformation();
		setParent(parent);
	}
	
	@ Override
	public List<Optimizable> getNodes() {
		List<Optimizable> nodes = new ArrayList<Optimizable>();
		nodes.addAll(optNodes);
		for (int nc = 0; nc < getChildrenNumber(); nc++)
			nodes.add(getChild(nc));
		return nodes;
	}
	
	@ Override
	public void addNode(Optimizable opt) {
		optNodes.add(opt);
	}
	
	@ Override
	public IBase getParentNode() {
		return parentComponent;
	}

	public boolean isLeaf() {
		return (getChildrenNumber() == 0);
	}
	
	public StructureComponent getParent() {
		return parentComponent;
	}
	
	public void setParent(StructureComponent parent) {
		if (this != parent)
			parentComponent = parent;
	}

	public List<StructureComponent> getFullComponentList() {
		List<StructureComponent> fullComponentList = new ArrayList<StructureComponent>();
		fullComponentList.add(this);
		for (Iterator<StructureComponent> it = getComponentIterator(); it.hasNext();) {
			StructureComponent component = it.next();
			if (component instanceof StructureComposite)
				fullComponentList.addAll(((StructureComposite) component).getFullComponentList());
			else
				fullComponentList.add(component);
		}
		return fullComponentList;
	}
	
	public boolean contains(StructureComponent child) {
		List<StructureComponent> componentList = getFullComponentList();
		for (StructureComponent component : componentList)
			if (component == child)
				return true;
		return false;
	}
	
	public abstract Iterator<StructureComponent> getComponentIterator();
	
	public abstract void addChild(StructureComponent component);
	
	/**
	 * detach the component from the matrix and set the component's parent to null
	 * @param component the component to be removed
	 */
	public abstract void removeChild(StructureComponent component);
	public abstract int getChildrenNumber();
	public abstract StructureComponent getChild(int position);
	
	public CoordinatesTransformation getCoordinatesTransformation() {
		return transformation;
	}
	
	public void setCoordinatesTransformation(CoordinatesTransformation transformation) {
		this.transformation = transformation;
		/*for (int nc = 0; nc < getChildrenNumber(); nc++) {
			StructureComponent component = getChild(nc);
			Matrix absCoordsMatrix = component.getDirectTransformation();
			Matrix relCoordsMatrix = transformation.getTransformationMatrix().inverse().times(absCoordsMatrix);
			component.getCoordinatesTransformation().setTransformationMatrix(relCoordsMatrix);
		}*/
	}
	
	public void setComponentType(String type) {
		this.componentType = type;
	}

	public String getComponentType() {
		return componentType;
	}
	
	public StructureComponent findChildComponentByName(String name) {
		if (this.getLabel().equals(name))
			return this;
		for (int nc = 0; nc < getChildrenNumber(); nc++) {
			StructureComponent component = getChild(nc);
			if (component.getLabel().equals(name))
				return component;
			if (!component.isLeaf()) {
				StructureComponent child = component.findChildComponentByName(name);
				if (child != null)
					return child;
			}
		}
		return null;
	}
	
	/*public abstract Matrix getCoordinatesTransformationMatrix_OLD();
	
	public static ArrayList<Matrix> getTransformationList_OLD(StructureComponent start, StructureComponent end) {
		if ((start == null) || (end == null))
			return null;
		ArrayList<Matrix> transformationList = new ArrayList<Matrix>();
		StructureComponent current = end;
		while (true) {
			if (current == null)
				return null;
			Matrix transform = current.getCoordinatesTransformationMatrix_OLD();
			if (transform != null)
				transformationList.add(transform);
			if (current == start)
				break;
			current = current.getParent();
		}
		return transformationList;
	}*/
	
	public static List<RealMatrix> getTransformationList(StructureComponent start, StructureComponent end) {
		List<RealMatrix> transformationList = new ArrayList<RealMatrix>();
		if (end == null)
			return transformationList;
		StructureComponent current = end;
		while ((current != null) && (current != start)) {
			transformationList.add(current.getCoordinatesTransformation().getTransformationMatrix());
			current = current.getParent();
		}
		return transformationList;
	}
	
	public static RealMatrix getDirectTransformation(StructureComponent start, StructureComponent end) {
		RealMatrix matrix = MatrixUtils.createRealIdentityMatrix(4);
		List<RealMatrix> transformationList = getTransformationList(start, end);
		for (int nm = transformationList.size() - 1; nm >= 0; nm--)
			matrix = matrix.multiply(transformationList.get(nm));
		return matrix;
	}
	
	public static RealMatrix getInverseTransformation(StructureComponent start, StructureComponent end) {
		RealMatrix matrix = MatrixUtils.createRealIdentityMatrix(4);
		List<RealMatrix> transformationList = getTransformationList(start, end);
		for (int nm = 0; nm < transformationList.size(); nm++)
			matrix = matrix.multiply(transformationList.get(nm));
		return matrix;
	}
	
	public RealMatrix getDirectTransformation(StructureComponent start) {
		return getDirectTransformation(start, this);
	}
	
	public RealMatrix getInverseTransformation(StructureComponent start) {
		return getInverseTransformation(start, this);
	}
	
	public RealMatrix getDirectTransformation() {
		StructureComponent start = this;
		while (start.getParent() != null)
			start = start.getParent();
		return getDirectTransformation(start, this);
	}
	
	public RealMatrix getInverseTransformation() {
		StructureComponent start = this;
		while (start.getParent() != null)
			start = start.getParent();
		return getInverseTransformation(start, this);
	}
	
	/**
	 * Gets the absolute position of the component with respect to the given structure component parent
	 * @param baseParent
	 * @return
	 */
	public Coord3d getPosition(StructureComponent baseParent) {
		RealMatrix transformation = getDirectTransformation(baseParent);
		double X = transformation.getEntry(0, 3);
		double Y = transformation.getEntry(1, 3);
		double Z = transformation.getEntry(2, 3);
		return new Coord3d(X, Y, Z);
	}
	
	/**
	 * Gets the absolute position of the component with respect to the structure coordinate system
	 * @return
	 */
	public Coord3d getPosition() {
		StructureComponent baseParent = getParent();
		while (baseParent != null)
			baseParent = baseParent.getParent();
		return getPosition(baseParent);
	}
	
	/**
	 * Sets the absolute position of the component with respect to the structure coordinate system
	 * @param coords
	 */
	public void setPosition(Coord3d coords) {
		double[][] baseElements = { {1,0,0,coords.getX()}, {0,1,0,coords.getY()}, {0,0,1,coords.getZ()}, {0,0,0,1} };
		RealMatrix absolutePositionMatrix = MatrixUtils.createRealMatrix(baseElements);
		RealMatrix previousComponentsMatrix = (getParent() == null) ? 
				MatrixUtils.createRealIdentityMatrix(4) : getDirectTransformation(getParent());
		RealMatrix pInverse = new LUDecompositionImpl(previousComponentsMatrix).getSolver().getInverse();
		RealMatrix relativePositionMatrix = pInverse.multiply(absolutePositionMatrix);
		getCoordinatesTransformation().setTransformationMatrix(relativePositionMatrix);
	}

}
