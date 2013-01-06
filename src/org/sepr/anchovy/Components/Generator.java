package org.sepr.anchovy.Components;

import java.util.ArrayList;
import java.util.Iterator;

import org.sepr.anchovy.InfoPacket;
import org.sepr.anchovy.Pair;
import org.sepr.anchovy.Pair.Label;

public class Generator extends Component {
	private double electrisityGenerated = 0;
	
	public Generator(String name){
		super(name);
	}
	
	@Override
	public InfoPacket getInfo() {
		InfoPacket info = super.getSuperInfo();
		info.namedValues.add(new Pair<Double>(Label.elec, electrisityGenerated));
		return info;
	}

	@Override
	public void calucalte() {
		super.setOuputFlowRate(calculateOutputFlowRate());
		electrisityGenerated += super.getOutputFlowRate();
	}

	@Override
	/*
	 * The generator is the part of the power plant that generates the electricity
	 * The output flow rate of the generator is the amount of amount of electisity being generated per cycle.
	 * 
	 * @return amount of electricity generated in current cycle.
	 */
	protected double calculateOutputFlowRate() {
		ArrayList<Component> inputComponents = super.getRecievesInputFrom();
		double totalInputFlowRate = 0;
		Iterator<Component> it = inputComponents.iterator();
		Component comp = null;
		
		while(it.hasNext()){
			comp = it.next();
			totalInputFlowRate += comp.getOutputFlowRate();
		}
		
		return totalInputFlowRate * 1.5;
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
			case elec:
				electrisityGenerated = (Double) pair.second();
			}
		}

	}

}
