/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 *   */
package tictactoe.src;

// import java.io.File;
// import java.io.IOException;
// import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
// import javax.sound.sampled.LineUnavailableException;
// import javax.sound.sampled.UnsupportedAudioFileException;

public enum SoundEffect {
    EAT_FOOD("tictactoe/src/audio/eatfood.wav"),
    EXPLODE("tictactoe/src/audio/explode.wav"),
    DIE("tictactoe/src/audio/die.wav"),
    BACKGROUND_MUSIC("tictactoe/src/audio/bgmusik.wav"); // Tambahkan background music

    /**
     * Nested enumeration for specifying volume
     */
    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    /**
     * Each sound effect has its own clip, loaded with its own sound file.
     */
    private Clip clip;
    private static Clip backgroundMusicClip; // Untuk background music

    /**
     * Private Constructor to construct each element of the enum with its own
     * sound file.
     */
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

    // Static method to play background music
    public static void playBackgroundMusic() {
        try {
            if (backgroundMusicClip == null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                SoundEffect.class.getClassLoader().getResource("tictactoe/src/audio/bgmusik.wav")
                );
                backgroundMusicClip = AudioSystem.getClip();
                backgroundMusicClip.open(audioInputStream);
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the background music
            }
        } catch (Exception e) {
            System.err.println("Error loading background music.");
            e.printStackTrace();
        }
    }

    // Static method to stop background music
    public static void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop(); // Stop the background music
        }
    }

    /**
     * Optional static method to pre-load all the sound files.
     */
    static void initGame() {
        values(); // calls the constructor for all the elements
    }
}
