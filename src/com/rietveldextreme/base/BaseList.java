package com.rietveldextreme.base;

import java.util.ArrayList;
import java.util.List;


// TODO Make use of this class to provide generic IBase objects list functionality
public class BaseList extends AbstractBase {
	
	public static final String BASELIST_TYPE = "List";

	private List<IBase> items = null;
	
	@Deprecated
	private IBase activeItem = null;
	
	public BaseList() {
		items = new ArrayList<IBase>();
	}

	@Override
	public String getClassID() {
		return BASELIST_TYPE;
	}

	@Override
	public List<? extends IBase> getNodes() {
		return items;
	}

	public List<IBase> getItems() {
		return items;
	}

	public void addItem(IBase item) {
		items.add(item);
	}

	@Deprecated
	public void setActiveItem(IBase active) {
		if (items.contains(active))
			activeItem = active;
	}

	@Deprecated
	public IBase getActiveItem() {
		checkActiveItem();
		return activeItem;
	}

	@Deprecated
	private void checkActiveItem() {
		if (activeItem == null || !items.contains(activeItem))
			if (items.size() != 0)
				activeItem = items.get(0);
			else
				activeItem = null;
	}

}
