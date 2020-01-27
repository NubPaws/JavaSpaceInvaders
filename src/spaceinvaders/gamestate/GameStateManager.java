package spaceinvaders.gamestate;

import java.awt.Graphics2D;

import spaceinvaders.extenable.Scene;
import spaceinvaders.main.GameBoard;

public class GameStateManager implements Scene {
	
	private GameBoard board;
	private GameState state;
	
	public GameStateManager(GameBoard board) {
		this.board = board;
		init();
	}
	
	@Override
	public void init() {
		setState(GameStateID.Menu);
	}
	
	@Override
	public void update(int tick) {
		state.update(tick);
	}
	
	@Override
	public void render(Graphics2D g2d) {
		state.render(g2d);
	}
	
	@Override
	public void dispose() {
		board.dispose();
	}
	
	public void setState(GameStateID id) {
		state = null;
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
		System.gc();
	}
	
}