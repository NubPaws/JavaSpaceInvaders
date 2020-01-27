package spaceinvaders.objects;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import spaceinvaders.extenable.Updatable;
import spaceinvaders.handlers.Handler;

public abstract class GameObject implements Updatable {
	
	protected float x, y, width, height, dx, dy;
	protected GameObjectID id;
	protected Rectangle2D.Float bounds;
	protected Handler<GameObject> handler;
	
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
	
	public abstract void collided(GameObject with);
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(getSprite(), (int)x, (int)y, null);
	}
	
	public boolean intersects(GameObject o) {
		return getBounds().intersects(o.getBounds());
	}
	
	public Rectangle2D.Float getBounds() {
		bounds.setRect(x, y, width, height);
		return bounds;
	}
	
	public boolean is(GameObjectID id) { return this.id == id; }
	
	protected void removeSelf() { handler.remove(this); }
	
	
	public float getX() { return x; }
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