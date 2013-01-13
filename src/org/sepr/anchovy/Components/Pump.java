/**
 * 
 */
package org.sepr.anchovy.Components;

import java.util.Iterator;

import org.sepr.anchovy.InfoPacket;
import org.sepr.anchovy.Pair;
import org.sepr.anchovy.Pair.Label;

/**
 * @author Harrison
 *
 */
public class Pump extends Component {
	

	private double RPM;
	private double pumpFlowRatio = 0.5; // The ratio to which the ouput flow rate is proportional to the RPM of the pump
	
	/**
	 * @param name the unique name of the component
	 */
	public Pump(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.sepr.anchovy.Components.Component#getInfo()
	 */
	@Override
	public InfoPacket getInfo() {
		InfoPacket info = super.getSuperInfo();
		info.namedValues.add(new Pair<Double> (Label.RPMs, RPM));
		return null;
	}

	/* (non-Javadoc)
	 * @see org.sepr.anchovy.Components.Component#calucalte()
	 */
	@Override
	public void calucalte() {
		super.setOuputFlowRate(calculateOutputFlowRate());
	}

	/* (non-Javadoc)
	 * @see org.sepr.anchovy.Components.Component#calculateOutputFlowRate()
	 */
	@Override
	protected double calculateOutputFlowRate() {
		//The output flow rate of the pump is directly proportional to the RPM of the pump.
		return RPM * getPumpFlowRatio(); 
	}

	/* (non-Javadoc)
	 * @see org.sepr.anchovy.Components.Component#takeInfo(org.sepr.anchovy.InfoProposal)
	 */
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
			case RPMs:
				RPM = (Double) pair.second();
			default:
				break;
			}
		}
	}

	public double getPumpFlowRatio() {
		return pumpFlowRatio;
	}

	public void setPumpFlowRatio(double pumpFlowRatio) {
		this.pumpFlowRatio = pumpFlowRatio;
	}
	
	public double getRPM() {
		return RPM;
	}

	public void setRPM(double rPM) {
		RPM = rPM;
	}

}
