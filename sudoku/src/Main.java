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

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        // Menampilkan pesan selamat datang dengan ikon gambar dan judul
        ImageIcon icon = new ImageIcon("FP-ASD-C-Group4/sudoku/src/welcom-img.gif"); // Ganti dengan path gambar Anda
        String message = "<html><h2>Selamat datang di permainan Sudoku!</h2><h1>Enjoy The Game!!!</h1></html>";
        BackgroundMusic backgroundMusic = new BackgroundMusic();
        backgroundMusic.playMusic("FP-ASD-C-Group4/sudoku/src/bg-musik.wav"); // Ganti dengan path file musik Anda

        JOptionPane.showMessageDialog(null, message, "Selamat Datang", JOptionPane.INFORMATION_MESSAGE, icon);

   
      

        new SudokuMain();
    }
}