package spaceinvaders.utils;

/**
 * Reprepsents a flag state in the game, that can be passed around
 * and updated between classes without having to have static members.
 */
public class Flag {
	
	/* The value of the flag. */
	private boolean value;
	
	/**
	 * Creates a new instance of the flag with the starting value passed.
	 * 
	 * @param value the starting value of the flag.
	 */
	public Flag(boolean value) {
		this.value = value;
	}
	
	/**
	 * @return whether the flag is on or not (equals true).
	 */
	public boolean on() {
		return value;
	}
	
	/**
	 * @return whether the flag is off or not (equals false).
	 */
	public boolean off() {
		return !value;
	}
	
	/**
	 * Sets the value of the flag to the given value.
	 * 
	 * @param value the new value of the flag.
	 */
	public void set(boolean value) {
		this.value = value;
	}
	
	/**
	 * Inverts the flag from on to off or from off to on.
	 */
	public void flip() {
		value = !value;
	}
	
}
