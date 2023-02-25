package spaceinvaders.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Scanner;

import spaceinvaders.handlers.Keys;
import spaceinvaders.main.GameBoard;

/**
 * The credit game state to display the credits of the game.
 * 
 * The format of a credits text file is as follows:
 * A line containing only a dot will be an empty line.
 * A line containing an @ at the start will not be included.
 * An empty line will be removed and truncated.
 * Any other line will be treated as is.
 */
public class CreditsGS extends GameState {
	
	private static float SLOW_SPEED = 0.5f;
	private static float FAST_SPEED = 3f;
	
	/* The y value of the text object. */
	private float y;
	/* The speed of the scrolling text. */
	private float dy;
	/* The height of the text */
	private float height;
	/* The scanner to read the values from the text file. */
	private LinkedList<String> credits;
	
	/**
	 * {@inheritDoc}
	 */
	public CreditsGS(GameStateManager gsm) {
		super(gsm);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		loadText("/street_creds.txt");
		
		y = GameBoard.HEIGHT + 50;
		dy = 0.5f;
		height = 2568; // MAGIC NUMBER.
	}
	
	/**
	 * Loads the text from the credits file into the memory.
	 * Does so line by line using the format stated above.
	 * 
	 * @param file the file for the credits text.
	 */
	private void loadText(String file) {
		credits = new LinkedList<>();
		
		Scanner scanner = new Scanner(getClass().getResourceAsStream(file));
		String next;
		while (scanner.hasNextLine()) {
			next = scanner.nextLine();
			if (next.equals("."))
				credits.add("");
			else if (!(next.charAt(0) == '@'))
				credits.add(next);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(int tick) {
		if (Keys.isHeld(Keys.Space))
			dy = FAST_SPEED;
		else
			dy = SLOW_SPEED;
		
		y -= dy;
		
		if (Keys.isPressed(Keys.Escape) || y <= -height) {
			dispose();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.white);
		for (int i = 0; i < credits.size(); i++) {
			g2d.drawString(credits.get(i), 10, y +(i *12));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		gsm.setState(GameStateID.Menu);
	}
	
}