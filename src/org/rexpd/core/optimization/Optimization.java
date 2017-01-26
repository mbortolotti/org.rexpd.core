package org.rexpd.core.optimization;

import java.util.List;

import org.rexpd.core.base.AbstractBase;
import org.rexpd.core.base.IBase;

public class Optimization extends AbstractBase {
	
	ParameterNode baseNode = null;
	
	public Optimization(IBase parent) {
		super(parent);
		addNode(baseNode = new ParameterNode(parent));
	}
	
	/**
	 * refresh the parameter state based on the updated model
	 */
//	public void refresh() {
//		ParameterNode newBaseNode = new ParameterNode(getParentNode());
//		/** retrieve the previously defined optimization state **/
//		List<Parameter> parameterList = Optimizations.getParameters(baseNode);
//		List<Parameter> newParameterList = Optimizations.getParameters(newBaseNode);
//		
//	}

}
