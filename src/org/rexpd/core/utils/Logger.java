package org.rexpd.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.rexpd.core.observer.IObservable;
import org.rexpd.core.observer.Message;
import org.rexpd.core.observer.Observer;

// TODO: this resembles too closely MessageService
public class Logger implements IObservable {
	
	List<Observer> observers;
	private static Logger service = new Logger();

	private Logger() {
		observers = new ArrayList<Observer>();
	}
	
	public static final void register(Observer observer) {
		
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
		for (int no = 0; no < observers.size(); no++)
			observers.get(no).update(msg);
	}

}
