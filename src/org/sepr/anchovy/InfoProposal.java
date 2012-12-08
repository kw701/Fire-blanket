package org.sepr.anchovy;

import java.util.ArrayList;

public class InfoProposal {
	/**
	 * ArrayList containing the data == (string, value) pairs
	 */
	public static ArrayList<Pair<?>> namedValues = new ArrayList<Pair<?>>();
	
	// TODO: must think what other stuff we need in this method;
	// 			don't forget, though, it has to be as lightweight and
	//			flexible as possible
	
	/**
	 * Sand box:
	 * <li>			feel free to use Pair constructs here
	 * <li>			run in Debug (F11)
	 * <li> 		also give Pair.Label, the enum, a try
	 */
	public static void main(String args[]) {
		// adding an integer to the Info object
		namedValues.add(new Pair<Integer>(Pair.Label.pRPM, 1));
		
		// adding a double value
		namedValues.add(new Pair<Double>(Pair.Label.coRL, 2.0));
		
		// adding a String 
		namedValues.add(new Pair<String>(Pair.Label.pres, "potato"));
		
		// adding an ArrayList
		namedValues.add(new Pair<ArrayList<Integer>>(Pair.Label.etc, new ArrayList<Integer>(3)));
		
		// defining an ArrayList with words and adding it to this Info object
		ArrayList<String> words = new ArrayList<String>();
		words.add("meow");
		words.add("cow");
		words.add("pixels");
		Pair<ArrayList<String>> x = new Pair<ArrayList<String>>(Pair.Label.etc, words);
		
		namedValues.add(x);
		
		// Printing the namedValues ArrayList
		System.out.println(namedValues);
	}
}
