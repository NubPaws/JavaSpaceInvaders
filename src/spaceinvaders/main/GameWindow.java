package spaceinvaders.main;

import javax.swing.JFrame;

/**
 * Handles the window and displays the main canvas of
 * the game that handles rendering and updating.
 */
public class GameWindow extends JFrame {
	
	/**
	 * Generates a new window for the game. This class will usually
	 * be called only once when constructing a game, but can be called
	 * also multiple times for a multiple window game (if such exists).
	 * 
	 * @param fullscreen Whether the game should run in full screen mode.
	 * @param sound Whether the game should run with sounds.
	 * @param loader The loading screen that is being displayed.
	 */
	public GameWindow(boolean fullscreen, boolean sound, LoadingScreen loader) {
		setTitle("Space Invaders");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		/* A fullscreen application is an application that takes the entire
		 * screen but has no decorations (like the X button).
		 */
		setUndecorated(fullscreen);
		
		/* Create the GameBoard and set the main font of the game. */
		GameBoard board = new GameBoard(fullscreen, sound);
		setFont(GameBoard.MAIN_FONT);
		
		/* Add the board and make sure everything fits and is in the
		 * center of the screen.
		 */
		add(board);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		/* Dispose of the loader. */
		if (loader != null)
			loader.dispose();
		/* Start the game. */
		board.start();
	}
	
}