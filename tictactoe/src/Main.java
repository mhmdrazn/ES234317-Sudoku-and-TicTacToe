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
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Popup Window
            JDialog dialog = new JDialog();
            dialog.setTitle("TicTacToe");
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
            JLabel welcomeText = new JLabel("Welcome to TicTacToe!");
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

            // TicTacToe Button
            JButton TicButton = new JButton("Start Game");
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
                playerButton.addActionListener(_ -> {
                    modeDialog.dispose();
                    GameMain.main(new String[]{"player"});
                });
                
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
                computerButton.addActionListener(_ -> {
                    modeDialog.dispose();
                });
                
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
                playerButton.addActionListener(_  -> {
                    modeDialog.dispose();
                    GameMain.main(new String[]{"player"});  // Pass mode as argument
                });
                
                computerButton.addActionListener(_  -> {

                    // Create difficulty selection dialog
                    JDialog difficultyDialog = new JDialog();
                    difficultyDialog.setTitle("Select Difficulty Level");
                    difficultyDialog.setModal(true);

                    // Main panel with vertical layout
                    JPanel difficultyPanel = new JPanel();
                    difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
                    difficultyPanel.setBackground(new Color(33, 37, 49));
                    difficultyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                    // Title
                    JLabel titleLabels = new JLabel("Choose Difficulty Level");
                    titleLabels.setFont(new Font("Figtree", Font.BOLD, 18));
                    titleLabels.setForeground(Color.WHITE);
                    titleLabels.setAlignmentX(Component.CENTER_ALIGNMENT);
                    difficultyPanel.add(titleLabels);
                    difficultyPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                    // Easy Mode Container
                    JPanel easyContainer = new JPanel(new BorderLayout());
                    easyContainer.setBackground(new Color(33, 37, 49));
                    easyContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    JButton easyButton = new JButton("Easy - Random Move");
                    easyButton.setFont(new Font("Figtree", Font.BOLD, 14));
                    easyButton.setMaximumSize(new Dimension(200, 40));
                    easyButton.setForeground(Color.WHITE);
                    easyButton.setBackground(new Color(92, 184, 92)); // Green color
                    easyButton.setFocusPainted(false);
                    easyButton.addActionListener(_  -> {
                        difficultyDialog.dispose();
                        GameMain.main(new String[]{"aiGame", "easy"});
                    });

                    JLabel easyDesc = new JLabel("<html><center>Computer makes random moves.<br>Perfect for beginners!</center></html>");
                    easyDesc.setFont(new Font("Figtree", Font.PLAIN, 12));
                    easyDesc.setForeground(Color.WHITE);
                    easyDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

                    easyContainer.add(easyButton, BorderLayout.NORTH);
                    easyContainer.add(easyDesc, BorderLayout.SOUTH);

                    // Medium Mode Container
                    JPanel mediumContainer = new JPanel(new BorderLayout());
                    mediumContainer.setBackground(new Color(33, 37, 49));
                    mediumContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    JButton mediumButton = new JButton("Medium - Rule Based");
                    mediumButton.setFont(new Font("Figtree", Font.BOLD, 14));
                    mediumButton.setMaximumSize(new Dimension(200, 40));
                    mediumButton.setForeground(Color.WHITE);
                    mediumButton.setBackground(new Color(240, 173, 78)); // Orange color
                    mediumButton.setFocusPainted(false);
                    mediumButton.addActionListener(_  -> {
                        difficultyDialog.dispose();
                        GameMain.main(new String[]{"aiGame", "medium"});
                    });

                    JLabel mediumDesc = new JLabel("<html><center>Computer uses basic strategy rules.<br>For players with some experience.</center></html>");
                    mediumDesc.setFont(new Font("Figtree", Font.PLAIN, 12));
                    mediumDesc.setForeground(Color.WHITE);
                    mediumDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

                    mediumContainer.add(mediumButton, BorderLayout.NORTH);
                    mediumContainer.add(mediumDesc, BorderLayout.SOUTH);

                    // Hard Mode Container
                    JPanel hardContainer = new JPanel(new BorderLayout());
                    hardContainer.setBackground(new Color(33, 37, 49));
                    hardContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    JButton hardButton = new JButton("Hard - Minimax");
                    hardButton.setFont(new Font("Figtree", Font.BOLD, 14));
                    hardButton.setMaximumSize(new Dimension(200, 40));
                    hardButton.setForeground(Color.WHITE);
                    hardButton.setBackground(new Color(217, 83, 79)); // Red color
                    hardButton.setFocusPainted(false);
                    hardButton.addActionListener(_ -> {
                        difficultyDialog.dispose();
                        GameMain.main(new String[]{"aiGame", "hard"});
                    });

                    JLabel hardDesc = new JLabel("<html><center>Computer uses advanced AI algorithm.<br>Challenge yourself against the best!</center></html>");
                    hardDesc.setFont(new Font("Figtree", Font.PLAIN, 12));
                    hardDesc.setForeground(Color.WHITE);
                    hardDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

                    hardContainer.add(hardButton, BorderLayout.NORTH);
                    hardContainer.add(hardDesc, BorderLayout.SOUTH);

                    // Add components to panel
                    difficultyPanel.add(easyContainer);
                    difficultyPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                    difficultyPanel.add(mediumContainer);
                    difficultyPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                    difficultyPanel.add(hardContainer);

                    // Add back button
                    JButton backButton = new JButton("Back");
                    backButton.setFont(new Font("Figtree", Font.BOLD, 12));
                    backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    backButton.setMaximumSize(new Dimension(100, 35));
                    backButton.setForeground(Color.WHITE);
                    backButton.setBackground(new Color(33, 37, 49));
                    backButton.setFocusPainted(false);
                    backButton.addActionListener(_  -> {
                        difficultyDialog.dispose();
                        modeDialog.setVisible(true);
                    });

                    difficultyPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                    difficultyPanel.add(backButton);

                    // Show dialog
                    difficultyDialog.add(difficultyPanel);
                    difficultyDialog.pack();
                    difficultyDialog.setLocationRelativeTo(null);
                    difficultyDialog.setVisible(true);
                });
                
                modeDialog.add(modePanel);
                modeDialog.pack();
                modeDialog.setLocationRelativeTo(null);
                modeDialog.setVisible(true);
            });
            container.add(TicButton, BorderLayout.CENTER);

            panel.add(container);

            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
    }
}