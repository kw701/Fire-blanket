package org.sepr.anchovy.Components;

import java.util.Iterator;

import org.sepr.anchovy.InfoPacket;
import org.sepr.anchovy.Pair;
import org.sepr.anchovy.Pair.Label;

public class Reactor extends Component {
	private double temperature;
	private double pressure;
	private double controlRodLevel;
	private double waterLevel;
	
	public Reactor(String name){
		super(name);
	}

	@Override
	public InfoPacket getInfo() {
		InfoPacket info = getSuperInfo();
		info.namedValues.add(new Pair<Double>(Label.temp, temperature));
		info.namedValues.add(new Pair<Double>(Label.pres, pressure));
		info.namedValues.add(new Pair<Double>(Label.coRL, controlRodLevel));
		info.namedValues.add(new Pair<Double>(Label.wLvl, waterLevel));
		return info;
	}

	@Override
	public void calucalte() {
		temperature = calculateTemperature();
		pressure = calcuatePressure();
		waterLevel = calculateWaterLevel();
		super.setOuputFlowRate(calculateOutputFlowRate());
	}
	protected double calculateTemperature(){
		// TODO work out what this calculation should do
		return 0;
	}
	protected double calcuatePressure(){
		// TODO work out what this calculation should do
		return 0;
	}
	protected double calculateWaterLevel(){
		// TODO work out what this calculation should do
		return 0;	
	}
	@Override
	protected double calculateOutputFlowRate(){
		// TODO work out what this calculation should do
		return 0;
	}
	@Override
	public void takeInfo(InfoPacket info) throws Exception {
		super.takeSuperInfo(info);
		Iterator<Pair<?>> i = info.namedValues.iterator();
		Pair<?> pair = null;
		Label label = null;
		while(i.hasNext()){
			pair = i.next();
			label = pair.getLabel();
			switch (label){
			case temp:
				temperature = (Double) pair.second();
				break;
			case pres:
				pressure = (Double) pair.second();
				break;
			case coRL:
				controlRodLevel = (Double) pair.second();
				break;
			case wLvl:
				waterLevel = (Double) pair.second();
				break;
			default:
				// should this do anything by default?
			}
		}
	}
}
