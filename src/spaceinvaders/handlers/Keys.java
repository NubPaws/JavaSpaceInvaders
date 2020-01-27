package spaceinvaders.handlers;

import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

public final class Keys extends KeyAdapter {
	
	private static final int KEYS = 12;
	private static final boolean[] keyState = new boolean[KEYS];
	private static final boolean[] pKeyState = new boolean[KEYS];
	
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
	
	public static final void update() {
		for (int i = 0; i < KEYS; i++) {
			pKeyState[i] = keyState[i];
		}
	}
	
	private static final void setKey(int key, boolean pressed) {
		if (key == KeyEvent.VK_W)
			keyState[W] = pressed;
		else
			if (key == KeyEvent.VK_A)
			keyState[A] = pressed;
		else
			if (key == KeyEvent.VK_S)
			keyState[S] = pressed;
		else
			if (key == KeyEvent.VK_D)
			keyState[D] = pressed;
		else
			if (key == KeyEvent.VK_UP)
			keyState[UpArrow] = pressed;
		else
			if (key == KeyEvent.VK_LEFT)
			keyState[LeftArrow] = pressed;
		else
			if (key == KeyEvent.VK_DOWN)
			keyState[DownArrow] = pressed;
		else
			if (key == KeyEvent.VK_RIGHT)
			keyState[RightArrow] = pressed;
		else
			if (key == KeyEvent.VK_SPACE)
			keyState[Space] = pressed;
		else
			if (key == KeyEvent.VK_ESCAPE)
			keyState[Escape] = pressed;
		else
			if (key == KeyEvent.VK_ENTER)
			keyState[Enter] = pressed;
	}
	
	public static final boolean isPressed(int key) {
		return keyState[key] && !pKeyState[key];
	}
	
	public static final boolean isHeld(int key) {
		return keyState[key];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		setKey(e.getKeyCode(), true);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		setKey(e.getKeyCode(), false);
	}
	
}