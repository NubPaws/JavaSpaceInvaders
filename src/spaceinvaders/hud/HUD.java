package spaceinvaders.hud;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import spaceinvaders.extenable.Loopable;
import spaceinvaders.main.GameBoard;
import spaceinvaders.utils.Counter;

/**
 * Represents the heads-up display (HUD). Handles calculating all
 * of the coordinates and dimensions for the texts.
 */
public class HUD implements Loopable {
	
	/* Counters for the different values on the heads up display (HUD). */
	private Counter score;
	private Counter lives;
	private Counter level;
	
	/* The color of the text. */
	private Color textColor;
	
	/* The texts for each value. */
	private String scoreText;
	private String livesText;
	private String levelText;
	private final int levelTextWidth;
	
	/**
	 * Create an instance of the HUD. Pass the counters that will
	 * be updated by the game itself.
	 * 
	 * @param score
	 * @param lives
	 * @param level
	 */
	public HUD(Counter score, Counter lives, Counter level) {
		this.score = score;
		this.lives = lives;
		this.level = level;
		
		textColor = Color.white;
		
		scoreText = "";
		livesText = "";
		levelText = "";
		
		levelTextWidth = initLevelTextWidth();
	}
	
	/**
	 * Generates the level text width as it is a bit more complicated to do so
	 * if you don't have a Graphics2D g2d instance nearby.
	 * 
	 * @return the level text width.
	 */
	private int initLevelTextWidth() {
		String text = "Level : 000";
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		return (int)GameBoard.MAIN_FONT.getStringBounds(text, frc).getWidth();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(int tick) {
		scoreText = "Score : " + score.getValue();
		livesText = "Lives : " + lives.getValue();
		levelText = String.format("Level : %03d", level.getValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(textColor);
		g2d.drawString(scoreText, 0, 10);
		g2d.drawString(livesText, 0, GameBoard.HEIGHT - 2);
		g2d.drawString(levelText, GameBoard.WIDTH - levelTextWidth, GameBoard.HEIGHT - 2);
	}
	
}
