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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
   private static final long serialVersionUID = 1L;
   public static final int CELL_SIZE = 60;
   public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
   public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

   private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
   private Puzzle puzzle = new Puzzle();
   private Timer timer;
   private JLabel timerLabel;
   private Game status;
   private numberList numberInput;
   private String difficultyLevel = "Easy";


   public GameBoardPanel(Timer timer) {
      super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

      // Create panel
      JPanel topRightPanel = new JPanel(new BorderLayout());
      JPanel bottomPanel = new JPanel(new BorderLayout());

      // Create timer label
      timerLabel = new JLabel("0 seconds");
      topRightPanel.add(timerLabel, BorderLayout.WEST);

      // Create number input
      numberInput = new numberList();
      bottomPanel.add(numberInput, BorderLayout.NORTH);

      // initialize the cells 
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            cells[row][col] = new Cell(row, col);
            super.add(cells[row][col]);   // JPanel
         }
      }

      CellInputListener listener = new CellInputListener();
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            if (cells[row][col].isEnabled()) {
               cells[row][col].addActionListener(listener);   // For all editable rows and cols
            }
         }
      }

      super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
      this.timer = timer;
   }

   public void newGame() {
      puzzle.newPuzzle(this.difficultyLevel);

      // Initialize all the 9x9 cells, based on the puzzle.
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
         }
      }

      numberInput.updateNumberCounts(cells);
   }

   public void resetGame(){
      for (int i = 0; i < 9; i++){
         for (int j = 0; j < 9; j++){
            if(cells[i][j].status == CellStatus.CORRECT_GUESS || cells[i][j].status == CellStatus.WRONG_GUESS){
               cells[i][j].setText("");
               cells[i][j].status = CellStatus.TO_GUESS;
               cells[i][j].paint();
               cells[i][j].setEnabled(true);
            }
            if (cells[i][j].status == CellStatus.CORRECT_GUESS ||  cells[i][j].status == CellStatus.GIVEN){
               cells[i][j].setEnabled(true);
               timer.start();
               if (cells[i][j].status == CellStatus.GIVEN){
                  cells[i][j].setEnabled(false);
               }
            }
         }
      }
   }

   public boolean isSolved() {
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
               return false;
            }
         }
      }
      return true;
   }

   private class CellInputListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         // Get a reference of the JTextField that triggers this action event
         Cell sourceCell = (Cell)e.getSource();
         
         if (sourceCell.status == CellStatus.WRONG_GUESS || sourceCell.status == CellStatus.TO_GUESS){
            int numberIn = Integer.parseInt(sourceCell.getText());
            System.out.println("You entered " + numberIn);

            if (numberIn == sourceCell.getNumber()) {
               sourceCell.status = CellStatus.CORRECT_GUESS;
            } else {
               sourceCell.status = CellStatus.WRONG_GUESS;
            }
            sourceCell.paint();   // re-paint this cell based on its status

            numberInput.updateNumberCounts(cells);

            if (isSolved()) {
               timer.stop();
               JOptionPane.showMessageDialog(null, "Congratulations! You have solved the game!");
            }
         }
      }
   }

   public void setDifficulty(String difficultyLevel) {
      this.difficultyLevel = difficultyLevel;
   }

   public String getDifficulty() {
      return difficultyLevel;
   }
}