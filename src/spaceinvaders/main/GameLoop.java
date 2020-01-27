package spaceinvaders.main;

public class GameLoop extends Thread {
	
	public static final double TICKS = 60;
	
	private GameBoard board;
	private int fps = 0;
	
	public GameLoop(GameBoard board) {
		this.board = board;
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double TIME_PER_UPDATE = Math.pow(10, 9) /TICKS;
		double dt = 0;
		int frames = 0;
		long fpsTimer = System.currentTimeMillis();
		int ticks = 0;
		long now;
		while (board.isRunning()) {
			now = System.nanoTime();
			dt += (now -lastTime) /TIME_PER_UPDATE;
			lastTime = now;
			
			while (dt >= 1) {
				ticks++;
				if (ticks == 60) ticks = 0;
				board.update(ticks);
				dt--;
			}
			
			board.draw();
			frames++;
			
			if (System.currentTimeMillis() -fpsTimer > 1000) {
				fpsTimer += 1000;
				fps = frames;
				frames = 0;
			}
		}
	}
	
	public int getFPS() { return fps; }
	
}