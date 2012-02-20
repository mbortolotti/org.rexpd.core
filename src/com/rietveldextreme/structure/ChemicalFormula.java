/*
 * Created on Mar 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rietveldextreme.structure;

import java.util.HashMap;
import java.util.Set;

/**
 * @author mauro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ChemicalFormula {
    
    public static final String CHEMICAL_FORMULA_CHANGED = "CHEMICAL_FORMULA_CHANGED";
    
    private HashMap<String, Double> formulaValues;
    
    public ChemicalFormula() {
        formulaValues = new HashMap<String, Double>();
    }
    
    public void addValue(String element, double value) {
        formulaValues.put(element, new Double(value));
    }
    
    public void Clear() {
        formulaValues.clear();
    }
    
    public double getValue(String Element) {
        double value = 0.0;
        Object val = formulaValues.get(Element);
        if (val != null)
            value = ((Double)val).doubleValue();
        return value;
    }
    
    public String[] getElements() {
        Set<String> keyset = formulaValues.keySet();
        return (String[]) keyset.toArray();
    }

}
