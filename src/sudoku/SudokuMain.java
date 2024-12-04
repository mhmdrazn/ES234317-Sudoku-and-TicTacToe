/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */

package src.sudoku;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;

   private Timer timer;
   private JLabel timerView;
   private int timeSecond = 0;
   private GameBoardPanel board;
   private JButton btnNewGame;
   private numberList numberPanel = new numberList();

   // Constructor
   public SudokuMain() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.setBackground(new Color(38, 49, 81)); // Dark blue background

      // Create the game board
      board = new GameBoardPanel(timer);
      board.setBackground(new Color(38, 49, 81)); // Dark blue color for the board
      cp.add(board, BorderLayout.CENTER);

      // Create and set up the timer
      timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               timeSecond++;
               updateTimerLabel();
            }
      });
      timer.start();

      timerView = new JLabel("Timer: 00:00");
      timerView.setHorizontalAlignment(JLabel.RIGHT);
      timerView.setFont(new Font("Figtree", Font.PLAIN, 14));
      timerView.setForeground(Color.WHITE);

      JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      timerPanel.setBackground(new Color(38, 49, 81));
      timerPanel.add(timerView);
      cp.add(timerPanel, BorderLayout.NORTH);

      // Create the number panel and New Game button container
      JPanel bottomPanel = new JPanel(new BorderLayout());

      // Add the number panel
      bottomPanel.add(numberPanel, BorderLayout.SOUTH);

      // Create and add New Game button
      btnNewGame = new JButton("New Game");
      btnNewGame.setBackground(new Color(38, 49, 81));
      btnNewGame.setForeground(Color.WHITE);
      btnNewGame.setFocusPainted(false);
      btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               board.newGame();
               timeSecond = 0; // Reset the timer
               updateTimerLabel();
            }
      });
      bottomPanel.add(btnNewGame, BorderLayout.CENTER);

      // Add the bottom panel containing number panel and New Game button
      cp.add(bottomPanel, BorderLayout.SOUTH);

      // Initialize the game board to start the game
      board.newGame();

      pack(); // Pack the UI components, instead of using setSize()
      setLocationRelativeTo(null); // Center the window
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle window-closing
      setTitle("Sudoku");
      setVisible(true);
   }

   // Update the timer label text in MM:SS format
   private void updateTimerLabel() {
      int minutes = timeSecond / 60;
      int seconds = timeSecond % 60;
      String formattedTime = String.format("%02d:%02d", minutes, seconds);
      timerView.setText("Timer: " + formattedTime);
   }

   public static void main(String[] args) {
      new SudokuMain(); // Start the application
   }
}