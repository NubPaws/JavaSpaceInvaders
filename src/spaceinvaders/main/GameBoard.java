package spaceinvaders.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.Clip;

import spaceinvaders.extenable.Scene;
import spaceinvaders.gamestate.GameStateManager;
import spaceinvaders.handlers.Keys;
import spaceinvaders.handlers.SoundHandler;

@SuppressWarnings("serial")
public class GameBoard extends Canvas implements Scene {
	
	public static final int WIDTH = 224, HEIGHT = 256, SCALE = 2;
	public static final Font MAIN_FONT;
	
	private boolean sound;
	private Dimension screenSize;
	private GameLoop loop;
	private boolean running;
	private BufferedImage buffer;
	private GameStateManager gsm;
	
	public GameBoard(boolean fullscreen, boolean sound) {
		this.sound = sound;
		init();
		if (fullscreen)
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		else screenSize = new Dimension(WIDTH *SCALE, HEIGHT *SCALE);
		setPreferredSize(screenSize);
		setFocusable(true);
		requestFocus();
		setIgnoreRepaint(true);
		setFont(MAIN_FONT);
		addKeyListener(new Keys());
	}
	
	@Override
	public void init() {
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		gsm = new GameStateManager(this);
		if (sound)
			SoundHandler.play("theme_song.aiff", Clip.LOOP_CONTINUOUSLY);
	}
	
	@Override
	public void update(int tick) {
		gsm.update(tick);
		Keys.update();
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.setFont(MAIN_FONT);
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		gsm.render(g2d);
	}
	
	@Override
	public void dispose() {
		System.exit(0);
	}
	
	// Rami's Special draw() method that works with the render() method
	public void draw() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics2D g2d = (Graphics2D)buffer.getGraphics();
		render(g2d);
		g2d.dispose();
		
		Graphics2D g2d2 = (Graphics2D)bs.getDrawGraphics();
		g2d2.drawImage(buffer, 0, 0, screenSize.width, screenSize.height, null);
		g2d2.dispose();
		bs.show();
	}
	
	public synchronized void start() {
		if (!running) {
			running = true;
			loop = new GameLoop(this);
			loop.start();
		}
	}
	
	public synchronized void stop() {
		if (running) {
			running = false;
			try {
				loop.join();
			} catch (InterruptedException iE) {
				iE.printStackTrace();
			}
		}
	}
	
	public boolean isRunning() { return running; }
	public int getFPS() { return loop.getFPS(); }
	
	static {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, GameBoard.class.getResourceAsStream("/font.ttf")));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		MAIN_FONT = new Font("Upheaval TT (BRK)", Font.PLAIN, 14);
	}
	
}