package org.sepr.anchovy;

/**
 * Pair<T> represents any <String, Object> pair 
 * 
 * @author Andrei
 * @param <T> -- an object type
 */

public class Pair<T> {
	private String first;
	private T second;
	
	Pair(String name, T val) {
		this.first = name;
		this.second = val;
	}
	
	public String first() {
		return this.first;
	}
	
	public T second() {
		return second;
	}
	
	public void update(T val) {
		this.second = val;
	}
	
	@Override
	public String toString() {
		return this.first + " " + this.second.toString();
	}

	/**
	 * Sandbox to check functionality. Remember to remove this main
	 */
	public static void main(String args[]) {
		Pair<Integer> a = new Pair<Integer>("temperature", 100);
		
		System.out.println(a);
		
		a.update(120);
		
		System.out.println(a);
	}
}