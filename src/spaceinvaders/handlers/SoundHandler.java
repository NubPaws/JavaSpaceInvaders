package spaceinvaders.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class SoundHandler {
	
	public static void play(String res) {
		play(res, 0);
	}
	
	public static void play(String... reses) {
		Clip clip = null;
		for (int i = 0; i < reses.length; i++) {
			if (clip == null || !clip.isRunning()) {
				clip = play(reses[i], 0);
			}
		}
	}
	
	public static Clip play(String res, int loop) {
		URL soundLocation;
		Clip clip = null;
		try {
			soundLocation = SoundHandler.class.getResource("/sounds/" + res);
			
			clip = AudioSystem.getClip();
			
			AudioInputStream inputStream;
			inputStream = AudioSystem.getAudioInputStream(soundLocation);
			
			clip.open(inputStream);
			clip.loop(0);
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