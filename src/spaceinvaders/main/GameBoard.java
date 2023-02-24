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

/**
 * This class handles the managing of the window of the game.
 * The job of the spaceinvaders.main.GameWindow is to run this
 * java.awt.Canvas and display only it.
 */
public class GameBoard extends Canvas implements Scene {
	
	/* The dimensions of the game, with the scale. */
	public static final int WIDTH = 224, HEIGHT = 256, SCALE = 2;
	/* The main font of the game, initizlied in the static initialize block */
	public static final Font MAIN_FONT;
	
	private boolean sound;
	private Dimension screenSize;
	private GameLoop loop;
	private boolean running;
	private BufferedImage buffer;
	private GameStateManager gsm;
	
	/**
	 * Creates the GameBoard object which is in charge of displaying the
	 * game, and managing it regarding input and logic updates.
	 * 
	 * @param fullscreen Whether the game should run in fullscreen mod.
	 * @param sound Whether the game should run with sounds.
	 */
	public GameBoard(boolean fullscreen, boolean sound) {
		this.sound = sound;
		init();
		
		/* If the game should run in fullscreen, make sure to set the dimensions
		 * of the canvas to the proper size. Otherwise display in the proper
		 * dimensions specified above.
		 * Toolkit.getDefaultToolkit().getScreenSize() gives us a Dimension
		 * object storing the width and height of the main display of the
		 * machine we are on.
		 */
		if (fullscreen)
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		else screenSize = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		
		// Set the size to be, if possible, the screenSize specified above.
		setPreferredSize(screenSize);
		// Request the focus of the application to properly handle the input.
		setFocusable(true);
		requestFocus();
		// Must ignore repaint so that our drawing function can work properly.
		setIgnoreRepaint(true);
		setFont(MAIN_FONT);
		addKeyListener(new Keys());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		gsm = new GameStateManager(this);
		if (sound)
			SoundHandler.play("theme_song.aiff", Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(int tick) {
		gsm.update(tick);
		Keys.update();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics2D g2d) {
		g2d.setFont(MAIN_FONT);
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		gsm.render(g2d);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		System.exit(0);
	}
	
	/**
	 * <p>
	 * Handles preparing the buffer to draw on the the next buffer
	 * to draw on. The function uses double buffer drawing strategy.</p>
	 * 
	 * <b>setIgnoreRepaint(true) must be set in order for this
	 * function to work properly on all devices and for the screen
	 * to not flicker.</b>
	 */
	public void draw() {
		/* Get the buffer strategy, if non exists create a buffer strategy
		 * of two buffers. That way we can draw the second buffer
		 * while displaying the first one. This will give a more smooth
		 * feeling to the game.
		 */
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		/* Get the graphics object of our buffered image so that
		 * we can draw on it. Draw the next frame on it and dispose
		 * of the graphics object. After that we can display the image we
		 * just drew to the screen using the buffer strategy.
		 */
		Graphics2D g2d;
		
		g2d = (Graphics2D)buffer.getGraphics();
		render(g2d);
		g2d.dispose();
		
		g2d = (Graphics2D)bs.getDrawGraphics();
		g2d.drawImage(buffer, 0, 0, screenSize.width, screenSize.height, null);
		g2d.dispose();
		bs.show();
	}
	
	/**
	 * Starts running the main game loop of the game.
	 */
	public synchronized void start() {
		if (!running) {
			running = true;
			loop = new GameLoop(this);
			loop.start();
		}
	}
	
	/**
	 * Stops runnings the main game loop.
	 */
	public synchronized void stop() {
		if (running) {
			running = false;
			try {
				loop.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return true if the running flag in the game, the one used by the
	 * main game loop, is set, false otherwise.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * @return The number of frames per second as counted in the last second
	 * of the game.
	 */
	public int getFPS() { return loop.getFPS(); }
	
	/**
	 * In order to create the MAIN_FONT for the game, to make sure it is available before
	 * we even enter the main, we must initialize it using the static initalizer block.
	 * We must do this as we want to inditate the MAIN_FONT of the game is something
	 * that cannot be changed - marking it as final.
	 * 
	 * To add a custom font to the JVM we must first register it as a font, then we can use it
	 * using it's name like any other font.
	 */
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