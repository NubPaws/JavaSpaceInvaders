package spaceinvaders.main;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	
	public GameWindow(boolean fullscreen, boolean sound, LoadingScreen loader) {
		setTitle("Space Invaders");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setUndecorated(fullscreen);
		
		GameBoard board = new GameBoard(fullscreen, sound);
		setFont(GameBoard.MAIN_FONT);
		
		add(board);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		loader.dispose();
		board.start();
	}
	
}