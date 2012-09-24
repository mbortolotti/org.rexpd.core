/*
 * Created on 24-mag-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.rexpd.core.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author mauro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Observable implements IObservable {

	private List<Observer> observers = null;

	public Observable() {
		observers = new ArrayList<Observer>();
	}

	@Override
	public void addObserver(Observer o) {
	    if (o == null)
            throw new NullPointerException();
		if (!observers.contains(o))
			observers.add(o);
	}

	@Override
	public void deleteObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Object message) {
		for (Iterator<Observer> it = observers.iterator(); it.hasNext();)
			it.next().update(this, message);
	}

}
