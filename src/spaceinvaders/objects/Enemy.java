package spaceinvaders.objects;

import java.util.Random;

import spaceinvaders.gamestate.GameGS;
import spaceinvaders.handlers.Handler;
import spaceinvaders.handlers.SoundHandler;
import spaceinvaders.main.GameBoard;
import spaceinvaders.utils.Counter;
import spaceinvaders.utils.Flag;

public class Enemy extends GameObject {
	
	private static final Random rand = new Random();
	
	private short deathTimer = 0;
	
	private Counter score;
	private Flag playerSprayAndPray;
	private Counter playerSprayAndPrayTimer;
	
	public Enemy(float x, float y, GameObjectID id, Handler<GameObject> handler, Counter score, Player player) {
		super(x, y, 0, 0, id, handler);
		width = getSprite().getWidth();
		height = getSprite().getHeight();
		if (id == GameObjectID.EnemyBoss) {
			dx = -1.75f;
		}
		
		this.score = score;
		this.playerSprayAndPray = player.getSprayAndPray();
		this.playerSprayAndPrayTimer = player.getSprayAndPrayTimer();
	}
	
	@Override
	public void update(int tick) {
		if (y >= GameBoard.HEIGHT -100)
			GameGS.aliensPassed = true;
		
		tryToShoot(tick);
		
		// If the enemy has exploded, remove it after 45 ticks.
		if (this.is(GameObjectID.EnemyExplosion)) {
			deathTimer++;
			if (deathTimer == 45) {
				removeSelf();
			}
		} else {
			x += dx; // Move the object if it has DX
		}
		
		if (x +width < 0)
			removeSelf();
	}
	
	@Override
	public void collided(GameObject with) {
		if (wasShotByPlayer(with)) {
			switch (id) {
			case EnemyTop:
				x -= 3; // Align explosion
				score.increment(40);
				break;
			case EnemyMid:
				x -= 1; // Align explosion
				score.increment(20);
				break;
			case EnemyBot:
				score.increment(40);
				break;
			case EnemyBoss:
				score.increment(100);
				playerSprayAndPray.set(true);
				playerSprayAndPrayTimer.set(0);
				break;
			default:
				break;
			}
			id = GameObjectID.EnemyExplosion;
			width = height = 0;
			SoundHandler.play("explode.aiff");
		}
	}
	
	private boolean wasShotByPlayer(GameObject shooterToCheck) {
		return shooterToCheck.is(GameObjectID.Shot) && ((Shot)shooterToCheck).sender == GameObjectID.Player;
	}
	
	private void tryToShoot(int tick) {
		if ((tick == 37 || tick == 13) && rand.nextInt(100) == 37) {
			handler.add(new Shot(x + (width / 2), y + height + 1, 5, handler, id));
			SoundHandler.play("laser.aiff");
		}
	}
	
}