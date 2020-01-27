package spaceinvaders.objects;

import java.awt.image.BufferedImage;

import spaceinvaders.handlers.ImagesHandler;

public enum GameObjectID {
	
	EnemyTop("enemy/top"),
	EnemyMid("enemy/mid"),
	EnemyBot("enemy/bot"),
	EnemyBoss("enemy/boss"),
	EnemyExplosion("enemy/enemy_explosion"),
	Barrier1("barrier/barrier1"),
	Barrier2("barrier/barrier2"),
	Barrier3("barrier/barrier3"),
	Barrier4("barrier/barrier4"),
	BarrierExplosion("barrier/barrier_explosion"),
	Player("player/player"),
	PlayerExplosion("player/player_explosion"),
	Shot("shot");
	
	private final BufferedImage image;
	
	GameObjectID(String res) {
		image = ImagesHandler.get("sprites/" + res + ".png");
	}
	
	public BufferedImage image() { return image; }
	
}