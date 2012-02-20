package com.rietveldextreme.structure;

import java.util.ArrayList;

public class ASUBlock {
	
	private ArrayList<Atom> atoms;
	private ArrayList<Bond> bonds;
	private ArrayList<Fragment> fragments;
	
	public ASUBlock() {
		atoms = new ArrayList<Atom>();
		bonds = new ArrayList<Bond>();
		fragments = new ArrayList<Fragment>();
	}
	
	public ArrayList<Atom> getAtomList() {
		return atoms;
	}
	
	public ArrayList<Bond> getBondList() {
		return bonds;
	}
	
	public ArrayList<Fragment> getFragmentList() {
		return fragments;
	}

}
