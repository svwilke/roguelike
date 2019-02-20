package apace.utils;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	private Clip clip;
	
	public Sound(String fileName) {
		try(AudioInputStream in = AudioSystem.getAudioInputStream(ClassLoader.getSystemClassLoader().getResource("res/sfx/" + fileName))){
			clip = AudioSystem.getClip();
			clip.open(in);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			System.out.println("#### ERROR ####");
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		if(clip == null) {
			System.err.println("Error: clip that was tried to be played is null.");
			return;
		}
		/*if(clip.isRunning()) {
			clip.stop();
		}*/
		clip.setFramePosition(0);
		clip.start();
	}
}
