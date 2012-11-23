package org.rexpd.core.utils;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public abstract class Config {

	public static final URL getURL(String pluginID, String relPath) throws IOException {
		Bundle bundle = Platform.getBundle(pluginID);
		if (bundle == null)
			return null;
		URL url = FileLocator.find(bundle, new Path(relPath), null);
		return FileLocator.toFileURL(url);
	}

}
