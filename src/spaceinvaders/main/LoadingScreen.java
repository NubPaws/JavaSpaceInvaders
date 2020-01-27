package spaceinvaders.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoadingScreen extends JFrame {
	
	private JLabel label;
	
	public LoadingScreen() {
		setUndecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
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