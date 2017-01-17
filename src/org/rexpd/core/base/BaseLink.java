package org.rexpd.core.base;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BaseLink<T extends IBase> implements IBase {

	public static final String LINK = "link";

	private T origin = null;
	private IBase parentNode = null;
	private String ID = null;
	private String linkID = null;
	private String classID = null;
	Class<? extends IBase> baseType = null;

	public BaseLink(T base) {
		ID = UUID.randomUUID().toString();
		setLink(base);
	}

	public T getLinked() {
		return origin;
	}

	public void setLink(T link) {
		origin = link;
		if (link != null) {
			linkID = link.getUID();
			classID = link.getClassID();
			baseType = link.getClass();
		}
	}

	public String getLinkID() {
		if (getLinked() != null)
			return getLinked().getUID();
		return linkID;
	}

	public void setLinkID(String id) {
		if (getLinked() != null)
			getLinked().setUID(id);
		else
			linkID = id;
	}

	public Class<? extends IBase> getBaseType() {
		if (getLinked() != null)
			return origin.getClass();
		return baseType;
	}

	@Override
	public String getClassID() {
		if (getLinked() != null)
			return getLinked().getClassID();
		return classID;
	}

	@Override
	public String getType() {
		if (getLinked() != null)
			return origin.getType();
		return null;
	}

	@Override
	public void setType(String type) {
		if (getLinked() != null)
			origin.setType(type);
	}

	@Override
	public String getLabel() {
		if (getLinked() != null)
			return origin.getLabel();
		return classID;
	}

	@Override
	public void setLabel(String label) {
		if (getLinked() != null)
			origin.setLabel(label);
	}

	@Override
	public String getUID() {
		return ID;
	}

	@Override
	public void setUID(String id) {
		ID = id;
	}

	@Override
	public boolean isEnabled() {
		if (getLinked() != null)
			return origin.isEnabled();
		return false;
	}

	@Override
	public void setEnabled(boolean en) {
		if (getLinked() != null)
			origin.setEnabled(en);
	}

	@Override
	public boolean isVisible() {
		if (getLinked() != null)
			return origin.isVisible();
		return false;
	}

	@Override
	public void setVisible(boolean en) {
		if (getLinked() != null)
			origin.setVisible(en);
	}

	@Override
	public IBase copy() throws InstantiationException {
		if (getLinked() != null)
			return origin.copy();
		throw new InstantiationException("Error: null linked object!");
	}

	@Override
	public IBase getParentNode() {
		if (getLinked() != null)
			return parentNode;
		return null;
	}

	@Override
	public void setParentNode(IBase parent) {
		if (getLinked() != null)
			parentNode = parent;
	}

	@Override
	public List<? extends IBase> getNodes() {
		if (getLinked() != null)
			return origin.getNodes();
		return Collections.emptyList();
	}

	@Override
	public void addNode(IBase node) {
		if (getLinked() != null)
			origin.addNode(node);
	}

	@Override
	public void addNode(int position, IBase node) {
		if (getLinked() != null)
			origin.addNode(position, node);
	}

	@Override
	public void removeNode(IBase node) {
		if (getLinked() != null)
			origin.removeNode(node);
	}

}
