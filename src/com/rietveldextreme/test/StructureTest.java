package com.rietveldextreme.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.rietveldextreme.structure.SpaceGroup;
import com.rietveldextreme.structure.Structure;

public class StructureTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSetSpaceGroup() {
		Structure structure = new Structure(null);
		structure.setSpaceGroup(SpaceGroup.fromSymbol("P 21 1 1"));
		assertEquals(structure.getSpaceGroup().hall(), "P 2xa");
		fail("Not yet implemented");
	}

	@Test
	public void testComputeOrthoCoordinatesDoubleDoubleDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testComputeFractCoordinatesDoubleDoubleDouble() {
		fail("Not yet implemented");
	}

}
