package spaceinvaders.handlers;

import java.awt.Graphics2D;
import java.util.LinkedList;

import spaceinvaders.extenable.Loopable;

public class Handler<T extends Loopable> {
	
	private LinkedList<T> list = new LinkedList<T>();
	
	public Handler() {
		list.clear();
	}
	
	public void update(int tick) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).update(tick);
		}
	}
	
	public void render(Graphics2D g2d) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).render(g2d);
		}
	}
	
	public T get(int i) { return list.get(i); }
	public LinkedList<T> list() { return list; }
	public void add(T t) { list.add(t); }
	public void remove(T t) { list.remove(t); }
	
}