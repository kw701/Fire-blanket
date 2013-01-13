package org.sepr.anchovy.Components;

import java.util.ArrayList;
import java.util.Iterator;

import org.sepr.anchovy.InfoPacket;
import org.sepr.anchovy.Pair;
import org.sepr.anchovy.Pair.Label;

public class Reactor extends Component {
	private double temperature;
	private double pressure;
	private double controlRodLevel;
	private double waterLevel = 50;
	
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
		double oldTemp = temperature;
		temperature = calculateTemperature();
		pressure = calcuatePressure(oldTemp);
		waterLevel = calculateWaterLevel();
		super.setOuputFlowRate(calculateOutputFlowRate());
	}
	//Pressure = Temperature * constant <- Pressure Temperature law
	protected double calculateTemperature(){
		//The temperature is affected by the level of the control rods, current temperature.
		//Higher control rod level the hotter it gets.
		double t = temperature;
		if(t > 100){
			t = t + t * ((controlRodLevel-50)/2); //If boiling lowering control rod level past 50% decreases temp otherwise it increases.
		}else{
			t = t + t * ((controlRodLevel-5)/2); //If not boiling then control rod increases temp unless fully down
		}
		return t;
	}
	protected double calcuatePressure(double oldTemp){
		// calculate pressure reletive to the current/old temperature
		double p = pressure;
		double ratio = temperature/oldTemp;
		p = p * ratio;
		return p;
	}
	protected double calculateWaterLevel(){
		//proportional to current water level + opfl and ipfl
		double inputFlowRate = 0;
		ArrayList<Component> inputs = super.getRecievesInputFrom();
		Iterator<Component> it = inputs.iterator();
		Component c = null;
		while(it.hasNext()){
			c = it.next();
			inputFlowRate += c.getOutputFlowRate();
		}
		return (inputFlowRate + waterLevel) - super.getOutputFlowRate();
	}
	@Override
	protected double calculateOutputFlowRate(){
		// OPFL is proportional to the pressure. 
		return pressure / 2;
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

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getControlRodLevel() {
		return controlRodLevel;
	}

	public void setControlRodLevel(double controlRodLevel) {
		this.controlRodLevel = controlRodLevel;
	}

	public double getWaterLevel() {
		return waterLevel;
	}

	public void setWaterLevel(double waterLevel) {
		this.waterLevel = waterLevel;
	}
	
}
