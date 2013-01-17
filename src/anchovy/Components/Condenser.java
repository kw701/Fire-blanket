package anchovy.Components;

import java.util.ArrayList;
import java.util.Iterator;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Pair.Label;

/**
 * This class is the representation of the condesner within the power plant - This component recives steam from another component,
 * then depending on what the state the power plant condensed it back to water to use elsewhere in the power plant.
 * 
 * @author Harrison
 */
public class Condenser extends Component {
	private double temperature;
	private double pressure;
	private double waterLevel;
	/**
	 * Set up the power plant, most work is done in Component.
	 * @param name The name of the component.
	 */
	public Condenser(String name) {
		super(name);
	}
	
	public Condenser(String name, InfoPacket info){
		super(name, info);
		Pair<?> currentpair = null;
		Iterator<Pair<?>> pi = info.namedValues.iterator();
		Label currentlabel = null;
		while(pi.hasNext()){
			currentpair = pi.next();
			currentlabel = currentpair.getLabel();
			switch (currentlabel){
			case temp:
				temperature = (Double) currentpair.second();
				break;
			case pres:
				pressure = (Double) currentpair.second();
				break;
			case wLvl:
				waterLevel = (Double) currentpair.second();
				break;
			default:
				break;
			}
		}
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
		double oldPressure = pressure;
		pressure = calculatePressure();
		temperature = calculateTemp(oldPressure);
		waterLevel = calculateWaterLevel();
		super.setOuputFlowRate(calculateOutputFlowRate());

	}
	/**
	 * Calculate the temperature of the condenser, 
	 * Temperature = old temp * ratio that pressure increased or decreased by - the coolent flow rate
	 * @param oldPressure pressure before the last calculation of pressure.
	 * @return The new temperature.
	 */
	protected double calculateTemp(double oldPressure){
		//Temperature = old temp * pressure increase/decrease raito - coolent flow rate
		
		ArrayList<Component> inputs = super.getRecievesInputFrom();
		Iterator<Component> it = inputs.iterator();
		Component c = null;
		
		double totalCoolantFlowRate = 0;
		
		while(it.hasNext()){
			c = it.next();
			if(c.getName().contains("Coolant")){
				totalCoolantFlowRate += c.getOutputFlowRate();
			}
		}
		double ratio = pressure/oldPressure;
		return temperature * ratio - totalCoolantFlowRate;
	}
	
	/**
	 * Calculates the pressure within the condenser
	 * Pressure  = current pressure + input flow rate of steam - output flow rate of water.
	 * @return The new pressure
	 */
	protected double calculatePressure(){
		//The pressure of the condenser is the current pressure + input flow of steam - output flow of water.
		ArrayList<Component> inputs = super.getRecievesInputFrom();
		Iterator<Component> it = inputs.iterator();
		Component c = null;
		double totalInputFlowRate = 0;
		while(it.hasNext()){
			c = it.next();
			if(!(c.getName().contains("Coolant"))){
				totalInputFlowRate += c.getOutputFlowRate();
			}
		}
		if(temperature > 100){
			return pressure + totalInputFlowRate - super.getOutputFlowRate();
		}else{
			return (pressure-pressure/temperature) + totalInputFlowRate - super.getOutputFlowRate();
		}
	}
	/**
	 * Calculate the water level within the condenser
	 * water level = steam condensed + current water level - water flow rate out.
	 * @return The new water level.
	 */
	protected double calculateWaterLevel(){
		//Water level = steam condensed + water level - water out
		double wLevel;
		if(temperature > 100){
			wLevel = waterLevel - super.getOutputFlowRate();
		}else{
			wLevel = (waterLevel - super.getOutputFlowRate()) + pressure / 10;
		}
		return wLevel;
	}

	@Override
	protected double calculateOutputFlowRate() {
		// TODO Auto-generated method stub
		// Must distinguish between a pump that pumps steam in with a pump that pumps coolent round (possibly by name of pump having collent or alike in)
		ArrayList<Component> outputs = super.getOutputsTo();
		Iterator<Component> it = outputs.iterator();
		Component c = null;
		double totalOPFL = 0;
		while(it.hasNext()){
			c = it.next();
			if(!c.getName().contains("Coolant")){
				totalOPFL += c.getOutputFlowRate();
			}
		}
		
		return totalOPFL;
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
