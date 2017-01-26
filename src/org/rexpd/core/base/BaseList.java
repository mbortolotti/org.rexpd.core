package org.rexpd.core.base;

import java.util.ArrayList;
import java.util.List;


// Provides generic IBase objects list functionality
public class BaseList<T extends IBase> extends AbstractBase {

	public static final String LIST = "List";

	private List<T> itemsList = null;
	private T activeItem = null;

	private boolean autoLabel = true;

	public BaseList() {
		itemsList = new ArrayList<T>();
	}

	public BaseList(List<T> items) {
		if (items != null) {
			itemsList = items;
			if (itemsList.size() > 0)
				activeItem = items.get(0);
		}
	}

	@Override
	public List<T> getNodes() {
		return itemsList;
	}

	public void addItem(T item) {
		itemsList.add(item);
		reLabel();
	}

	public void removeItem(T item) {
		itemsList.remove(item);
		reLabel();
	}

	public int getItemCount() {
		return itemsList.size();
	}

	public void setActiveItem(T active) {
		if (itemsList.contains(active))
			activeItem = active;
	}

	public T getActiveItem() {
		return activeItem;
	}
	
	private void reLabel() {
		if (autoLabel)
			for (int ni = 0; ni < itemsList.size(); ni++)
				itemsList.get(ni).setLabel(Integer.toString(ni+1));
	}

}
