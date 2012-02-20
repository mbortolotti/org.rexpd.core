/*
 * Created on 24-mag-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rietveldextreme.utils.Observer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author mauro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Observable implements IObservable {

	private ArrayList<Observer> observers = null;

	public Observable() {
		observers = new ArrayList<Observer>();
	}

	@Override
	public void attach(Observer o) {
		if (!observers.contains(o))
			observers.add(o);
	}

	@Override
	public void detach(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notify(Object message) {
		for (Iterator<Observer> it = observers.iterator(); it.hasNext();)
			it.next().update(this, message);
	}

}
