package spaceinvaders.objects;

import static spaceinvaders.utils.Helper.clampf;
import spaceinvaders.handlers.Handler;
import spaceinvaders.handlers.Keys;
import spaceinvaders.handlers.SoundHandler;
import spaceinvaders.main.GameBoard;
import spaceinvaders.main.GameLoop;
import spaceinvaders.utils.Counter;
import spaceinvaders.utils.Flag;

public class Player extends GameObject {
	
	private Counter lives = new Counter(3);
	private short deathTimer = 0;
	private boolean dead = false;
	private final int shotInterval = 20;
	private int shotTimer = shotInterval;
	
	private Flag sprayAndPray = new Flag(false);
	private Counter sprayAndPrayTimer = new Counter(0);
	
	public Player(Handler<GameObject> handler) {
		super(0, 0, 0, 0, GameObjectID.Player, handler);
		
		dx = 2.5f;
		
		width = getSprite().getWidth();
		height = getSprite().getHeight();
		
		x = (GameBoard.WIDTH - width) / 2;
		y = GameBoard.HEIGHT - height - 25;
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
		
		if(sprayAndPray.on()) {
			sprayAndPrayTimer.increment();
			if (sprayAndPrayTimer.at((int)(GameLoop.TICKS *7.5f))) {
				sprayAndPray.set(false);
				sprayAndPrayTimer.set(0);
			}
		}
		
		x = clampf(x, 0, GameBoard.WIDTH -width);
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
		if (lives.getValue() > 0 ) {
			lives.decrement();
			dead = false;
			id = GameObjectID.Player;
		}
	}
	
	/**
	 * @return the spray and pray flag for the player.
	 */
	public Flag getSprayAndPray() {
		return sprayAndPray;
	}
	
	/**
	 * @return the spray and pray timer (counter) for the player.
	 */
	public Counter getSprayAndPrayTimer() {
		return sprayAndPrayTimer;
	}
	
	public boolean isDead() { return dead; }
	public Counter getLives() { return lives; }
	
	private boolean canShoot() { return sprayAndPray.on() || shotTimer == shotInterval; }
	private boolean wantToShoot() { return Keys.isPressed(Keys.Space); }
	private boolean canAndWantToShoot() { return wantToShoot() && canShoot(); }
	
	private void tryToShoot() {
		if (canAndWantToShoot()) {
			SoundHandler.play("laser.aiff");
			handler.add(new Shot(x +(width /2), y -3, -5, handler, "Player"));
			shotTimer = 0;
		}
	}
	
}