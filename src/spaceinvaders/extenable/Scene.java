package spaceinvaders.extenable;

/**
 * A scene is an instance that can be updated and
 * rendered, but also is able to be initialize,
 * therefore it can be desposed.
 */
public interface Scene extends Loopable {
	
	/**
	 * A function to handle the initialization
	 * of the scene. Must be called after the
	 * creation of the object's instance.
	 */
	void init();
	
	/**
	 * A function to handle disposing the instance.
	 * Recommended to be called before finishing using
	 * the instance of the object to allow the garbage
	 * collector to work in a more efficient manner.
	 */
	void dispose();
	
}