/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */

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
    EAT_FOOD("tictactoe/src/audio/mixkit-chewing-something-crunchy-2244.wav"),
    EXPLODE("tictactoe/src/audio/retro-explode-1-236678.wav"),
    DIE("tictactoe/src/audio/086398_game-die-81356.wav");

    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    private Clip clip;

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
                clip.stop();
            }
            clip.setFramePosition(0);
                clip.start();
            } else {
                System.err.println("Clip is null. Sound effect cannot be played.");
            }
        }

    static void initGame() {
        values();
    }
}