package spaceinvaders.handlers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Handles loading the images and sclaing them in the game.
 */
public final class ImagesHandler {
	
	/**
	 * Load an image from the resource folder.
	 * 
	 * @param res the resources requested as if the res folder is the current directory.
	 * @return a BufferedImage containing the loaded image or null if loading failed.
	 */
	public static final BufferedImage get(String res) {
		try {
			return ImageIO.read(ImagesHandler.class.getResource("/" + res));
		} catch (IOException ioE) {
			ioE.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Load an image from the resource folder and scales it.
	 * 
	 * @param res the resources requested as if the res folder is the current directory.
	 * @return a scaled BufferedImage containing the loaded image or null if loading failed.
	 */
	public static final BufferedImage get(String res, int width, int height) {
		return getScaledInstance(get(res), width, height);
	}
	
	/**
	 * Given a BufferedImage, scales that image and returns the new scaled instance of
	 * that image to the caller.
	 * 
	 * @param image the BufferedImage to scale.
	 * @param width the new width of the image.
	 * @param height the new height of the image.
	 * @return
	 */
	public static final BufferedImage getScaledInstance(BufferedImage image, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();
		return resizedImage;
	}
	
}