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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTTGraphicsOthello extends JFrame {
   private static final long serialVersionUID = 1L;

   public static final int ROWS = 8;
   public static final int COLS = 8;

   public static final int CELL_SIZE = 60;
   public static final int BOARD_WIDTH  = CELL_SIZE * COLS;
   public static final int BOARD_HEIGHT = CELL_SIZE * ROWS;
   public static final int GRID_WIDTH = 3;
   public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
   public static final int CELL_PADDING = CELL_SIZE / 7;
   public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
   public static final int SYMBOL_STROKE_WIDTH = 8; 
   public static final Color COLOR_BG = new Color(34, 139, 34); 
   public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
   public static final Color COLOR_GRID = Color.BLACK; 
   public static final Color COLOR_BLACK = Color.BLACK;
   public static final Color COLOR_WHITE = Color.WHITE;
   public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

   public enum State {
      PLAYING, BLACK_WON, WHITE_WON
   }
   private State currentState;

   public enum Seed {
      BLACK, WHITE, NO_SEED
   }
   private Seed currentPlayer;
   private Seed[][] board;

   private GamePanel gamePanel;
   private JLabel statusBar;

   public TTTGraphicsOthello() {
      initGame();

      gamePanel = new GamePanel();
      gamePanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

      gamePanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            
            int row = mouseY / CELL_SIZE;
            int col = mouseX / CELL_SIZE;

            if (currentState == State.PLAYING) {
               if (row >= 0 && row < ROWS && col >= 0
                     && col < COLS && board[row][col] == Seed.NO_SEED) {
                  // Make the move and flip opponent's discs
                  updateGame(currentPlayer, row, col);
                  // Switch player
                  currentPlayer = (currentPlayer == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
               }
            } else {
               newGame();
            }
            repaint();
         }
      });

      // Setup the status bar (JLabel) to display status message
      statusBar = new JLabel("       ");
      statusBar.setFont(FONT_STATUS);
      statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
      statusBar.setOpaque(true);
      statusBar.setBackground(COLOR_BG_STATUS);

      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(gamePanel, BorderLayout.CENTER);
      cp.add(statusBar, BorderLayout.PAGE_END);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
      setTitle("Othello");
      setLocationRelativeTo(null);
      setVisible(true);

      newGame();
   }

   public void initGame() {
      board = new Seed[ROWS][COLS];
   }

   public void newGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = Seed.NO_SEED;
         }
      }
      currentPlayer = Seed.BLACK;
      currentState = State.PLAYING;
   }

   public void updateGame(Seed mySeed, int rowSelected, int colSelected) {
      board[rowSelected][colSelected] = mySeed;  // place the player's disc

      // Flip along the row (both directions)
      flipLine(mySeed, rowSelected, colSelected, 0, 1);  // Right
      flipLine(mySeed, rowSelected, colSelected, 0, -1); // Left

      // Flip along the column (both directions)
      flipLine(mySeed, rowSelected, colSelected, 1, 0);  // Down
      flipLine(mySeed, rowSelected, colSelected, -1, 0); // Up

      // Flip along the diagonal (both directions)
      flipLine(mySeed, rowSelected, colSelected, 1, 1);  // Down-Right
      flipLine(mySeed, rowSelected, colSelected, -1, -1); // Up-Left

      // Flip along the opposite diagonal (both directions)
      flipLine(mySeed, rowSelected, colSelected, 1, -1);  // Down-Left
      flipLine(mySeed, rowSelected, colSelected, -1, 1);  // Up-Right

      if (isBoardFull()) {
         currentState = countDiscs();
      }
   }

   private void flipLine(Seed mySeed, int row, int col, int rowDelta, int colDelta) {
      Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
      int curRow = row + rowDelta;
      int curCol = col + colDelta;

      while (curRow >= 0 && curRow < ROWS && curCol >= 0 && curCol < COLS && board[curRow][curCol] == opponentSeed) {
         curRow += rowDelta;
         curCol += colDelta;
      }

      if (curRow >= 0 && curRow < ROWS && curCol >= 0 && curCol < COLS && board[curRow][curCol] == mySeed) {
         curRow -= rowDelta;
         curCol -= colDelta;
         while (curRow != row || curCol != col) {
            board[curRow][curCol] = mySeed;
            curRow -= rowDelta;
            curCol -= colDelta;
         }
      }
   }

   private boolean isBoardFull() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (board[row][col] == Seed.NO_SEED) {
               return false;
            }
         }
      }
      return true;
   }

   private State countDiscs() {
      int blackCount = 0;
      int whiteCount = 0;
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (board[row][col] == Seed.BLACK) {
               blackCount++;
            } else if (board[row][col] == Seed.WHITE) {
               whiteCount++;
            }
         }
      }
      if (blackCount > whiteCount) {
         return State.BLACK_WON;
      } else {
         return State.WHITE_WON;
      }
   }

   class GamePanel extends JPanel {
      private static final long serialVersionUID = 1L;

      @Override
      public void paintComponent(Graphics g) {  // Callback via repaint()
         super.paintComponent(g);
         setBackground(COLOR_BG);

         // Draw grid-lines
         g.setColor(COLOR_GRID);
         for (int row = 1; row < ROWS; ++row) {
            g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF,
                  BOARD_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
         }
         for (int col = 1; col < COLS; ++col) {
            g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH_HALF, 0,
                  GRID_WIDTH, BOARD_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
         }

         // Draw all the seeds (discs) using Graphics2D for better anti-aliasing
         Graphics2D g2d = (Graphics2D) g;
         g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); // disc outline
         for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
               int x1 = col * CELL_SIZE + CELL_PADDING;
               int y1 = row * CELL_SIZE + CELL_PADDING;
               if (board[row][col] == Seed.BLACK) {
                  g2d.setColor(COLOR_BLACK);
                  g2d.fillOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
               } else if (board[row][col] == Seed.WHITE) {
                  g2d.setColor(COLOR_WHITE);
                  g2d.fillOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE); // no outline for white discs
               }
            }
         }

         // Print status-bar message
         switch (currentState) {
            case PLAYING:
               statusBar.setForeground(Color.BLACK);
               if (currentPlayer == Seed.BLACK) {
                  statusBar.setText("Black's Turn");
               } else {
                  statusBar.setText("White's Turn");
               }
               break;
            case BLACK_WON:
               statusBar.setForeground(COLOR_BLACK);
               statusBar.setText("Black Won! Click to play again.");
               break;
            case WHITE_WON:
               statusBar.setForeground(COLOR_WHITE);
               statusBar.setText("White Won! Click to play again.");
               break;
         }
      }
   }

   public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new TTTGraphicsOthello();
         }
      });
   }
}
