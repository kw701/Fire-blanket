package org.sepr.anchovy.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sepr.anchovy.*;
import org.sepr.anchovy.Pair.Label;
import org.sepr.anchovy.Components.*;

public class GameEngineTest {
	GameEngine gameEngine = null;
	Valve valve1 = null;
	ArrayList<InfoPacket> v1Info = null;
	
	@Before
	public void setUp() throws Exception {
		gameEngine = new GameEngine();
		valve1 = new Valve("Valve 1");
		v1Info = new ArrayList<InfoPacket>();
		gameEngine.addComponent(valve1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddComponent() {
		
		ArrayList<InfoPacket> info = gameEngine.getAllComponentInfo();
		v1Info.add(valve1.getInfo());
		assert(v1Info.equals(info));
		
	}
	
	@Test
	public void testSetComponentInfo(){
		InfoPacket info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Valve 1"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		
		try {
			valve1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			gameEngine.assignInfoToComponent(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
