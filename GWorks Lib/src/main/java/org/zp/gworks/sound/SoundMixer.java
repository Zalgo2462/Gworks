package org.zp.gworks.sound;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * Date: 8/29/2014
 * Time: 7:22 PM
 */
public class SoundMixer {
	public static Clip loadAudio(String url) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		Clip clip = AudioSystem.getClip();
		AudioInputStream s = AudioSystem.getAudioInputStream(SoundMixer.class.getResourceAsStream(url));
		clip.open(s);
		return clip;
	}
}
