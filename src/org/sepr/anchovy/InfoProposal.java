package org.sepr.anchovy;

import java.util.ArrayList;

public class InfoProposal {
	public static ArrayList<Pair<?>> namedValues = new ArrayList<Pair<?>>();
	
	
	public static void main(String args[]) {
		namedValues.add(new Pair<Integer>("test", 1));
		namedValues.add(new Pair<Double>("test", 2.0));
		namedValues.add(new Pair<String>("test", "potato"));
		namedValues.add(new Pair<ArrayList<Integer>>("test", new ArrayList<Integer>(3)));
		
		System.out.println(namedValues);
	}
}
