/**
 * 
 */
package org.sepr.anchovy.Components;

import org.sepr.anchovy.InfoPacket;

/**
 * @author Harrison Spain
 *
 */
public class Pump extends Component {

	/**
	 * @param name
	 */
	public Pump(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sepr.anchovy.Components.Component#getInfo()
	 */
	@Override
	public InfoPacket getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.sepr.anchovy.Components.Component#calucalte()
	 */
	@Override
	public void calucalte() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.sepr.anchovy.Components.Component#calculateOutputFlowRate()
	 */
	@Override
	protected double calculateOutputFlowRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.sepr.anchovy.Components.Component#takeInfo(org.sepr.anchovy.InfoProposal)
	 */
	@Override
	public void takeInfo(InfoPacket info) throws Exception {
		// TODO Auto-generated method stub

	}

}
