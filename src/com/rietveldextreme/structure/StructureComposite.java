
package com.rietveldextreme.structure;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;

/**
 * @version $Revision: 1.00 $, $Date: 28-gen-2004 17.05.50
 * @author Mauro Bortolotti
 * @since JDK1.1
 */

public abstract class StructureComposite extends StructureComponent {

	private List<StructureComponent> componentList = new ArrayList<StructureComponent>();

	public StructureComposite() {
	}
	
	public StructureComposite(StructureComponent parent) {
		super(parent);
	}

	public final void addChild(StructureComponent component) {
		/** retrieves the component absolute coordinates matrix **/
		RealMatrix absCoordsMatrix = component.getDirectTransformation();
		/** Removes the component from the previous parent **/
		StructureComponent previous_parent = component.getParent();
		if (previous_parent != null)
			previous_parent.removeChild(component);
		componentList.add(component);
		component.setParent(this);
		/** updates the component local coordinates matrix **/
		RealMatrix relCoordsMatrix = new LUDecompositionImpl(getDirectTransformation()).getSolver().getInverse().multiply(absCoordsMatrix);
		component.getCoordinatesTransformation().setTransformationMatrix(relCoordsMatrix);
	}

	public void removeChild(StructureComponent component) {
		componentList.remove(component);
		component.setParent(null);
	}

	public final StructureComponent getChild(int position) {
		return componentList.get(position);
	}

	public final int getChildrenNumber() {
		return componentList.size();
	}
	
	public Iterator<StructureComponent> getComponentIterator() {
		return componentList.iterator();
	}

}
