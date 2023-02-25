package spaceinvaders.gamestate;

import static spaceinvaders.utils.Helper.clampi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import spaceinvaders.handlers.Handler;
import spaceinvaders.handlers.Keys;
import spaceinvaders.hud.HUD;
import spaceinvaders.main.GameBoard;
import spaceinvaders.objects.Barrier;
import spaceinvaders.objects.Enemy;
import spaceinvaders.objects.GameObject;
import spaceinvaders.objects.GameObjectID;
import spaceinvaders.objects.Player;
import spaceinvaders.utils.Counter;

/**
 * Handles the "play" game state of the game.
 * The part with the actual gameplay.
 */
public class GameGS extends GameState {
	
	/* Enum to indicate how the aliens should move. */
	public enum MoveDirection {
		Right, Left, Down
	}
	
	/* Static member indicating if we passed the entire stage. */
	public static boolean aliensPassed;
	
	/* Collection of the game objects in the game. */
	private Handler<GameObject> handler;
	/* Counters for the left and right movements. Indicating
	 * how much we moved to each direction.
	 */
	private byte movedLeft, movedRight;
	/* Variables for the timing of the movements. */
	private int moveTimer, timeToMove;
	
	/* Counters for the score and the level. */
	private Counter score;
	private Counter level;
	
	/* Indicator whether the game has been finished. */
	private boolean gameCompleted;
	/* Random number generator. */
	private Random rand;
	
	/* Instance of the player. */
	private Player player;
	
	private HUD hud;
	
	/**
	 * {@inheritDoc}
	 */
	public GameGS(GameStateManager gsm) {
		super(gsm);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		level = new Counter(1);
		score = new Counter();
		aliensPassed = false;
		
		initPrimitiveVariables();
		rand = new Random();
		
		initHandler();
		
		hud = new HUD(score, player.getLives(), level);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(int tick) {
		/* If the game should move, under the definition of when
		 * should the game move. Update the entities and move the
		 * enemies accordingly.
		 * Don't forget to check collisions.
		 */
		if (shouldMove()) {
			handler.update(tick);
			moveEnemies();
			checkCollision();
		}
		
		/* If the player has won, and the level is not yet at the max
		 * the let the player play. Otherwise if the palyer got to the
		 * last level then tell him he won.
		 * If there are still levels to play, load them.
		 */
		if (hasWon()) {
			if (level.at(Byte.MAX_VALUE)) {
				gameCompleted = true;
				return;
			}
			prepareNextLevel();
		}
		
		/* 5% chance to summor a special ship that once destroyed gives the
		 * player a boost in the firing rate.
		 */
		if (tick <= 3 && rand.nextInt(100) <= 5) {
			handler.add(new Enemy(GameBoard.WIDTH, 12.5f, GameObjectID.EnemyBoss, handler, score, player));
		}
		
		hud.update(tick);
		
		/* If the user presses escape, close the game state. */
		if (Keys.isPressed(Keys.Escape))
			dispose();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics2D g2d) {
		handler.render(g2d);
		
		hud.render(g2d);
		
		if (hasLost())
			drawEndScreen(g2d, "Game Over!");
		if (gameCompleted)
			drawEndScreen(g2d, "You have finished the game");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		handler.list().clear();
		handler = null;
		gsm.setState(GameStateID.Menu);
	}
	
	///**
	// * Draws the HUD - Heads Up Display.
	// * 
	// * @param g2d The graphics instance of the current buffer.
	// */
	//private void drawHUD(Graphics2D g2d) {
	//	// TODO: Extrapolate to a different class that handles this.
	//	g2d.setColor(Color.white);
	//	g2d.drawString("Score : " + score, 0, 10);
	//	g2d.drawString("Lives : " + player.getLives(), 0, GameBoard.HEIGHT -2);
	//	int levelTextWidth = g2d.getFontMetrics().stringWidth("Level : " + 999);
	//	g2d.drawString(String.format("Level : %03d", level), GameBoard.WIDTH -levelTextWidth, GameBoard.HEIGHT -2);
	//}
	
	/**
	 * Displays the end screen - the Game Over text.
	 * 
	 * @param g2d The graphic instance of the current buffer.
	 * @param text The text to display for the end screen.
	 */
	private void drawEndScreen(Graphics2D g2d, String text) {
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, GameBoard.WIDTH, GameBoard.HEIGHT);
		int width = g2d.getFontMetrics().stringWidth(text);
		g2d.setColor(Color.white);
		g2d.drawString(text, (GameBoard.WIDTH -width) / 2, GameBoard.HEIGHT / 2);
	}
	
	/**
	 * Handles moving the enemies according to a specific movement pattern
	 * as is in the original game.
	 */
	private void moveEnemies() {
		/* The movement was done using counters
		 * that count up to 3. That way we know when to move to each direction
		 * starting with the right, moving to it three times, then moving left
		 * three times. Once both counters are full we are moving down once and
		 * then starting the cycle all over again by reseting the movement
		 * counting variables. */
		
		moveTimer++;
		if (moveTimer == timeToMove) {
			moveTimer = 0;
			if (movedRight < 3) {
				movedRight++;
				moveAliens(MoveDirection.Right);
			} else if (movedLeft < 3) {
				movedLeft++;
				moveAliens(MoveDirection.Left);
			}  else {
				moveAliens(MoveDirection.Down);
				movedLeft = 0;
				movedRight = 0;
			}
		}
	}
	
