package spaceinvaders.gamestate;

import spaceinvaders.extenable.Scene;

public abstract class GameState implements Scene {
	
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public GameStateManager getGSM() { return gsm; }
	
}