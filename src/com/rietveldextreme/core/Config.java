/*
 * Created on Apr 4, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rietveldextreme.core;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;


public class Config {

	public static String getFullPath(String relPath) throws IOException {
		if (Activator.getDefault() == null)
			return relPath;
		Bundle bundle = Activator.getDefault().getBundle();
		URL url = FileLocator.find(bundle, new Path(relPath), Collections.EMPTY_MAP);
		return FileLocator.toFileURL(url).getPath();
	}

}
