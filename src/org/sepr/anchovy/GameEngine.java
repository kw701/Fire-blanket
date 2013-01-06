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
