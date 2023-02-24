package spaceinvaders.extenable;

/**
 * An updatable is an instance that can be updated, logically.
 */
public interface Updatable {
	
	/**
	 * Handle the logic of the instance.
	 * 
	 * @param tick The current tick, between 0 and GameLoop.TICKS.
	 */
	void update(int tick);
	
}