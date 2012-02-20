package com.rietveldextreme.optimization;

import java.util.ArrayList;
import java.util.List;

import com.rietveldextreme.serialization.AbstractSerializable;


public abstract class AbstractOptimizable extends AbstractSerializable implements Optimizable {

	List<Optimizable> nodes = null;
	Optimizable parent = null;
	
	public AbstractOptimizable() {
		this(null);
	}
	
	public AbstractOptimizable(Optimizable parent) {
		this.parent = parent;
		nodes = new ArrayList<Optimizable>();
	}

	@Override
	public List<? extends Optimizable> getNodes() {
		List<Optimizable> temp = new ArrayList<Optimizable>();
		temp.addAll(nodes);
		return temp;
	}

	public Optimizable getParentNode() {
		return parent;
	}
	
	public void addNode(Optimizable node) {
		nodes.add(node);
	}

}
