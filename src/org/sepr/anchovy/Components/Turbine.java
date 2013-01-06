package org.sepr.anchovy.Components;

import java.util.Iterator;

import org.sepr.anchovy.InfoPacket;
import org.sepr.anchovy.Pair;
import org.sepr.anchovy.Pair.Label;

public class Turbine extends Component {
	private double RPM;
	
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
		return 0;
	}
	@Override
	protected double calculateOutputFlowRate() {
		// TODO Auto-generated method stub
		//the output flow rate of the turbine is the RPM
		return RPM;
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

}
