package spaceinvaders.objects;

import spaceinvaders.handlers.Handler;
import spaceinvaders.main.GameBoard;

/**
 * A shot instance to represent a shot in the game and the information
 * that it should pack. This allows the shots to be projectiles and not
 * instant as we store the information about the sender of the shot.
 */
public class Shot extends GameObject {
	
	/* Who sent the shot. */
	public final GameObjectID sender;
	
	/**
	 * Creates an instance of the shot.
	 * 
	 * @param x the starting x position.
	 * @param y the starting y position.
	 * @param dy the velocity of the shot.
	 * @param handler the list of all game objects.
	 * @param sender the sender of the shot.
	 */
	public Shot(float x, float y, float dy, Handler<GameObject> handler, GameObjectID sender) {
		super(x, y, 1, 3, GameObjectID.Shot, handler);
		this.dy = dy;
		this.sender = sender;
	}
	
	@Override
	public void update(int tick) {
		y += dy;
		if (y +height <= 0 || y >= GameBoard.HEIGHT)
			removeSelf();
	}
	
	@Override
	public void collided(GameObject with) {
		if ((with instanceof Enemy && sender == GameObjectID.Player)
		||	(with instanceof Player && sender == GameObjectID.EnemyBoss)
		||	(with instanceof Barrier && !with.is(GameObjectID.BarrierExplosion)))
			removeSelf();
	}
	
}