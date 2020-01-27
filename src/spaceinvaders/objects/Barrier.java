package spaceinvaders.objects;

import spaceinvaders.handlers.Handler;
import spaceinvaders.handlers.SoundHandler;

public class Barrier extends GameObject {
	
	private int deathTimer = 0;
	private byte count = 1;
	
	public Barrier(float x, float y, Handler<GameObject> handler) {
		super(x, y, 0, 0, GameObjectID.Barrier1, handler);
		width = getSprite().getWidth();
		height = getSprite().getHeight();
	}
	
	@Override
	public void update(int tick) {
		if (this.is(GameObjectID.BarrierExplosion)) deathTimer++;
		if (deathTimer == 30)
			removeSelf();
	}
	
	@Override
	public void collided(GameObject with) {
		count++;
		if (count == 2) {
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
			count = 1;
		}
	}
	
}