package org.sepr.anchovy;

import java.util.Random;

public abstract class Component {
	private String name;
	private int meanTimeBetweenFailure; //MTBF
	private Double failureTime;
	private Double outputFlowRate;
	
	public Component(String name){
		this.name = name;
		if(failureTime == null){
			calcRandomFailTime();
		}
		
	}
	
	/*
	 * Calculates the failure time of the component normaly distributed around the MTBF
	 */
	protected void calcRandomFailTime(){
		Random rand = new Random();
		failureTime = rand.nextGaussian()* 10 + meanTimeBetweenFailure;
	}
	
	/*
	 * repairs the component
	 */
	//TODO make this procedure
	public void repair(){
		calcRandomFailTime();
	}
	
	public abstract InfoProposal getInfo();
	public abstract void calucalte();
	
}
