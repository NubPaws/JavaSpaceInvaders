package spaceinvaders.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Scanner;

import spaceinvaders.handlers.Keys;
import spaceinvaders.main.GameBoard;

public class CreditsGS extends GameState {
	
	public CreditsGS(GameStateManager gsm) { super(gsm); }
	
	private float y;
	private Scanner scanner;
	private LinkedList<String> credits;
	
	@Override
	public void init() {
		y = GameBoard.HEIGHT + 50;
		scanner = new Scanner(getClass().getResourceAsStream("/street_creds.txt"));
		credits = new LinkedList<>();
		String next;
		while (scanner.hasNextLine()) {
			next = scanner.nextLine();
			if (next.equals("."))
				credits.add("");
			else if (!(next.charAt(0) == '@'))
				credits.add(next);
		}
	}
	
	@Override
	public void update(int tick) {
		y -= .5f;
		if (Keys.isPressed(Keys.Escape) || y <= -2568) {
			dispose();
		}
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.white);
		for (int i = 0; i < credits.size(); i++) {
			g2d.drawString(credits.get(i), 10, y +(i *12));
		}
	}
	
	@Override
	public void dispose() {
		gsm.setState(GameStateID.Menu);
	}
	
}