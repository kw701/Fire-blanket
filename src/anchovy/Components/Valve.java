/**
 * 
 */
package anchovy.Components;

import java.util.ArrayList;
import java.util.Iterator;

import anchovy.InfoPacket;
import anchovy.Pair;
import anchovy.Pair.Label;

/**
 * Representation of Valve withing the power plant.
 * @author Harrison
 */
public class Valve extends Component {
	private Boolean position;
	/**
	 * @param name The unique name of the component
	 */
	public Valve(String name) {
		super(name);
	}
	
	public Valve(String name, InfoPacket info) {
		super(name, info);
		Pair<?> currentpair = null;
		Iterator<Pair<?>> pi = info.namedValues.iterator();
		Label currentlabel = null;
		while(pi.hasNext()){
			currentpair = pi.next();
			currentlabel = currentpair.getLabel();
			switch (currentlabel){
			case psit:
				position = (Boolean) currentpair.second();
				break;
			default:
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see anchovy.Component#getInfo()
	 */
	@Override
	public InfoPacket getInfo() {
		InfoPacket info = super.getSuperInfo();
		info.namedValues.add(new Pair<Boolean>(Label.psit, position));
		return info;
	}

	/* (non-Javadoc)
	 * @see anchovy.Component#calucalte()
	 */
	@Override
	public void calucalte() {
		super.setOuputFlowRate(calculateOutputFlowRate());
	}

	/* (non-Javadoc)
	 * @see anchovy.Component#calculateOutputFlowRate()
	 */
	@Override
	/**
	 * Ouputut fow rate of valve is directly based of of the unput flow rate.
	 * If valve is open, output flow  = input flow
	 * otherwise the output flow = 0.
	 */
	protected double calculateOutputFlowRate() {
		if(position){
			ArrayList<Component> recievesInputFrom = super.getRecievesInputFrom();
			Iterator<Component> it = recievesInputFrom. iterator();
			Double inFlowRate = 0.0;
			Component comp = null;
			while (it.hasNext()){
				comp = it.next();
				inFlowRate += comp.getOutputFlowRate();
			}
			return inFlowRate;
		}else{
			return 0;
		}
		
	}
	

	public Boolean getPosition() {
		return position;
	}

	public void setPosition(Boolean position) {
		this.position = position;
	}

	/* (non-Javadoc)
	 * @see anchovy.Component#takeInfo(anchovy.InfoProposal)
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
			case psit:
				position = (Boolean) pair.second();
			default:
				break;
			}
		}
	}
	/*
	 * sandbox
	 */
	public static void main(String[] args){
		InfoPacket in = new InfoPacket();
		in.namedValues.add(new Pair<String>(Label.cNme, "Valve 1"));
		in.namedValues.add(new Pair<Double>(Label.falT, 1.23));
		in.namedValues.add(new Pair<Double>(Label.OPFL, 12.34));
		in.namedValues.add(new Pair<Boolean>(Label.psit, true));
		Valve v = new Valve("Valve 1");
		try {
			v.takeInfo(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Valve v2 = new Valve("V2");
		v2.setOuputFlowRate(1234.567);
		v2.connectToInput(v);
		v.connectToOutput(v2);
		System.out.println("Flow from v2: " + v2.getOutputFlowRate());
		System.out.println("Flow out from v: " + v.getOutputFlowRate());
		v.setPosition(false);
		System.out.println("Valve is now closed");
		v.calucalte();
		System.out.println("Flow out from v: " + v.getOutputFlowRate());
		
	}

}
