package org.sepr.anchovy;

import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

import org.sepr.anchovy.Pair.Label;

public abstract class Component {
	private String name;
	private int meanTimeBetweenFailure; //MTBF
	private Double failureTime;
	private Double outputFlowRate;
	private ArrayList<Component> outputsTo;
	private ArrayList<Component> recievesInputFrom;
	
	public Component(String name){
		this.name = name;
		if(failureTime == null){
			calcRandomFailTime();
		}
		outputsTo = new ArrayList<Component>();
		recievesInputFrom = new ArrayList<Component>();
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
	
	protected InfoProposal getSuperInfo(){
		InfoProposal info = new InfoProposal();
		info.namedValues.add(new Pair<String>(Label.cNme, name));
		info.namedValues.add(new Pair<Double>(Label.falT, failureTime));
		info.namedValues.add(new Pair<Double>(Label.OPFL, outputFlowRate));

		Iterator<Component> i = outputsTo.iterator();
		Component c = null;
		while (i.hasNext()){
			c = i.next();
			info.namedValues.add(new Pair<String>(Label.oPto, c.getName()));
		}
		i = recievesInputFrom.iterator();
		while (i.hasNext()){
			c = i.next();
			info.namedValues.add(new Pair<String>(Label.rcIF, c.getName()));
		}
		return info;
	}
	protected void resetConections(){
		outputsTo.clear();
		recievesInputFrom.clear();
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){ return name;}
	public void connectToOutput(Component component){
		outputsTo.add(component);
	}
	public void connectToInput(Component component){
		recievesInputFrom.add(component);
	}
	public void setOuputFlowRate(double outputFlowRate){
		this.outputFlowRate = outputFlowRate;
	}
	public double getOutputFlowRate(){
		return outputFlowRate;
	}
	public abstract InfoProposal getInfo();
	/*
	 * By having a single calculate method, any component can be told to calculate
	 * Without the rest of the program explicitly knowing what type of component it is.
	 * This method should therefore call separate (more specific calculates) within the actual component.
	 */
	public abstract void calucalte();
	protected abstract double calculateOutputFlowRate();
	
	public abstract void takeInfo(InfoProposal info);

	public int getMeanTimeBetweenFailure() {
		return meanTimeBetweenFailure;
	}

	public void setMeanTimeBetweenFailure(int meanTimeBetweenFailure) {
		this.meanTimeBetweenFailure = meanTimeBetweenFailure;
	}

	public Double getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(Double failureTime) {
		this.failureTime = failureTime;
	}

	public ArrayList<Component> getOutputsTo() {
		return outputsTo;
	}

	public void setOutputsTo(ArrayList<Component> outputsTo) {
		this.outputsTo = outputsTo;
	}

	public ArrayList<Component> getRecievesInputFrom() {
		return recievesInputFrom;
	}

	public void setRecievesInputFrom(ArrayList<Component> recievesInputFrom) {
		this.recievesInputFrom = recievesInputFrom;
	}

	public void setOutputFlowRate(Double outputFlowRate) {
		this.outputFlowRate = outputFlowRate;
	}
	
}
