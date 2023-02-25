package spaceinvaders.gamestate;

import spaceinvaders.extenable.Scene;

/**
 * A GameState should handle a state of game where a state
 * is defined to be a self contained section of the game.
 * Self contained being, the area, logically, is all
 * related to one another.
 */
public abstract class GameState implements Scene {
	
	/* Each GameState has a delegate for the GameStateManager
	 * to allow for ease of game state changing.
	 */
	protected GameStateManager gsm;
	
	/**
	 * Create a GameState instance.
	 * 
	 * @param gsm The GameStateManager to handle this game state.
	 */
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	/**
	 * Getter for the game state manager. Will be used by the
	 * state to dispose itself if it needs to.
	 * 
	 * @return The GameStateManager handling the instance.
	 */
	public GameStateManager getGSM() { return gsm; }
	
}