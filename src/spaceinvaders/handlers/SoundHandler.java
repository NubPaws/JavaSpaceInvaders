package spaceinvaders.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Handles playing sounds in the game.
 */
public final class SoundHandler {
	
	/**
	 * Plays a given sound from a file
	 * 
	 * @param res the file to play, where the location starts from the
	 * resources folder.
	 */
	public static void play(String res) {
		play(res, 0);
	}
	
	/**
	 * Play a resource from the resources folder, and choose if
	 * to loop and how many times.
	 * 
	 * @param res the file to play, where the location starts from the
	 * resources folder.
	 * @param loop 0 to not loop at all, >0 for the amount of times to loop.
	 * Pass Clip.LOOP_CONTINUOUSLY to loop non stop.
	 * @return The clip instance to be stopped if needed.
	 */
	public static Clip play(String res, int loop) {
		URL soundLocation;
		Clip clip = null;
		try {
			soundLocation = SoundHandler.class.getResource("/sounds/" + res);
			
			clip = AudioSystem.getClip();
			
			AudioInputStream inputStream;
			inputStream = AudioSystem.getAudioInputStream(soundLocation);
			
			clip.open(inputStream);
			clip.loop(loop);
			clip.start();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		return clip;
	}
	
}