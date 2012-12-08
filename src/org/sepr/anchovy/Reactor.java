package org.sepr.anchovy;

import java.util.Iterator;
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
	public InfoProposal getInfo() {
		InfoProposal info = getSuperInfo();
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
		return 0;
	}
	protected double calcuatePressure(){
		return 0;
	}
	protected double calculateWaterLevel(){
		return 0;	
	}
	@Override
	protected double calculateOutputFlowRate(){
		return 0;
	}
	@Override
	public void takeInfo(InfoProposal info) {
		super.resetConections();
		Iterator<Pair<?>> i = info.namedValues.iterator();
		Pair<?> pair = null;
		Label label = null;
		while(i.hasNext()){
			pair = i.next();
			label = pair.getLabel();
			switch (label){
			case cNme:
				super.setName((String) pair.second());
			case falT:
				super.setFailureTime( (Double) pair.second());
			case oPto:
				super.connectToOutput((Component) pair.second());
			case rcIF:
				super.connectToInput((Component) pair.second());
			case OPFL:
				super.setOuputFlowRate((Double) pair.second());
			case temp:
				temperature = (Double) pair.second();
			case pres:
				pressure = (Double) pair.second();
			case coRL:
				controlRodLevel = (Double) pair.second();
			case wLvl:
				waterLevel = (Double) pair.second();
			default:
				
			
			}
		
		}
	}
}
