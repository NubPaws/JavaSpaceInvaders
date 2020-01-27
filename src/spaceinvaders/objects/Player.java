package spaceinvaders.objects;

import static spaceinvaders.utils.Helper.clamp;
import spaceinvaders.handlers.Handler;
import spaceinvaders.handlers.Keys;
import spaceinvaders.handlers.SoundHandler;
import spaceinvaders.main.GameBoard;
import spaceinvaders.main.GameLoop;

public class Player extends GameObject {
	
	private static boolean sprayAndPray = false;
	private static int sprayAndPrayTimer = 0;
	
	private byte lives = 3;
	private short deathTimer = 0;
	private boolean dead = false;
	private final int shotInterval = 20;
	private int shotTimer = shotInterval;
	
	public Player(Handler<GameObject> handler) {
		super(0, 0, 0, 0, GameObjectID.Player, handler);
		
		dx = 2.5f;
		
		width = getSprite().getWidth();
		height = getSprite().getHeight();
		
		x = (GameBoard.WIDTH -width) /2;
		y = GameBoard.HEIGHT -height -25;
	}
	
	@Override
	public void update(int tick) {
		if (dead) {
			deathTimer++;
			if (deathTimer == 75) {
				deathTimer = 0;
				restart();
			}
		} else {
			if (Keys.isHeld(Keys.A) || Keys.isHeld(Keys.LeftArrow)) {
				x -= dx;
			} else if (Keys.isHeld(Keys.D) || Keys.isHeld(Keys.RightArrow)) {
				x += dx;
			}
			
			tryToShoot();
		}
		if (shotTimer < shotInterval) shotTimer++;
		
		if(sprayAndPray) {
			sprayAndPrayTimer++;
			if (sprayAndPrayTimer == (int)(GameLoop.TICKS *7.5f))
				sprayAndPray = false;
		}
		
		x = clamp(x, 0, GameBoard.WIDTH -width);
	}
	
	@Override
	public void collided(GameObject with) {
		if (with.is(GameObjectID.Shot)) {
			id = GameObjectID.PlayerExplosion;
			dead = true;
			SoundHandler.play("explode.aiff");
		}
	}
	
	public void restart() {
		if (lives > 0 ) {
			lives--;
			dead = false;
			id = GameObjectID.Player;
		}
	}
	
	public boolean isDead() { return dead; }
	public byte getLives() { return lives; }
	
	private boolean canShoot() { return sprayAndPray || shotTimer == shotInterval; }
	private boolean wantToShoot() { return Keys.isPressed(Keys.Space); }
	private boolean canAndWantToShoot() { return wantToShoot() && canShoot(); }
	
	private void tryToShoot() {
		if (canAndWantToShoot()) {
			SoundHandler.play("laser.aiff");
			handler.add(new Shot(x +(width /2), y -3, -5, handler, "Player"));
			shotTimer = 0;
		}
	}
	
	public static void activateSpray() {
		sprayAndPray = true;
		sprayAndPrayTimer = 0;
	}
	
}