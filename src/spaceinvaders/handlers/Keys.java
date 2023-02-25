package spaceinvaders.handlers;

import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

/**
 * A global class to handle the keys input from the user
 * working with Java's key input thread.
 * 
 * In Swing and in AWT there exists a seperate thread for
 * input from the user, where talking about keyboard input
 * or mouse input. This class uses that for it's purpose.
 * We must add an instance of the Keys (which is a singleton
 * therefore there is only one instance of this final class)
 * to the game window/board. There is also a need to call the
 * update function and the render function of the Keys class.
 * 
 * Everything here is static as the Keys class should be
 * accessable from everywhere.
 */
public final class Keys extends KeyAdapter {
	
	/* Members to store the information about the keys. Each key is
	 * represented as an index of the keyState array, where the
	 * prevKeyState is an array for the state of the key in the
	 * previous iteration and the keyState is the state of the key
	 * in the current iteration. We can use that to detect clicks rather
	 * than holding the buttons as Java has a bit of a buggy detection for
	 * clicks.
	 */
	private static final int KEYS = 12;
	private static final boolean[] keyState = new boolean[KEYS];
	private static final boolean[] prevKeyState = new boolean[KEYS];
	
	/* The indices for each key, the order here is arbitrary. */
	public static final int W = 0,
							A = 1,
							S = 2,
							D = 3,
							Space = 4,
							Escape = 5,
							Enter = 6,
							UpArrow = 7,
							LeftArrow = 8,
							DownArrow = 9,
							RightArrow = 10;
	
	/**
	 * Updates the Key class with, setting the previous key state
	 * to be equal to the current key state.
	 */
	public static final void update() {
		for (int i = 0; i < KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}
	
	/**
	 * Inner method used to set the key value making editting this class
	 * a bit more simple.
	 * 
	 * @param key the index for the key (should not use actual indices but the
	 * variables names for each key).
	 * @param pressed the state of the key - true for pressed and false for released.
	 */
	private static final void setKey(int key, boolean pressed) {
		switch (key) {
			case KeyEvent.VK_W:
				keyState[W] = pressed;
				break;
			case KeyEvent.VK_A:
				keyState[A] = pressed;
				break;
			case KeyEvent.VK_S:
				keyState[S] = pressed;
				break;
			case KeyEvent.VK_D:
				keyState[D] = pressed;
				break;
			case KeyEvent.VK_UP:
				keyState[UpArrow] = pressed;
				break;
			case KeyEvent.VK_LEFT:
				keyState[LeftArrow] = pressed;
				break;
			case KeyEvent.VK_DOWN:
				keyState[DownArrow] = pressed;
				break;
			case KeyEvent.VK_RIGHT:
				keyState[RightArrow] = pressed;
				break;
			case KeyEvent.VK_SPACE:
				keyState[Space] = pressed;
				break;
			case KeyEvent.VK_ESCAPE:
				keyState[Escape] = pressed;
				break;
			case KeyEvent.VK_ENTER:
				keyState[Enter] = pressed;
				break;
		}
	}
	
	/**
	 * A pressed key is pressed for only one update frame.
	 * The check for the key pressed is if the key is pressed
	 * and in the previous state it wasn't pressed.
	 * 
	 * @param key the key to check if it is pressed.
	 * @return whether that key is pressed or not.
	 */
	public static final boolean isPressed(int key) {
		return keyState[key] && !prevKeyState[key];
	}
	
	/**
	 * @param key the key to check if it is pressed.
	 * @return whether that key is pressed or not.
	 */
	public static final boolean isHeld(int key) {
		return keyState[key];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		setKey(e.getKeyCode(), true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		setKey(e.getKeyCode(), false);
	}
	
}