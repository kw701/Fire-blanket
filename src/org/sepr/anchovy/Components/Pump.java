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
		return 0; // TODO should be based on the RPM of the pump
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

}
