/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */

package sudoku.src;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create custom dialog
            JDialog dialog = new JDialog();
            dialog.setTitle("Sudoku");
            dialog.setModal(true);

            // Main panel with vertical layout
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(33, 37, 49));
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.setAlignmentY(Component.CENTER_ALIGNMENT);
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Image
            ImageIcon welcomeIcon = new ImageIcon("D:\\capstone\\plis\\FP-ASD-C-Group4\\sudoku\\src\\welcom-img-unscreen.gif");
            JLabel imageLabel = new JLabel(welcomeIcon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(imageLabel);

            // Welcome Text
            JLabel welcomeText = new JLabel("Welcome to Sudoku");
            welcomeText.setFont(new Font("Figtree", Font.BOLD, 16));
            welcomeText.setForeground(Color.WHITE);
            welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
            welcomeText.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            panel.add(welcomeText);

            JLabel enjoyText = new JLabel("Enjoy The Game!");
            enjoyText.setFont(new Font("Figtree", Font.PLAIN, 14));
            enjoyText.setForeground(Color.WHITE);
            enjoyText.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(enjoyText);

            JLabel label = new JLabel("Text with Custom Color");
            label.setForeground(new Color(0x212531));
            label.setFont(new Font("Figtree", Font.BOLD, 10));
            panel.add(label);

            // OK Button
            JButton okButton = new JButton("Start Game");
            okButton.setFont(new Font("Figtree", Font.BOLD, 14));
            okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            okButton.setForeground(new Color(255, 255, 255));
            // okButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            okButton.setFocusPainted(false);
            okButton.setBackground(new Color(33, 37, 49));
            okButton.addActionListener(_ -> {
                dialog.dispose();
                new SudokuMain();
            });
            panel.add(okButton);

            // Play background music
            BackgroundMusic backgroundMusic = new BackgroundMusic();
            backgroundMusic.playMusic("D:\\capstone\\plis\\FP-ASD-C-Group4\\sudoku\\src\\audio\\bg-musik.wav");

            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
    }
}