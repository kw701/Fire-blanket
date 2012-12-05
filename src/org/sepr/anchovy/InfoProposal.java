package org.sepr.anchovy;

import java.util.ArrayList;

public class InfoProposal {
	public static ArrayList<Pair<?>> namedValues = new ArrayList<Pair<?>>();
	
	
	public static void main(String args[]) {
		namedValues.add(new Pair<Integer>("test", 1));
		namedValues.add(new Pair<Integer>("test", 2));
		namedValues.add(new Pair<Integer>("test", 3));
		namedValues.add(new Pair<Integer>("test", 4));
		
		System.out.println(namedValues);
	}
}
