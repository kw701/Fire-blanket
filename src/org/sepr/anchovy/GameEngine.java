package org.sepr.anchovy;

import org.sepr.anchovy.Components.*;
import org.sepr.anchovy.Pair.Label;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.sepr.anchovy.io.*;
public class GameEngine {
	ArrayList<Component> powrPlntComponents = null;
	UI ui = null;
	/*
	 * Constructor for the game engine
	 * On creation it created a list to store the components of the power plant
	 * and links to a user interface (what ever type that may be)
	 */
	public GameEngine(){
		powrPlntComponents = new ArrayList<Component>();
		ui = new UI(this);
		
		/*
		final Timer gameLoop = new Timer();
		gameLoop.scheduleAtFixedRate(new TimerTask(){
			boolean stop = false;
			long timesRoundLoop = 0;
			
			public void run(){
				timesRoundLoop++;
				if(timesRoundLoop > 10){
					stop = true;
				}
				if(!stop){
					System.out.println("Hello");
					runSimulation();
				}else{
					gameLoop.cancel();
				}
				
			}
		}, 0, 1000);
		*/
	}
	/*
	 * Using a list of Info Packets (generated from loading the same from file or elsewhere)
	 * Adds each of the components described in the Info Packet list to the list of components in the power plant
	 * Then sends the info packet to that component to initialize all its values
	 * Once all components of the power plant are in the list, they are then all connected together in the way described by the infopackets.
	 * 
	 * @param allPowerPlantInfo A list of info packets containing all the information about all components to be put into the power plant.
	 */
	public void setupPowerPlantConfigureation(ArrayList<InfoPacket> allPowerPlantInfo){
		Iterator<InfoPacket> infoIt = allPowerPlantInfo.iterator();
		InfoPacket currentInfo = null;
		String currentCompName = null;
		Component currentNewComponent = null;
		
		//Create component list.
		while(infoIt.hasNext()){
			currentInfo = infoIt.next();
			currentCompName = getComponentNameFromInfo(currentInfo);
			
			//Determine component types we are dealing with.
			if(currentCompName.contains("Consenser")){
				currentNewComponent = new Condenser(currentCompName);
			}else if(currentCompName.contains("Generator")){
				currentNewComponent = new Generator(currentCompName);
			}else if(currentCompName.contains("Pump")){
				currentNewComponent = new Pump(currentCompName);
			}else if(currentCompName.contains("Reactor")){
				currentNewComponent = new Reactor(currentCompName);
			}else if(currentCompName.contains("Turbine")){
				currentNewComponent = new Turbine(currentCompName);
			}else if(currentCompName.contains("Valve")){
				currentNewComponent = new Valve(currentCompName);
			}
			addComponent(currentNewComponent); //add the component to the power plant
			
			try {
				assignInfoToComponent(currentInfo); //send the just added component its info.
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//Connect components together
		infoIt = allPowerPlantInfo.iterator();// reset the iterator TODO i think this works.
		ArrayList<String> inputComponents = null;
		ArrayList<String> outputComponents = null;
		
		Iterator<Pair<?>> pairIt = null;
		Pair currentPair = null;
		Label currentLabel = null;
		
		Component currentComponent = null;
		Iterator<Component> compIt = null;
		
		Iterator<String> connectionNameIt = null;
		
		//get info for each components
		while(infoIt.hasNext()){
			currentInfo = infoIt.next();
			pairIt = currentInfo.namedValues.iterator();
			
			//get the useful information out of the info.
			while(pairIt.hasNext()){
				currentPair = pairIt.next();
				currentLabel = currentPair.getLabel();
		
				switch (currentLabel){
				case cNme:
					currentCompName = (String) currentPair.second();
					break;
				case rcIF:
					inputComponents = (ArrayList<String>) currentPair.second();
					break;
				case oPto:
					outputComponents = (ArrayList<String>) currentPair.second();
					break;
				default:
					break;
				}
			}
			
			//Get the component that we are going to conect other components to.
			currentComponent = getPowerPlantComponent(currentCompName);
			
			//Attach each input component to the current component.
			connectionNameIt = inputComponents.iterator();
			while(connectionNameIt.hasNext()){
				connectComponentTo(currentComponent, getPowerPlantComponent(connectionNameIt.next()), true);
			}
			//Attach each output component to the current compoennt
			connectionNameIt = outputComponents.iterator();
			while(connectionNameIt.hasNext()){
				connectComponentTo(currentComponent, getPowerPlantComponent(connectionNameIt.next()), false);
			}
			
			
		}
	}
	private Component getPowerPlantComponent(String currentCompName) {
		Component currentComponent = null;
		Iterator<Component> compIt;
		compIt = powrPlntComponents.iterator();
		while(compIt.hasNext()){
			if(compIt.next().getName() == currentCompName){
				currentComponent = compIt.next();
			}
		}
		return currentComponent;
	}
	private String getComponentNameFromInfo(InfoPacket info){
		Iterator<Pair<?>> pairIt = info.namedValues.iterator();
		Pair<?> pair = null;
		String name = null;
		while(pairIt.hasNext() && name==null){
			pair = pairIt.next();
			if(pair.getLabel() == Label.cNme){
				name = (String) pair.second();
			}
		}
		return name;
		
	}
	
	/*
	 * Sends an info packet a component
	 * the components is specified by the name of the component in the info packet.
	 * @param info Info Packet to be sent to a component
	 */
	public void assignInfoToComponent(InfoPacket info) throws Exception{
		String compToSendTo = null;
		/*
		 * going through the list of pairs to pick out the pair denoting the name of the component to sent the info to
		 */
		Pair<?> pair = null;
		Iterator<Pair<?>> pi = info.namedValues.iterator();
		Label label = null;
		while(pi.hasNext() && compToSendTo == null){
			pair = pi.next();
			label = pair.getLabel();
			switch (label){
			case cNme:
				compToSendTo = (String) pair.second();
			default:
				break;
			}
		}
		/*
		 * Going through the list of components to find the component with the matching name
		 */
		Iterator<Component> ci = powrPlntComponents.iterator();
		boolean comNotFound = true;
		Component com = null;
		while(ci.hasNext() && comNotFound){
			comNotFound = true;
			com = ci.next();
			if(com.getName() == compToSendTo){
				comNotFound = false;
				
			}
		}
		/*
		 * if the component wasn't found throw an exception stating this
		 */
		if(com == null){
			throw new Exception("The component you were trying to send info to doesn't exit");
		}else{
			com.takeInfo(info);
		}
	}
	public void runSimulation(){
		Iterator<Component> ci = powrPlntComponents.iterator();
		Component comp = null;
		while(ci.hasNext()){
			comp = ci.next();
			comp.calucalte();
		}
	}
	/*
	 * Add a component to the list of components
	 * @param component the component to be added to the list of components
	 */
	public void addComponent(Component component){
		powrPlntComponents.add(component);
	}
	
	
	/*
	 * Connect two components together
	 * @param comp1 the component that we are working with
	 * @param comp2 the component that will be added to comp1
	 * @param input_output denoted whether it is an input or an output; in = true, out = false
	 */
	public void connectComponentTo(Component comp1, Component comp2, boolean input_ouput){
		if(input_ouput){
			comp1.connectToInput(comp2);
		}else{
			comp1.connectToOutput(comp2);
		}
	}
	
	public ArrayList<InfoPacket> getAllComponentInfo(){
		ArrayList<InfoPacket> allInfo = new ArrayList<InfoPacket>();
		Iterator<Component> ci = powrPlntComponents.iterator();
		Component comp = null;
		while(ci.hasNext()){
			comp = ci.next();
			allInfo.add(comp.getInfo());
		}
		return allInfo;
		
	}
	/*
	 * Resets the components of the power plant to am empty list.
	 * Will be needed for loading a power plant from file.
	 */
	public void clearPowerPlant(){
		powrPlntComponents = new ArrayList<Component>();
	}
	/*
	 * The main method for the game
	 * 
	 */
	public static void main(String[] args){
		// TODO create the main game loop
		GameEngine game = new GameEngine();
		game.addComponent(new Valve("Valve 1"));
		ArrayList<InfoPacket> info = game.getAllComponentInfo();
	}
}
