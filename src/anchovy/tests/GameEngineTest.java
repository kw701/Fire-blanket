package anchovy.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import anchovy.*;
import anchovy.Components.*;
import anchovy.Pair.Label;

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
	public void testConnectComponents(){
		gameEngine.connectComponentTo(valve1, valve1, true);
		
		InfoPacket info = null;
		Iterator<InfoPacket> it = gameEngine.getAllComponentInfo().iterator();
		while(it.hasNext()){
			info = it.next();
			Iterator<Pair<?>> pIt = info.namedValues.iterator();
			while(pIt.hasNext()){
				if(pIt.next().getLabel() == Label.oPto){
					assertTrue(pIt.next().second() == "Valve 1");
				}else if(pIt.next().getLabel() == Label.rcIF){
					assertTrue(pIt.next().second() == "Valve 1");
				}
				
			}
			
			
		}
		
	}
	
	@Test
	public void testSetComponentInfo(){
		InfoPacket info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Valve 1"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		ArrayList<InfoPacket> plantList = new ArrayList<InfoPacket>();
		
		try {
			valve1.takeInfo(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			gameEngine.setupPowerPlantConfigureation(plantList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue("Valve 1"==valve1.getName());
		assertTrue(valve1.getPosition());
		assertTrue(12.34==valve1.getOutputFlowRate());
		
	}
	
	@Test
	public void testSetupPowerPlantConfigureation(){
		ArrayList<InfoPacket> infoList = new ArrayList<InfoPacket>();
		
		InfoPacket info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Valve 1"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<String>(Label.rcIF, "Valve 2"));
		info.namedValues.add(new Pair<String>(Label.oPto, "Valve 2"));
		infoList.add(info);
		
		info = new InfoPacket();
		info.namedValues.add(new Pair<String>(Label.cNme, "Valve 2"));
		info.namedValues.add(new Pair<Boolean>(Label.psit, true));
		info.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		info.namedValues.add(new Pair<String>(Label.oPto, "Valve 1"));
		info.namedValues.add(new Pair<String>(Label.rcIF, "Valve 1"));
		infoList.add(info);
		gameEngine.clearPowerPlant();
		gameEngine.setupPowerPlantConfigureation(infoList);
		assertTrue(gameEngine.getAllComponentInfo().isEmpty());
		
		
		
		//ArrayList<InfoPacket> allInfo =
		assertTrue(gameEngine.getAllComponentInfo().equals(infoList));
		
	}

}