	/**
	 * @return whether the player has won the game or not.
	 */
	private boolean hasWon() {
		for (GameObject obj : handler.list())
			if (obj instanceof Enemy)
				return false;
		return true;
	}
	
	/**
	 * Initializes the barriers of the player in the game as their placement is
	 * static and consistent with all levels.
	 */
	private void initBarriers() {
		final float MARGIN = 28f;
		for (int i = 0; i < 4; i++) {
			handler.add(new Barrier(((i +1) *MARGIN) +(i *20), GameBoard.HEIGHT -60, handler));
		}
	}
	
	/**
	 * Initializes the enemies using specific coordinates and a specific margin. The values
	 * here all correspond to the specific resolution of the game. The resolution is
	 * consistent, therefore the absolute positioning here is fine.
	 */
	private void initEnemies() {
		final float MARGIN = 17.5f;
		int iPos;
		for (int i = 0; i < 14; i++) {
			iPos = i * 13;
			handler.add(new Enemy(MARGIN + 1.5f  + iPos, 20, GameObjectID.EnemyTop, handler, score, player));
			handler.add(new Enemy(MARGIN + 0.25f + iPos, 40, GameObjectID.EnemyMid, handler, score, player));
			handler.add(new Enemy(MARGIN + 0.25f + iPos, 60, GameObjectID.EnemyMid, handler, score, player));
			handler.add(new Enemy(MARGIN         + iPos, 80, GameObjectID.EnemyBot, handler, score, player));
		}
	}
	
	/**
	 * Handles moving the aliens in the proper manner, as
	 * a block, like a contant grid or array. The movement
	 * is based on a direction and is on purpose choppy as
	 * to emulate the original game.
	 * 
	 * @param dir The direction to move the aliens.
	 */
	private void moveAliens(MoveDirection dir) {
		MoveDirection left = MoveDirection.Left;
		MoveDirection right = MoveDirection.Right;
		MoveDirection down = MoveDirection.Down;
		
		int amount;
		
		if (dir == right || dir == left) {
			amount = (dir == right) ? 10 : -10;
			for (GameObject object : handler.list()) {
				if (shouldMoveEnemy(object)) {
					object.moveX(amount);
				}
			}
		} else if (dir == down) {
			amount = 5;
			for (GameObject object : handler.list()) {
				if (shouldMoveEnemy(object)) {
					object.moveY(amount);
				}
			}
		}
	}
	
	/**
	 * For a given enemy, decides if the enemy should be moved or not.
	 * An emeny that is dead and its instance is alive only for the game
	 * to display it's death animation is an example for an entity that
	 * should not be moved in the next update.
	 * The same is true for the player, while displaying it's death
	 * animation the player should not be able to move.
	 * 
	 * @param object The instance to check if it should be moved or not.
	 * @return Whether the position of the enemy should be updated or not.
	 */
	private boolean shouldMoveEnemy(GameObject object) {
		return object instanceof Enemy
			&& !(object.is(GameObjectID.EnemyExplosion)
			|| object.is(GameObjectID.EnemyBoss));
	}
	
	/**
	 * Checks the collision of all the game objects and updates them
	 * accordingly using a sort of observer design pattern.
	 */
	private void checkCollision() {
		GameObject a, b;
		for (int i = 0; i < handler.list().size(); i++) {
			a = handler.get(i);
			for (int j = 0; j < handler.list().size(); j++) {
				b = handler.get(j);
				if (a != b && a.intersects(b)) {
					a.collided(b);
					b.collided(a);
				}
			}
		}
	}
	
	/**
	 * @return whether or not the enemies should move.
	 */
	private boolean shouldMove() {
		return !(gameCompleted || hasLost());
	}
	
	/**
	 * @return whether or not the player lost the game.
	 */
	private boolean hasLost() {
		return aliensPassed || (player.isDead() && player.getLives().at(0));
	}
	
	/**
	 * Initializes the handler and the entity instances.
	 */
	private void initHandler() {
		handler = new Handler<>();
		player = new Player(handler);
		handler.add(player);
		initBarriers();
		initEnemies();
	}
	
	/**
	 * Initializes all of the counters and variables in one place.
	 */
	private void initPrimitiveVariables() {
		movedLeft = 0;
		movedRight = 1;
		moveTimer = 0;
		timeToMove = 70;
		gameCompleted = false;
	}
	
	/**
	 * Prepares the state for the next level by reseting the instances.
	 */
	private void prepareNextLevel() {
		level.increment();
		timeToMove = (int)clampi(timeToMove -5, 10, 120);
		movedLeft = 0;
		movedRight = 1;
		
		initEnemies();
	}
	
}