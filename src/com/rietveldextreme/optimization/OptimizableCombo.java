package com.rietveldextreme.optimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OptimizableCombo extends AbstractOptimizable implements Optimizable {

	private List<Optimizable> items = null;
	private Optimizable activeItem = null;
	
	public OptimizableCombo() {
		items = new ArrayList<Optimizable>();
	}

	@Override
	public List<? extends Optimizable> getNodes() {
		if (getActiveItem() == null)
			return Collections.emptyList();
		return getActiveItem().getNodes();
	}

	public List<Optimizable> getItems() {
		return items;
	}

	public void setActiveItem(Optimizable active) {
		if (items.contains(active))
			activeItem = active;
	}

	public Optimizable getActiveItem() {
		checkActiveItem();
		return activeItem;
	}

	public void addItem(Optimizable item) {
		items.add(item);
	}

	public void removeItem(Optimizable item) {
		items.remove(item);
		checkActiveItem();
	}

	private void checkActiveItem() {
		if (activeItem == null || !items.contains(activeItem))
			if (items.size() != 0)
				activeItem = items.get(0);
			else
				activeItem = null;
	}

}
