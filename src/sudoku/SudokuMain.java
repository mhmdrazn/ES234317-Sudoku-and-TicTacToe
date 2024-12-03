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

   // private variables
   GameBoardPanel board = new GameBoardPanel(timer);
   JButton btnNewGame = new JButton("New Game");

   // Constructor
   public void play() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());

      // Create a timer
      timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               timeSecond++;
               updateTimerLabel();
            }
      });

      timer.start();

      // Create the timer label with initial text
      timerView = new JLabel("00:00");
      timerView.setHorizontalAlignment(JLabel.CENTER);
      timerView.setFont(new Font("Figtree", Font.PLAIN, 16));

      // Add the timer label to the top-right corner
      JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      timerPanel.add(timerView);
      cp.add(timerPanel, BorderLayout.NORTH);
      

      board.setBackground(new Color(30, 30, 60));  // Dark blue color
      
      cp.add(board, BorderLayout.CENTER);

      btnNewGame.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            board.newGame();
         }
      });

      // Add a button to the south to re-start the game via board.newGame()
      cp.add(btnNewGame, BorderLayout.SOUTH);
      
      // Initialize the game board to start the game
      board.newGame();

      pack();     // Pack the UI components, instead of using setSize()
      setLocationRelativeTo(cp);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setTitle("Sudoku");
      setVisible(true);
   }

   // Update the timer label text
   private void updateTimerLabel() {
      // Calculate minutes and seconds
      int minutes = timeSecond / 60;
      int seconds = timeSecond % 60;

      // Format the time as MM:SS
      String formattedTime = String.format("%02d:%02d", minutes, seconds);

      // Update the timer label text
      timerView.setText(formattedTime);
   }

}