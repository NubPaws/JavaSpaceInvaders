package spaceinvaders.objects;

import spaceinvaders.handlers.Handler;
import spaceinvaders.handlers.SoundHandler;

/**
 * Class for the instances of the barriers that protect the player in the game.
 */
public class Barrier extends GameObject {
	
	/* Timer for the death animation. */
	private int deathTimer;
	
	/**
	 * Initializes a barrier object at the designed location.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 * @param handler the handler of the game objects of the game.
	 */
	public Barrier(float x, float y, Handler<GameObject> handler) {
		super(x, y,
			GameObjectID.Barrier1.image().getWidth(),
			GameObjectID.Barrier1.image().getHeight(),
			GameObjectID.Barrier1, handler);
		
		deathTimer = 0;
	}
	
	@Override
	public void update(int tick) {
		if (this.is(GameObjectID.BarrierExplosion)) deathTimer++;
		if (deathTimer == 30)
			removeSelf();
	}
	
	@Override
	public void collided(GameObject with) {
		switch (id) {
		case Barrier1:
			id = GameObjectID.Barrier2;
			break;
		case Barrier2:
			id = GameObjectID.Barrier3;
			break;
		case Barrier3:
			id = GameObjectID.Barrier4;
			break;
		case Barrier4:
			id = GameObjectID.BarrierExplosion;
			SoundHandler.play("explode.aiff");
			break;
		default:
			break;
		}
	}
	
}