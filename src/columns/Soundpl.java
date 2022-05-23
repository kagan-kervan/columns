package columns;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Soundpl {
	private static final String CARD_SOUND_FILE_PATH = "cardsound.wav";
	private static final String CARD_SOUND_TRANSFERRING_FILE_PATH = "cardtransferringsound.wav";
	
	
	private static Clip cardSoundClip;
	private static Clip cardSoundClipTransfer;
	
	public static void initialize() {
		try {
			File cardSoundFile = new File(CARD_SOUND_FILE_PATH);
			cardSoundClip = AudioSystem.getClip();
			cardSoundClip.open(AudioSystem.getAudioInputStream(cardSoundFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			File cardSoundFile2 = new File(CARD_SOUND_TRANSFERRING_FILE_PATH);
			cardSoundClipTransfer = AudioSystem.getClip();
			cardSoundClipTransfer.open(AudioSystem.getAudioInputStream(cardSoundFile2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void playCardSound() {
		// Reset the frame position to zero so that the
		// audio clip can be played more than once.
		cardSoundClip.setFramePosition(0);
		
		cardSoundClip.start();
	}
	public static void playCardTransferringSound() {

		cardSoundClipTransfer.setFramePosition(0);
		
		cardSoundClipTransfer.start();
	}
}
