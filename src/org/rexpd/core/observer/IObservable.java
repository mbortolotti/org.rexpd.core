/*
 * Created on 3-giu-2004
 *
 * TODO To change the  for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.rexpd.core.observer;

/**
 * @author mauro
 *
 *         The IObservable interface should be implemented by classes that
 *         participate into the "Observer" pattern as the sources of property
 *         changes
 *
 * @version $Revision: 1.0 $, $Date: 3-giu-2004 17.32.18 $
 * @author mauro
 * @since JDK1.4
 */

public interface IObservable {

	public void addObserver(Observer o);

	public void removeObserver(Observer o);

	public void notifyObservers(Message msg);

}
