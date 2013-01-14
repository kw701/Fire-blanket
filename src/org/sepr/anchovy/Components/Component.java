package org.sepr.anchovy.Components;

import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

import org.sepr.anchovy.InfoPacket;
import org.sepr.anchovy.Pair;
import org.sepr.anchovy.Pair.Label;
/**
 * A general component, all components within the power plant are children of this class.
 * Contains all common attributes and methods of components.
 * 
 * @author Harrison
 */
public abstract class Component {
	private String name;
	private int meanTimeBetweenFailure; //MTBF
	private Double failureTime;
	private Double outputFlowRate;
	private ArrayList<Component> outputsTo;
	private ArrayList<Component> recievesInputFrom;
	
	/**
	 * Setup the component ready for use, set its name and initialize the lists of components it is connected to.
	 * @param name the name of the individual component, should be unique.
	 */
	public Component(String name){
		this.name = name;
		if(failureTime == null){
			calcRandomFailTime();
		}
		outputsTo = new ArrayList<Component>();
		recievesInputFrom = new ArrayList<Component>();
	}
	
	/**
	 * Calculates the failure time of the component normally distributed around the MTBF
	 */
	protected void calcRandomFailTime(){
		Random rand = new Random();
		failureTime = rand.nextGaussian()* 10 + meanTimeBetweenFailure;
	}
	
	/**
	 * repairs the component. will work at any time.
	 */
	//TODO make this procedure
	public void repair(){
		calcRandomFailTime();
	}
	
	/**
	 * Create an information packet for the attributes of the general component. Usually used to get this part of the getInfo for the child componenets. 
	 * 
	 * @return info an information packet containing; the component name, failure time and output flow rate, output and input comonents
	 */
	protected InfoPacket getSuperInfo(){
		InfoPacket info = new InfoPacket();
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
	
	/**
	 * Assigns the vaules stored in the given info packet to the relevant attributes.
	 * Renameing the component is possible via this method so care may be needed. 
	 * DOES NOT DEAL WITH COMPONENT CONNECTIONS - must be done at game engine as components can't see other components until they are connected.
	 * @param info An info packet the the component. 
	 * 
	 */
	protected void takeSuperInfo(InfoPacket info){
		resetConections();
		Iterator<Pair<?>> i = info.namedValues.iterator();
		Pair<?> pair = null;
		Label label = null;
		while(i.hasNext()){
			pair = i.next();
			label = pair.getLabel();
			switch (label){
			case cNme:
				setName((String) pair.second());
				break;
			case falT:
				setFailureTime((Double) pair.second());
				break;
			case OPFL:
				setOuputFlowRate((Double) pair.second());	
			default:
				break;
			}
		}
	}
	
	/**
	 * Clears the lists of components that this component is connected to. 
	 */
	protected void resetConections(){
		outputsTo.clear();
		recievesInputFrom.clear();
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){ return name;}
	
	/**
	 * Connects the given component to the list of components that are output to.
	 * @param  component Component to add to outputs.
	 */
	public void connectToOutput(Component component){
		outputsTo.add(component);
	}
	/**
	 * Connects the given component to the list of components that this component recives input from.
	 * @param component Component to add to inputs
	 */
	public void connectToInput(Component component){
		recievesInputFrom.add(component);
	}
	
	public void setOuputFlowRate(double outputFlowRate){
		this.outputFlowRate = outputFlowRate;
	}
	public double getOutputFlowRate(){
		return outputFlowRate;
	}
	
	/**
	 * Create an info packet containing data about all attributes for the component - should call super.getSuperInfo()
	 * 
	 * @return info an info packet containing all attributes for the component
	 */
	public abstract InfoPacket getInfo();
	
	/**
	 * By having a single calculate method, any component can be told to calculate
	 * Without the rest of the program explicitly knowing what type of component it is.
	 * This method should therefore call separate (more specific calculates) within the actual component.
	 * such as calculateTemperature()
	 */
	public abstract void calucalte();
	
	/**
	 * The only specific calculation that all components must do as every component has an output flow.
	 * Abstract because every type of component will calculate this in a different way.
	 * 
	 * @return The new output flow rate
	 */
	protected abstract double calculateOutputFlowRate();
	
	/**
	 * Sets all attributes of a component using the given info packet.
	 * Abstract as this deals with the component child specific attributes.
	 * should call super.takeSuperInfo()
	 * 
	 * @param info InfoPacket defining values of attributes of the component.
	 */
	public abstract void takeInfo(InfoPacket info) throws Exception;

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
