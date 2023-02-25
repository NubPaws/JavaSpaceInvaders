package spaceinvaders.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import spaceinvaders.handlers.ImagesHandler;
import spaceinvaders.handlers.Keys;
import spaceinvaders.main.GameBoard;
import spaceinvaders.objects.GameObjectID;

/**
 * The main menu game state.
 */
public class MenuGS extends GameState {
	
	/* The colors for the selected options. */
	private static final Color SELECTED_COLOR = new Color(255, 215, 0);
	private static final Color UNSELECTED_COLOR = Color.white;
	
	/* The background of the main menu. */
	private BufferedImage background;
	/* The selected option in the menu. */
	private int selected;
	/* The position of the marker (for extra elegance). */
	private Point markerPos;
	/* The width of the play option, the credits option and the quit option. */
	private int playWidth;
	private int creditsWidth;
	private int quitWidth;
	/* The y coordinate for the options */
	private int playY;
	private int creditsY;
	private int quitY;
	
	/**
	 * {@inheritDoc}
	 */
	public MenuGS(GameStateManager gsm) {
		super(gsm);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		background = ImagesHandler.get("sprites/background.png");
		selected = 0;
		markerPos = new Point(0, 0);
		
		initOptionsWidth();
		playY = 125;
		creditsY = 150;
		quitY = 175;
	}
	
	/**
	 * Initialize the options width variable to save on some rendering
	 * time in the application.
	 */
	private void initOptionsWidth() {
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		Font font = GameBoard.MAIN_FONT;
		
		playWidth = (int)font.getStringBounds("Play", frc).getWidth();
		creditsWidth = (int)font.getStringBounds("Credits", frc).getWidth();
		quitWidth = (int)font.getStringBounds("Quit", frc).getWidth();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(int tick) {
		/* Handle navigation. */
		if (Keys.isPressed(Keys.DownArrow) || Keys.isPressed(Keys.S))
			selected = (selected + 1) % 3;
		if (Keys.isPressed(Keys.UpArrow) || Keys.isPressed(Keys.W))
			selected = (3 + selected - 1) % 3; // Weird java negative modulu...
		
		/* Handle selection. */
		if (Keys.isPressed(Keys.Enter) || Keys.isPressed(Keys.Space)) {
			switch (selected) {
			case 0:
				gsm.setState(GameStateID.Game);
				break;
			case 1:
				gsm.setState(GameStateID.Credits);
				break;
			case 2:
				dispose();
				break;
			}
		}
		
		/* Calculate the position for the marker, make him cute you know. */
		calculateMarkerPosition();
		
		if (Keys.isPressed(Keys.Escape))
			dispose();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(background, 0, 0, null);
		
		
		defineColor(g2d, 0);
		g2d.drawString("Play", (GameBoard.WIDTH - playWidth) / 2, playY);
		
		defineColor(g2d, 1);
		g2d.drawString("Credits", (GameBoard.WIDTH - creditsWidth) / 2, creditsY);
		
		defineColor(g2d, 2);
		g2d.drawString("Quit", (GameBoard.WIDTH - quitWidth) / 2, quitY);
		
		g2d.drawImage(GameObjectID.EnemyMid.image(), markerPos.x, markerPos.y, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		gsm.dispose();
	}
	
	/**
	 * Sets the color for each option. Given the index of the current
	 * option and the graphics instance we can set the color based on
	 * the selected member of the instance.
	 * 
	 * @param g2d the graphics instance of the game.
	 * @param i the index of the current option to select the color for.
	 */
	private void defineColor(Graphics2D g2d, int i) {
		if (selected == i)
			g2d.setColor(SELECTED_COLOR);
		else
			g2d.setColor(UNSELECTED_COLOR);
	}
	
	/**
	 * Calculates the marker position based on the current selected
	 * option in the instance.
	 */
	private void calculateMarkerPosition() {
		final int markerSize = 16;
		final int halfMarkerSize = markerSize / 2;
		switch (selected) {
		case 0:
			markerPos.x = ((GameBoard.WIDTH - playWidth) / 2) - markerSize;
			markerPos.y = playY - halfMarkerSize;
			break;
		case 1:
			markerPos.x = ((GameBoard.WIDTH - creditsWidth) / 2) - markerSize;
			markerPos.y = creditsY - halfMarkerSize;
			break;
		case 2:
			markerPos.x = ((GameBoard.WIDTH - quitWidth) / 2) - markerSize;
			markerPos.y = quitY - halfMarkerSize;
			break;
		}
	}
	
}