package spaceinvaders.objects;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import spaceinvaders.extenable.Loopable;
import spaceinvaders.handlers.Handler;

/**
 * Represents the basis for a game object in the game. Each game object
 * is loopable and has the following properties.
 */
public abstract class GameObject implements Loopable {
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected float dx;
	protected float dy;
	protected GameObjectID id;
	protected Rectangle2D.Float bounds;
	protected Handler<GameObject> handler;
	
	/**
	 * @param x the starting x coordinate of the object.
	 * @param y the starting y coordinate of the object.
	 * @param width the width of the object.
	 * @param height the height of the object.
	 * @param id the GameObjectID of the object.
	 * @param handler the list of handlers for the rest of the objects.
	 */
	public GameObject(float x, float y, float width, float height, GameObjectID id, Handler<GameObject> handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.handler = handler;
		dx = dy = 0;
		bounds = new Rectangle2D.Float(x, y, width, height);
	}
	
	/**
	 * Handles the collision with a given object.
	 * 
	 * @param with the object that collided with.
	 */
	public abstract void collided(GameObject with);
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(getSprite(), (int)x, (int)y, null);
	}
	
	/**
	 * Checks if the object intersects with a different object in the game.
	 * @param o the object to check the intersection with.
	 * @return whether or not the object intersects with the other object.
	 */
	public boolean intersects(GameObject o) {
		return getBounds().intersects(o.getBounds());
	}
	
	/**
	 * @return the bounding box of the game.
	 */
	public Rectangle2D.Float getBounds() {
		bounds.setRect(x, y, width, height);
		return bounds;
	}
	
	/**
	 * @param id the id to check for the object.
	 * @return whether or not the game object has the id.
	 */
	public boolean is(GameObjectID id) {
		return this.id == id;
	}
	
	/**
	 * Removes the object from the handlers list.
	 */
	protected void removeSelf() {
		handler.remove(this);
	}
	
	// Getters and setters for the state of the object.
	
	public float getX() {return x; }
	public void setX(float x) { this.x = x; }
	public void moveX(float x) { this.x += x; }
	
	public float getY() {  return y;}
	public void setY(float y) { this.y = y; }
	public void moveY(float y) { this.y += y; }
	
	public float getWidth() { return width; }
	public void setWidth(float width) { this.width = width; }
	
	public float getHeight() { return height; }
	public void setHeight(float height) { this.height = height; }
	
	public float getDx() { return dx; }
	public void setDx(float dx) { this.dx = dx; }
	
	public float getDy() { return dy; }
	public void setDy(float dy) { this.dy = dy; }
	
	public GameObjectID getId() { return id; }
	public void setId(GameObjectID id) { this.id = id; }
	
	public BufferedImage getSprite() { return id.image(); }
}