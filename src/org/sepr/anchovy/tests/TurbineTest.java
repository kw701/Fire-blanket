package org.sepr.anchovy.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.sepr.anchovy.InfoPacket;
import org.sepr.anchovy.Pair;
import org.sepr.anchovy.Pair.Label;
import org.sepr.anchovy.Components.Turbine;

public class TurbineTest {
	private Turbine turbine1;
	private InfoPacket info;
	@Before
	public void setUp() throws Exception {
		turbine1 = new Turbine("Turbine 1");
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Turbine 1"));
		info.namedValues.add(new Pair<Double>(Label.RPMs, 30.0));
	}

	@Test
	public void testTakeInfo() {
		try {
			turbine1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(turbine1.getRPM() == 30.0);
	}
	

}
