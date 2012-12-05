package org.sepr.anchovy;

/**
 * Pair<T> represents any <String, Object> pair 
 * 
 * @author Andrei
 * @param <T> -- an object type
 */

public class Pair<T> {
	/**
	 * An enum that prevents hardcoding the strings used as labels for Pair values <br> 
	 * More information about Java enums: http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
	 */
	public static enum Label {
		temp("temperature"),
		pres("pressure"),
		etc("feel free to add more stuff;" +
				"it should have the enum term 4-char long to keep the convention");
		
		// if needed we can add more info about labels (i.e. new fields)
		private final String name;
		
		Label(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
	}

	/**
	 * The label: this is a string that identifies what kind of value this Pair holds 
	 */
	private String first;
	/**
	 * The value: the actual variable, either primitive or reference (e.g. ArrayList)
	 */
	private T second;
	
	
	/**
	 * Simply initializes the label and the value with the 2 arguments given
	 * @param name
	 * @param val
	 */
	Pair(String name, T val) {
		this.first = name;
		this.second = val;
	}
	
	/**
	 * Returns the label, i.e. the first element of the pair
	 * @return String first == a string from Pair.Label
	 */
	public String first() {
		return this.first;
	}
	
	/**
	 * Returns the label, i.e. the second element of the pair
	 * @return T second == a value, either primitive or reference (e.g. ArrayList)
	 */
	public T second() {
		return second;
	}
	
	/**
	 * Simply updates the value of the Pair, i.e. of the second element
	 * @param val this value is assigned to the Pair
	 */
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