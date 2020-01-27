package spaceinvaders.extenable;

import java.awt.Graphics2D;

public interface Updatable {
	
	void update(int tick);
	void render(Graphics2D g2d);
	
}