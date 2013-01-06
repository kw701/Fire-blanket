package org.sepr.anchovy.Components;

import java.util.Iterator;

import org.sepr.anchovy.InfoPacket;
import org.sepr.anchovy.Pair;
import org.sepr.anchovy.Pair.Label;

public class Condenser extends Component {
	private double temperature;
	private double pressure;
	private double waterLevel;
	public Condenser(String name) {
		super(name);
	}

	@Override
	public InfoPacket getInfo() {
		InfoPacket info = super.getSuperInfo();
		info.namedValues.add(new Pair<Double>(Label.temp, temperature));
		info.namedValues.add(new Pair<Double>(Label.pres, pressure));
		info.namedValues.add(new Pair<Double>(Label.wLvl, waterLevel));
		return info;
	}

	@Override
	public void calucalte() {
		temperature = calculateTemp();
		pressure = calculatePressure();
		waterLevel = calculateWaterLevel();
		super.setOuputFlowRate(calculateOutputFlowRate());

	}
	protected double calculateTemp(){
		return 0;
	}
	protected double calculatePressure(){
		return 0;
	}
	protected double calculateWaterLevel(){
		return 0;
	}

	@Override
	protected double calculateOutputFlowRate() {
		// TODO Auto-generated method stub
		// Must distinguish between a pump that pumps steam in with a pump that pumps coolent round (possibly by name of pump having collent or alike in)
		return 0;
	}

	@Override
	public void takeInfo(InfoPacket info) throws Exception {
		super.takeSuperInfo(info);
		Iterator<Pair<?>> it = info.namedValues.iterator();
		Pair<?> pair = null;
		Label label = null;
		while(it.hasNext()){
			pair = it.next();
			label = pair.getLabel();
			switch(label){
			case temp:
				temperature = (Double) pair.second();
				break;
			case pres:
				pressure = (Double) pair.second();
				break;
			case wLvl:
				waterLevel = (Double) pair.second();
			default:
				break;
			}
		}
		

	}

}
