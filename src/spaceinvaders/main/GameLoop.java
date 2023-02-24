package spaceinvaders.main;

/**
 * Handles the main game loop of the game. An instance of this
 * class should runs on a seperate thread, running the logic
 * update function a fixed number of times per second and
 * running the drawing functions as much as possible for the
 * highest fps possible.
 */
public class GameLoop extends Thread {
	
	/* The number of logic updates per second. The idea is
	 * that the number of updates must be fixed for the physics
	 * of the game to work properly.
	 */
	public static final double TICKS = 60;
	
	/* Must have a delegate of the spaceinvaders.main.GameBoard for them
	 * to communicate. This means that GameBoard is aware of GameLoop and
	 * GameLoop is aware of GameBoard.
	 */
	private GameBoard board;
	private int fps = 0;
	
	/**
	 * Initialize a game loop instance for a given gameboard.
	 * @param board The game board to update and render.
	 */
	public GameLoop(GameBoard board) {
		this.board = board;
	}
	
	/**
	 * The main game loop, should be ran using the start function of
	 * the Thread class in order for it to run on a seperate thread.
	 */
	@Override
	public void run() {
		/* The idea is as such. We will be running non-stop. Each
		 * iteration we want to know how much real-world time passed
		 * between the last time we entered the loop and the current time.
		 * We keep track of that difference using nanoTime (which is not
		 * as accurate as currentTimeMillis but is close enough for our
		 * usages) and the variable deltaTime. deltaTime represents the
		 * difference in time. Knowing the difference, and how much updates
		 * we want to happen per second we can run (using the other inner loop)
		 * the updates that were supposed to happen.
		 * 
		 * Keeping track of the number of updates that occured and the number of
		 * frames that occur allows us to run some basic diagnostics for the game
		 * as well as very basic profiling for the game as a whole.
		 * 
		 * Keeping track of the number of updates also allows us to run animations
		 * properly, as drawing them is handles by the render method which runs
		 * an unbounded number of time per second, which can make the animations be
		 * dependent on the frame rate rather than on the logic updates that are
		 * fixed.
		 */
		long lastTime = System.nanoTime();
		final double TIME_PER_UPDATE = Math.pow(10, 9) / TICKS;
		double dt = 0;
		int frames = 0;
		long fpsTimer = System.currentTimeMillis();
		int ticks = 0;
		long now;
		while (board.isRunning()) {
			now = System.nanoTime();
			dt += (now - lastTime) / TIME_PER_UPDATE;
			lastTime = now;
			
			while (dt >= 1) {
				ticks++;
				if (ticks == 60)
					ticks = 0;
				board.update(ticks);
				dt--;
			}
			
			board.draw();
			frames++;
			
			if (System.currentTimeMillis() - fpsTimer > 1000) {
				fpsTimer += 1000;
				fps = frames;
				frames = 0;
			}
		}
	}
	
	public int getFPS() {
		return fps;
	}
	
}