package org.rexpd.core.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public abstract class AbstractBase implements IBase {
	
	private String type = "";
	private String label = "";
	private String ID = null;
	private boolean enabled = true;
	private boolean visible = true; // TODO - consider pulling up to IBase
	
	IBase parent = null;
	protected List<IBase> nodes = null;
	
	public AbstractBase() {
		this(null);
	}
	
	public AbstractBase(IBase p) {
		parent = p;
		createUID();
		nodes = new ArrayList<IBase>();
	}
	
	@Override
	public String getClassID() {
		return getClass().getSimpleName();
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String t) {
		type = t;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String l) {
		label = l;
	}
	
	@Override
	public String getUID() {
		if (ID == null || ID.equals(""))
			createUID();
		return ID;
	}
	
	@Override
	public void setUID(String uid) {
		ID = uid;
	}
	
	private void createUID() {
		ID = UUID.randomUUID().toString();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean vis) {
		visible = vis;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	// TODO: recursively enable/disable sub-nodes
	public void setEnabled(boolean en) {
		enabled = en;
	}
	
	@Override
	public IBase getParentNode() {
		return parent;
	}
	
	@Override
	public void addNode(IBase node) {
		nodes.add(node);
	}

	@Override
	public List<? extends IBase> getNodes() {
		List<IBase> temp = new ArrayList<IBase>();
		temp.addAll(nodes);
		return temp;
	}

}
