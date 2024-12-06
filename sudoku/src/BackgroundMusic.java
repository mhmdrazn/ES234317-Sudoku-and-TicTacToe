<<<<<<< HEAD
package src;
=======
/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */


package sudoku.src;
>>>>>>> ef6db2b828bef0865c59e7bd2b820a3b444681b1

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BackgroundMusic {
    private Clip clip;
    private long clipTimePosition;

    public void playMusic(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Memutar musik secara terus-menerus
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clipTimePosition = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    public void resumeMusic() {
        if (clip != null) {
            clip.setMicrosecondPosition(clipTimePosition);
            clip.start();
        }
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
        }
    }
}