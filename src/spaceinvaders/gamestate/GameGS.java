package spaceinvaders.gamestate;

import static spaceinvaders.utils.Helper.clamp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import spaceinvaders.handlers.Handler;
import spaceinvaders.handlers.Keys;
import spaceinvaders.main.GameBoard;
import spaceinvaders.objects.Barrier;
import spaceinvaders.objects.Enemy;
import spaceinvaders.objects.GameObject;
import spaceinvaders.objects.GameObjectID;
import spaceinvaders.objects.Player;

public class GameGS extends GameState {
	
	public static int score;
	public static boolean aliensPassed;
	
	private Handler<GameObject> handler;
	private byte movedLeft, movedRight;
	private int moveTimer, timeToMove;
	private byte level;
	private boolean gameCompleted;
	private Random rand;
	
	private Player player;
	
	public GameGS(GameStateManager gsm) {
		super(gsm);
	}
	
	@Override
	public void init() {
		score = 0;
		aliensPassed = false;
		
		initPrimitiveVariables();
		rand = new Random();
		
		initHandler();
	}
	
	
	
	@Override
	public void update(int tick) {
		if (shouldMove()) {
			handler.update(tick);
			moveEnemies();
			checkCollision();
		}
		if (hasWon()) {
			if (level == Byte.MAX_VALUE) {
				gameCompleted = true;
				return;
			}
			prepareNextLevel();
		}
		
		if (tick <= 3 && rand.nextInt(100) <= 5) {
			handler.add(new Enemy(GameBoard.WIDTH, 12.5f, GameObjectID.EnemyBoss, handler));
		}
		
		if (Keys.isPressed(Keys.Escape))
			dispose();
	}
	
	@Override
	public void render(Graphics2D g2d) {
		handler.render(g2d);
		
		drawHUD(g2d);
		
		if (hasLost())
			drawEndScreen(g2d, "Game Over!");
		if (gameCompleted)
			drawEndScreen(g2d, "You have finished the game");
	}
	
	@Override
	public void dispose() {
		handler.list().clear();
		handler = null;
		gsm.setState(GameStateID.Menu);
	}
	
	
	private void drawHUD(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.drawString("Score : " + score, 0, 10);
		g2d.drawString("Lives : " + player.getLives(), 0, GameBoard.HEIGHT -2);
		int levelTextWidth = g2d.getFontMetrics().stringWidth("Level : " + 999);
		g2d.drawString(String.format("Level : %03d", level), GameBoard.WIDTH -levelTextWidth, GameBoard.HEIGHT -2);
	}
	
	private void drawEndScreen(Graphics2D g2d, String text) {
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, GameBoard.WIDTH, GameBoard.HEIGHT);
		int width = g2d.getFontMetrics().stringWidth(text);
		g2d.setColor(Color.white);
		g2d.drawString(text, (GameBoard.WIDTH -width) /2, GameBoard.HEIGHT /2);
	}
	
	
	private void moveEnemies() {
		moveTimer++;
		if (moveTimer == timeToMove) {
			moveTimer = 0;
			if (movedRight < 3) {
				movedRight++;
				moveAliens(0);
			} else if (movedLeft < 3) {
				movedLeft++;
				moveAliens(1);
			}  else {
				moveAliens(2);
				movedLeft = movedRight = 0;
			}
		}
	}
	
	
	private boolean hasWon() {
		for (GameObject obj : handler.list())
			if (obj instanceof Enemy)
				return false;
		return true;
	}
	
	
	private void initBarriers() {
		final float MARGIN = 28f;
		for (int i = 0; i < 4; i++) {
			handler.add(new Barrier(((i +1) *MARGIN) +(i *20), GameBoard.HEIGHT -60, handler));
		}
	}
	
	
	private void initEnemies() {
		final float MARGIN = 17.5f;
		for (int i = 0; i < 14; i++) {
			handler.add(new Enemy(MARGIN +1.5f +(i *13), 20, GameObjectID.EnemyTop, handler));
			handler.add(new Enemy(MARGIN +.25f +(i *13), 40, GameObjectID.EnemyMid, handler));
			handler.add(new Enemy(MARGIN +.25f +(i *13), 60, GameObjectID.EnemyMid, handler));
			handler.add(new Enemy(MARGIN +(i *13), 80, GameObjectID.EnemyBot, handler));
		}
	}
	
	
	private void moveAliens(int dir) {
		// dir : 0->right, 1->left, 2->down
		if (dir == 0 || dir == 1) { 
			dir = (dir == 0) ? 10 : -10;
			for (GameObject object : handler.list()) {
				if (shouldMoveEnemy(object)) {
					object.moveX(dir);
				}
			}
		} else if (dir == 2) {
			dir = 5;
			for (GameObject object : handler.list()) {
				if (shouldMoveEnemy(object)) {
					object.moveY(dir);
				}
			}
		}
	}
	
	private boolean shouldMoveEnemy(GameObject object) {
		return object instanceof Enemy && !(object.is(GameObjectID.EnemyExplosion) || object.is(GameObjectID.EnemyBoss));
	}
	
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
	
	
	private boolean shouldMove() {
		return !(gameCompleted || hasLost());
	}
	
	
	private boolean hasLost() {
		return aliensPassed || (player.isDead() && player.getLives() == 0);
	}
	
	private void initHandler() {
		handler = new Handler<>();
		player = new Player(handler);
		handler.add(player);
		initBarriers();
		initEnemies();
	}
	
	private void initPrimitiveVariables() {
		movedLeft = 0;
		movedRight = 1;
		moveTimer = 0;
		timeToMove = 70;
		level = 1;
		gameCompleted = false;
	}
	
	private void prepareNextLevel() {
		level++;
		timeToMove = (int)clamp(timeToMove -5, 10, 120);
		movedLeft = 0;
		movedRight = 1;
		
		initEnemies();
	}
	
}