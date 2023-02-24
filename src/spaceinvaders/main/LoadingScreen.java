package spaceinvaders.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays a loading message while the game actually loads
 * in the background. All the loading does is display a
 * static window.
 */
public class LoadingScreen extends JFrame {
	
	private JLabel label;
	
	/**
	 * Generate a loading screen.
	 */
	public LoadingScreen() {
		setUndecorated(true);
		/* Make sure that closing the loading screen does not EXIT_ON_CLOSE. */
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		/* Add a basic "Loading Game..." label to a panel and add the panel to
		 * the window to display it. Set the label to the center of the screen
		 * using the BorderLayout.
		 */
		JPanel panel = new JPanel();
		
		label = new JLabel("Loading Game...");
		label.setFont(new Font("Arial", Font.BOLD, 32));
		label.setForeground(Color.white);
		
		panel.add(label, BorderLayout.CENTER);
		panel.setBackground(Color.black);
		
		add(panel, BorderLayout.CENTER);
		setSize(300, 50);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}