/*
 * Created on 3-giu-2004
 *
 * TODO To change the  for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rietveldextreme.utils.Observer;

/**
 * @author mauro
 *
 * The IObservable interface should be implemented by classes that participate
 * into the "Observer" pattern as the sources of property changes
 *
 * @version $Revision: 1.0 $, $Date: 3-giu-2004 17.32.18 $
 * @author mauro
 * @since JDK1.4
 */

public interface IObservable {
	
	public void attach(Observer o);
	public void detach(Observer o);
	public void notify(Object message);
	
}
