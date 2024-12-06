/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */

package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
 
 public class Mistake extends JTextField {
     public static final int MAX_MISTAKES = 3;  // Public constant for max mistakes
     private int i = 0;  // Counter for mistakes
 
     public Mistake() {
         setText(getMistakeText());  // Initialize the text field with mistake count
         setEditable(false);  // Make the text field non-editable
         setForeground(Color.BLACK);  // Set text color to black
         setHorizontalAlignment(JTextField.CENTER);  // Center-align the text
     }
 
     // Increment the mistake count, ensuring it does not exceed the max allowed mistakes
     public void change() {
         if (i < MAX_MISTAKES) {
             i++;  // Increase mistake count if below max limit
         }
         updateText();  // Update the text field to reflect the current mistakes
     }
 
     // Reset the mistake count to zero
     public void reset() {
         i = 0;  // Reset mistake counter
         updateText();  // Update the text field
     }
 
     // Return the current number of mistakes
     public int getMistake() {
         return i;
     }
 
     // Update the text field with the current mistake count
     private void updateText() {
         setText(getMistakeText());  // Set the text to reflect the current mistake status
     }

     public void playMistakeSound() {
        try {
            // Load the sound file
            File soundFile = new File("FP-ASD-C-Group4/sudoku/src/086398_game-die-81356 copy.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void playGameOverSound() {
        try {
            // Load the game over sound file
            File soundFile = new File("FP-ASD-C-Group4/sudoku/src/mixkit-game-over-dark-orchestra-633.wav"); 
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Play the sound
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
 
     // Generate the text to display the number of mistakes
     private String getMistakeText() {
         return i + "/" + MAX_MISTAKES + " Mistakes";  // Display current mistakes out of max
     }
 }
 