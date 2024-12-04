package tictactoe.src;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.initGame() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can the static variable SoundEffect.volume to SoundEffect.Volume.MUTE
 *    to mute the sound.
 *
 * For Eclipse, place the audio file under "src", which will be copied into "bin".
 */
public enum SoundEffect {
   EAT_FOOD("tictactoe/src/audio/mixkit-chewing-something-crunchy-2244.wav"),
   EXPLODE("tictactoe/src/audio/retro-explode-1-236678.wav"),
   DIE("tictactoe/src/audio/086398_game-die-81356.wav");

   /** Nested enumeration for specifying volume */
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }

   public static Volume volume = Volume.LOW;

   /** Each sound effect has its own clip, loaded with its own sound file. */
   private Clip clip;

   /** Private Constructor to construct each element of the enum with its own sound file. */
   SoundEffect(String soundFileName) {
    try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
            getClass().getClassLoader().getResource(soundFileName)
        );
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    } catch (Exception e) {
        System.err.println("Error loading sound file: " + soundFileName);
        e.printStackTrace();
    }
    }

    public void play() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop(); // Stop if already playing
             }
             clip.setFramePosition(0); // Rewind to the beginning
             clip.start(); // Start playback
             } else {
                System.err.println("Clip is null. Sound effect cannot be played.");
            }
        }

   /** Optional static method to pre-load all the sound files. */
   static void initGame() {
      values(); // calls the constructor for all the elements
   }
}