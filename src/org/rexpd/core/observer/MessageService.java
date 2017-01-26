package org.rexpd.core.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * IObservable implementation offering generic, 
 * global message dispatching functionality
 *
 */
public class MessageService implements IObservable {

	private static List<Observer> observers = null;
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
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Message msg) {
		/** Avoid using iterators or the enhanced-for loop, as those cause ConcurrentModificationException */
		for (int no = 0; no < observers.size(); no++)
			observers.get(no).update(msg);
	}
	
//	public static void notify(IObservable sender, Message message) {
//		/** Avoid using iterators or the enhanced-for loop, as those cause ConcurrentModificationException */
//		for (int no = 0; no < observers.size(); no++)
//			observers.get(no).update(sender, message, null);
//	}

}
