package spaceinvaders.handlers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class ImagesHandler {
	
	public static BufferedImage get(String res) {
		try {
			return ImageIO.read(ImagesHandler.class.getResource("/" + res));
		} catch (IOException ioE) {
			ioE.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage get(String res, int width, int height) {
		return getScaledInstance(get(res), width, height);
	}
	
	public static BufferedImage getScaledInstance(BufferedImage image, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();
		return resizedImage;
	}
	
}