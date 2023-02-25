package spaceinvaders.handlers;

import java.awt.Graphics2D;
import java.util.LinkedList;

import spaceinvaders.extenable.Loopable;

/**
 * A handler to update and render multiple items that are loopable.
 * A handler is a loopable collection of loopables.
 */
public class Handler<T extends Loopable> implements Loopable {
	
	private LinkedList<T> list;
	
	/**
	 * Initializes an instnace of the handler.
	 */
	public Handler() {
		list = new LinkedList<T>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(int tick) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).update(tick);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics2D g2d) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).render(g2d);
		}
	}
	
	/**
	 * @param i index of the element in the list.
	 * @return the element as returned by LinkedList<T>.get().
	 */
	public T get(int i) {
		return list.get(i);
	}
	
	/**
	 * @return the list of loopables in the handler as is.
	 */
	public LinkedList<T> list() {
		return list;
	}
	
	/**
	 * Add an element into the handler to be handled.
	 * 
	 * @param t the element to add.
	 */
	public void add(T t) {
		list.add(t);
	}
	
	/**
	 * Removes an element based on a given instance of that element.
	 * 
	 * @param t the element to be removed.
	 */
	public void remove(T t) {
		list.remove(t);
	}
	
}