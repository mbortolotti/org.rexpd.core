package com.rietveldextreme.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rietveldextreme.structure.SpaceGroup;
import com.rietveldextreme.structure.SpaceGroupTable;

public class SpaceGroupTest {

	@Test
	public void testFromSymbol() {
		for (SpaceGroup sg : SpaceGroupTable.spaceGroupsList())
			assertEquals(sg.hall(), SpaceGroup.fromSymbol(sg.hermann_mauguin()).hall());
	}

	@Test
	public void testFromITNumber() {
		fail("Not yet implemented");
	}

}
