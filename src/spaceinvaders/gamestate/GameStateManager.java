package spaceinvaders.gamestate;

import java.awt.Graphics2D;

import spaceinvaders.extenable.Scene;
import spaceinvaders.main.GameBoard;

/**
 * A manager class for the game states. Manages cycling through
 * each game state, closing and opening game states, and passing
 * throught the update and render calls to the proper game
 * state that is currently selected.
 */
public class GameStateManager implements Scene {
	
	/* A delegate for the GameBoard. */
	private GameBoard board;
	/* The current selected game state. */
	private GameState state;
	
	/**
	 * Creates an instance of the game state manager.
	 * This class can technically be a singleton if the
	 * window class was a singleton.
	 * There is no problem with creating multiple game states
	 * managers for a game or for multiple windows.
	 * 
	 * @param board
	 */
	public GameStateManager(GameBoard board) {
		this.board = board;
		init();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		setState(GameStateID.Menu);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(int tick) {
		state.update(tick);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics2D g2d) {
		state.render(g2d);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		board.dispose();
	}
	
	/**
	 * Sets the new game state based on the given ID.
	 * 
	 * @param id the ID of the game state to set.
	 */
	public void setState(GameStateID id) {
		/* Recomment a call to the garbage collector as this is the
		 * perfect time for the game to collect the garbage.
		 */
		state = null;
		System.gc();
		
		switch (id) {
		case Game:
			state = new GameGS(this);
			break;
		case Menu:
			state = new MenuGS(this);
			break;
		case Credits:
			state = new CreditsGS(this);
			break;
		}
	}
	
}