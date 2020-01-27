package spaceinvaders.objects;

import spaceinvaders.handlers.Handler;
import spaceinvaders.main.GameBoard;

public class Shot extends GameObject {
	
	public final String sender;
	
	public Shot(float x, float y, float dy, Handler<GameObject> handler, String sender) {
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
		if ((with instanceof Enemy && sender.equals("Player"))
		||	(with instanceof Player && sender.equals("Enemy"))
		||	(with instanceof Barrier && !with.is(GameObjectID.BarrierExplosion)))
			removeSelf();
	}
	
}