package org.rexpd.core.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * IObservable implementation offering generic, 
 * global message dispatching functionality
 *
 */
public class MessageService implements IObservable {

	private List<Observer> observers = null;
	private static MessageService service = new MessageService();

	private MessageService() {
		observers = new ArrayList<Observer>();
	}

	/**
	 * Static method returning the singleton instance 
	 * 
	 * @return the project instance
	 */
	public static MessageService getInstance() {
		return service;
	}

	@Override
	public void addObserver(Observer o) {
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
