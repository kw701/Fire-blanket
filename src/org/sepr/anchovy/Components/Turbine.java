package org.sepr.anchovy.Components;

import java.util.ArrayList;
import java.util.Iterator;

import org.sepr.anchovy.InfoPacket;
import org.sepr.anchovy.Pair;
import org.sepr.anchovy.Pair.Label;

public class Turbine extends Component {
	private double RPM;
	private double RPMRatio = 0.5; //Ratio governing how much of the steam flow transfers to rpm
	
	public Turbine(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public InfoPacket getInfo() {
		InfoPacket info = super.getSuperInfo();
		info.namedValues.add(new Pair<Double>(Label.RPMs, RPM));
		return info;
	}

	@Override
	public void calucalte() {
		RPM = calculateRPM();
		super.setOuputFlowRate(calculateOutputFlowRate());
	}
	protected double calculateRPM(){
		//RPM is proportional to the input flow rate of steam into the turbine.
		return getTotalInputFlowRate() / RPMRatio;
	}

	private double getTotalInputFlowRate() {
		ArrayList<Component> inputs = super.getRecievesInputFrom();
		Iterator<Component> it = inputs.iterator();
		Component c = null;
		
		double totalIPFL = 0;
		while(it.hasNext()){
			c = it.next();
			totalIPFL =+ c.getOutputFlowRate();
		}
		return totalIPFL;
	}
	
	@Override
	protected double calculateOutputFlowRate() {
		//OutputFlowRate = input flow rate
		return getTotalInputFlowRate();
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
			case RPMs:
				RPM = (Double) pair.second();
			default:
				break;
			}
		}
	}

	public double getRPM() {
		return RPM;
	}

	public void setRPM(double rPM) {
		RPM = rPM;
	}

	public double getRPMRatio() {
		return RPMRatio;
	}

	public void setRPMRatio(double rPMRatio) {
		RPMRatio = rPMRatio;
	}

}
