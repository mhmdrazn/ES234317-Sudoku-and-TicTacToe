/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */

package othello.src;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Popup Window
            JDialog dialog = new JDialog();
            dialog.setTitle("Othello");
            dialog.setModal(true);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(33, 37, 49));
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.setAlignmentY(Component.CENTER_ALIGNMENT);
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Container for button
            JPanel container = new JPanel(new BorderLayout());
            container.setBackground(new Color(33, 37, 49));
            container.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            // Image
            ImageIcon welcomeIcon = new ImageIcon("sudoku\\src\\welcom-img-unscreen.gif");
            JLabel imageLabel = new JLabel(welcomeIcon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(imageLabel);

            // Welcome Text
            JLabel welcomeText = new JLabel("Welcome to Othello!");
            welcomeText.setFont(new Font("Figtree", Font.BOLD, 16));
            welcomeText.setForeground(Color.WHITE);
            welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
            welcomeText.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            panel.add(welcomeText);

            JLabel label = new JLabel("Text with Custom Color");
            label.setForeground(new Color(0x212531));
            label.setFont(new Font("Figtree", Font.BOLD, 10));
            panel.add(label);

            // Exit button
            JButton exitButton = new JButton("Exit");
            exitButton.setFont(new Font("Figtree", Font.BOLD, 12));
            exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            exitButton.setMaximumSize(new Dimension(100, 35));
            exitButton.setForeground(Color.WHITE);
            exitButton.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
            exitButton.setFocusPainted(true);
            exitButton.setBackground(new Color(33, 37, 49));
            exitButton.addActionListener(_ -> {
                System.exit(0);
            });
            
            // Othello Button
            JButton OthelloButton = new JButton("Start Game");
            OthelloButton.setFont(new Font("Figtree", Font.BOLD, 14));
            OthelloButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            OthelloButton.setForeground(new Color(255, 255, 255));
            OthelloButton.setFocusPainted(false);
            OthelloButton.setBackground(new Color(33, 37, 49));
            OthelloButton.addActionListener(_ -> {
                dialog.dispose();
                TTTGraphicsOthello.main(new String[0]);
            });
            container.add(OthelloButton, BorderLayout.CENTER);

            panel.add(container);

            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
    }
}