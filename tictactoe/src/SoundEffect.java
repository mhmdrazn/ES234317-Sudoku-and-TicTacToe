/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */
package tictactoe.src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public enum SoundEffect {
    EAT_FOOD("tictactoe/src/audio/eatfood.wav"),
    EXPLODE("tictactoe/src/audio/explode.wav"),
    DIE("tictactoe/src/audio/die.wav"),
    BACKGROUND_MUSIC("tictactoe/src/audio/bgmusik.wav");

    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    private Clip clip;
    private static Clip backgroundMusicClip;

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

    public static void playBackgroundMusic() {
        try {
            if (backgroundMusicClip == null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                SoundEffect.class.getClassLoader().getResource("tictactoe/src/audio/bgmusik.wav")
                );
                backgroundMusicClip = AudioSystem.getClip();
                backgroundMusicClip.open(audioInputStream);
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            System.err.println("Error loading background music.");
            e.printStackTrace();
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    }

    static void initGame() {
        values();
    }
}
