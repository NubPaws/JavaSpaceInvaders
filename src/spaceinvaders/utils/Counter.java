package spaceinvaders.utils;

/**
 * Counter class to be passed as a reference, keeping track of the count
 * between classes without having to use an observer.
 */
public class Counter {
	
	/* The value of the counter. */
	private int value;
	
	/**
	 * Create a counter with it's value set to be 0.
	 */
	public Counter() {
		this(0);
	}
	
	/**
	 * Create a counter instance with the settings.
	 * 
	 * @param value the starting value of the counter.
	 */
	public Counter(int value) {
		this.value = value;
	}
	
	/**
	 * @return the value of the counter.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Increment the counter by 1.
	 */
	public void increment() {
		value += 1;
	}
	
	/**
	 * Increment the counter by inc.
	 *
	 * @param inc
	 */
	public void increment(int inc) {
		value += inc;
	}
	
	/**
	 * Decrement the counter by 1.
	 */
	public void decrement() {
		value -= 1;
	}
	
	/**
	 * Decrement the counter by dec.
	 *
	 * @param dec
	 */
	public void decrement(int dec) {
		value -= dec;
	}
	
	/**
	 * Check if the counter is at a given value.
	 * 
	 * @param value the value to check against.
	 * @return whether or not the counter is a given value.
	 */
	public boolean at(int value) {
		return this.value == value;
	}
	
	/**
	 * Sets the value of the counter to a different value.
	 * 
	 * @param value the new value of the counter.
	 */
	public void set(int value) {
		this.value = value;
	}
	
}
