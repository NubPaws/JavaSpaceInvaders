package spaceinvaders.objects;

import static spaceinvaders.utils.Helper.clampf;
import spaceinvaders.handlers.Handler;
import spaceinvaders.handlers.Keys;
import spaceinvaders.handlers.SoundHandler;
import spaceinvaders.main.GameBoard;
import spaceinvaders.main.GameLoop;
import spaceinvaders.utils.Counter;
import spaceinvaders.utils.Flag;

/**
 * The player class, holding information about the player and what not.
 */
public class Player extends GameObject {
	
	/* State for the life and death of the player. */
	private Counter lives = new Counter(3);
	private int deathTimer = 0;
	private boolean dead = false;
	
	/* State for the shooting ability of the player. */
	private final int shotInterval = 20;
	private int shotTimer = shotInterval;
	
	/* State for the special ability of the player to shoot faster. */
	private Flag sprayAndPray = new Flag(false);
	private Counter sprayAndPrayTimer = new Counter(0);
	
	/**
	 * Creates an instance of the player.
	 * 
	 * @param handler the list of game objects in the game.
	 */
	public Player(Handler<GameObject> handler) {
		// Location must be set differently in this class, therefore the values start at 0.
		super(0, 0, 0, 0, GameObjectID.Player, handler);
		
		// Movement speed horizontally (the player cannot move vertically).
		dx = 2.5f;
		
		width = getSprite().getWidth();
		height = getSprite().getHeight();
		
		setPosition();
	}
	
	private void setPosition() {
		x = (GameBoard.WIDTH - width) / 2;
		y = GameBoard.HEIGHT - height - 25;
	}
	
	@Override
	public void update(int tick) {
		/* Make it so if the player is dead it cannot move and the
		 * player won't get any more updates until it comes back to
		 * live it has more lives left.
		 */
		if (dead) {
			deathTimer++;
			if (deathTimer == GameLoop.seconds(1.25f)) {
				deathTimer = 0;
				restart();
			}
			return;
		}
		
		/* Handle player movement allowing for both arrow keys and A-D keys */
		if (Keys.isHeld(Keys.A) || Keys.isHeld(Keys.LeftArrow)) {
			x -= dx;
		} else if (Keys.isHeld(Keys.D) || Keys.isHeld(Keys.RightArrow)) {
			x += dx;
		}
		
		tryToShoot();
		
		/* Give the player a delay on its shootings. */
		if (shotTimer < shotInterval)
			shotTimer++;
		
		/* If the player can spray than it is handled differently. */
		if(sprayAndPray.on()) {
			sprayAndPrayTimer.increment();
			if (sprayAndPrayTimer.at(GameLoop.seconds(7.5f))) {
				sprayAndPray.set(false);
				sprayAndPrayTimer.set(0);
			}
		}
		
		// Don't let the player wander off the screen.
		x = clampf(x, 0, GameBoard.WIDTH - width);
	}
	
	@Override
	public void collided(GameObject with) {
		if (with.is(GameObjectID.Shot)) {
			id = GameObjectID.PlayerExplosion;
			dead = true;
			SoundHandler.play("explode.aiff");
		}
	}
	
	/**
	 * Restarts the player after death.
	 */
	public void restart() {
		if (lives.getValue() > 0 ) {
			lives.decrement();
			dead = false;
			id = GameObjectID.Player;
			setPosition();
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
	
	public boolean isDead() {
		return dead;
	}
	public Counter getLives() {
		return lives;
	}
	
	private boolean canShoot() {
		return sprayAndPray.on() || shotTimer == shotInterval;
	}
	private boolean wantToShoot() {
		return Keys.isPressed(Keys.Space);
	}
	private boolean canAndWantToShoot() {
		return wantToShoot() && canShoot();
	}
	
	/**
	 * Checks if the player wants to shoot, meaning the user pressed space.
	 * Checks if the player can shoot, meaning enough time has passed between
	 * the previous and the current shot.
	 * If the player can and want to shoot, then shoot by generating the shot
	 * and shooting it.
	 */
	private void tryToShoot() {
		if (canAndWantToShoot()) {
			SoundHandler.play("laser.aiff");
			handler.add(new Shot(x +(width /2), y -3, -5, handler, GameObjectID.Player));
			shotTimer = 0;
		}
	}
	
}