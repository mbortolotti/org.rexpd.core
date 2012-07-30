package com.rietveldextreme.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class BaseList extends AbstractBase {

	private List<IBase> items = null;
	private IBase activeItem = null;
	
	public BaseList() {
		items = new ArrayList<IBase>();
	}

	@Override
	public String getClassID() {
		return getClass().getSuperclass().getSimpleName();
	}

	@Override
	public List<? extends IBase> getNodes() {
		if (getActiveItem() == null)
			return Collections.emptyList();
		return getActiveItem().getNodes();
	}

	public List<IBase> getItems() {
		return items;
	}

	public void setActiveItem(IBase active) {
		if (items.contains(active))
			activeItem = active;
	}

	public IBase getActiveItem() {
		checkActiveItem();
		return activeItem;
	}

	public void addItem(IBase item) {
		items.add(item);
	}

	private void checkActiveItem() {
		if (activeItem == null || !items.contains(activeItem))
			if (items.size() != 0)
				activeItem = items.get(0);
			else
				activeItem = null;
	}

}
