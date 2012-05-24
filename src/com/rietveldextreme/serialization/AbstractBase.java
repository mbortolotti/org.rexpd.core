package com.rietveldextreme.serialization;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.rietveldextreme.optimization.Optimizable;



public class AbstractBase implements IBase {
	
	private String type;
	private String label;
	private String ID = null;
	
	IBase parent = null;
	protected List<Optimizable> nodes = null;
	
	public AbstractBase() {
		this(null);
	}
	
	public AbstractBase(IBase p) {
		parent = p;
		createUID();
		setType("");
		setLabel("");
		nodes = new ArrayList<Optimizable>();
	}
	
	public void addNode(Optimizable node) {
		nodes.add(node);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String t) {
		type = t;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String l) {
		label = l;
	}
	
	public String getUID() {
		if (ID == null || ID.equals(""))
			createUID();
		return ID;
	}
	
	public void setUID(String uid) {
		ID = uid;
	}
	
	private void createUID() {
		ID = UUID.randomUUID().toString();
	}
	
	public IBase getParentNode() {
		return parent;
	}

	@Override
	public List<? extends Optimizable> getNodes() {
		List<Optimizable> temp = new ArrayList<Optimizable>();
		temp.addAll(nodes);
		return temp;
	}

}
