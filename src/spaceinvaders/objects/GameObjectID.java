package spaceinvaders.objects;

import java.awt.image.BufferedImage;

import spaceinvaders.handlers.ImagesHandler;

/**
 * Stores information about the identification of each of the
 * game objects and also store all of the images that are used
 * by each of the objects in one place to avoid duplicates
 * in the memory for the iamges used.
 */
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
	
	/**
	 * Initializes a game object id.
	 * @param res the sprite name relative to the res/sprite folder.
	 */
	GameObjectID(String res) {
		image = ImagesHandler.get("sprites/" + res + ".png");
	}
	
	/**
	 * @return the image associated with the id.
	 */
	public BufferedImage image() {
		return image;
	}
	
}