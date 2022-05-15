package columns;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Soundpl
{
	
	static void PlaySound(File Sound) 
	{
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
			Thread.sleep(clip.getMicrosecondLength() / 1000);
		} catch (Exception e) {

		}
	}
	
}
