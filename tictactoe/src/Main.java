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
import javax.swing.*;
import sudoku.src.BackgroundMusic;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create custom dialog
            JDialog dialog = new JDialog();
            dialog.setTitle("TicTacToe");
            dialog.setModal(true);

            // Main panel with vertical layout
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
            JLabel welcomeText = new JLabel("Welcome to TicTacToe!");
            welcomeText.setFont(new Font("Figtree", Font.BOLD, 16));
            welcomeText.setForeground(Color.WHITE);
            welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
            welcomeText.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            panel.add(welcomeText);

            JLabel enjoyText = new JLabel("Select your game type");
            enjoyText.setFont(new Font("Figtree", Font.PLAIN, 14));
            enjoyText.setForeground(Color.WHITE);
            enjoyText.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(enjoyText);

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

            // TicTacToe Button
            JButton TicButton = new JButton("TicTacToe");
            TicButton.setFont(new Font("Figtree", Font.BOLD, 14));
            TicButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            TicButton.setForeground(new Color(255, 255, 255));
            TicButton.setFocusPainted(false);
            TicButton.setBackground(new Color(33, 37, 49));
            TicButton.addActionListener(_ -> {
                dialog.dispose();
                
                // Create mode selection dialog
                JDialog modeDialog = new JDialog();
                modeDialog.setTitle("Select Game Mode");
                modeDialog.setModal(true);
                
                // Main panel with vertical layout
                JPanel modePanel = new JPanel();
                modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.Y_AXIS));
                modePanel.setBackground(new Color(33, 37, 49));
                modePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                // Title
                JLabel titleLabel = new JLabel("Choose Your Game Mode");
                titleLabel.setFont(new Font("Figtree", Font.BOLD, 18));
                titleLabel.setForeground(Color.WHITE);
                titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                modePanel.add(titleLabel);
                
                // Container
                JPanel VSPlayer = new JPanel(new BorderLayout());
                VSPlayer.setBackground(new Color(33, 37, 49));
                VSPlayer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                JPanel VSCom = new JPanel(new BorderLayout());
                VSCom.setBackground(new Color(33, 37, 49));
                VSCom.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                // VS Player Button
                JButton playerButton = new JButton("Player vs Player");
                playerButton.setFont(new Font("Figtree", Font.BOLD, 14));
                playerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                playerButton.setMaximumSize(new Dimension(200, 40));
                playerButton.setForeground(Color.WHITE);
                playerButton.setBackground(new Color(64, 154, 225));
                playerButton.setFocusPainted(false);
                
                // VS Player Description
                JLabel playerDesc = new JLabel("<html><center>Challenge your friend in a classic<br>head-to-head match!</center></html>");
                playerDesc.setFont(new Font("Figtree", Font.PLAIN, 12));
                playerDesc.setForeground(Color.WHITE);
                playerDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

                VSPlayer.add(playerButton, BorderLayout.NORTH);
                modePanel.add(Box.createRigidArea(new Dimension(0, 10)));
                VSPlayer.add(playerDesc, BorderLayout.SOUTH);
                
                // VS Computer Button
                JButton computerButton = new JButton("Player vs Computer");
                computerButton.setFont(new Font("Figtree", Font.BOLD, 14));
                computerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                computerButton.setMaximumSize(new Dimension(200, 40));
                computerButton.setForeground(Color.WHITE);
                computerButton.setBackground(new Color(239, 105, 80));
                computerButton.setFocusPainted(false);
                
                // VS Computer Description
                JLabel computerDesc = new JLabel("<html><center>Test your skills against our<br>advanced AI opponent!</center></html>");
                computerDesc.setFont(new Font("Figtree", Font.PLAIN, 12));
                computerDesc.setForeground(Color.WHITE);
                computerDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                VSCom.add(computerButton, BorderLayout.NORTH);
                modePanel.add(Box.createRigidArea(new Dimension(0, 10)));
                VSCom.add(computerDesc, BorderLayout.SOUTH);
                
                // Add components with spacing
                modePanel.add(VSPlayer, BorderLayout.CENTER);
                modePanel.add(Box.createRigidArea(new Dimension(0, 5)));
                modePanel.add(VSCom, BorderLayout.CENTER);
                modePanel.add(Box.createRigidArea(new Dimension(0, 10)));
                modePanel.add(exitButton, BorderLayout.SOUTH);
                
                // Button actions
                playerButton.addActionListener(e -> {
                    modeDialog.dispose();
                    GameMain.main(new String[]{"player"});  // Pass mode as argument
                });
                
                computerButton.addActionListener(e -> {
                    modeDialog.dispose();
                    GameMain.main(new String[]{"computer"});  // Pass mode as argument
                });
                
                modeDialog.add(modePanel);
                modeDialog.pack();
                modeDialog.setLocationRelativeTo(null);
                modeDialog.setVisible(true);
            });
            container.add(TicButton, BorderLayout.WEST);
            
            // TicTacToe Button
            JButton OthelloButton = new JButton("Othello");
            OthelloButton.setFont(new Font("Figtree", Font.BOLD, 14));
            OthelloButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            OthelloButton.setForeground(new Color(255, 255, 255));
            OthelloButton.setFocusPainted(false);
            OthelloButton.setBackground(new Color(33, 37, 49));
            OthelloButton.addActionListener(_ -> {
                dialog.dispose();
                GameMain.main(new String[0]);
            });
            container.add(OthelloButton, BorderLayout.EAST);

            panel.add(container);

            // Play background music
            BackgroundMusic backgroundMusic = new BackgroundMusic();
            backgroundMusic.playMusic("tictactoe\\src\\audio\\bg-musik.wav");

            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
    }
}