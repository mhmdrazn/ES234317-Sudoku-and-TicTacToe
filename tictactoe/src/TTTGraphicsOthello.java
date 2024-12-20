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

/**
 * Othello (Reversi): Two-player Graphics version (Black and White discs with flipping feature)
 */
public class TTTGraphicsOthello extends JFrame {
   private static final long serialVersionUID = 1L; // to prevent serializable warning

   // Define named constants for the game board
   public static final int ROWS = 8;  // ROWS x COLS cells
   public static final int COLS = 8;

   // Define named constants for the drawing graphics
   public static final int CELL_SIZE = 60; // cell width/height (square)
   public static final int BOARD_WIDTH  = CELL_SIZE * COLS; // the drawing canvas
   public static final int BOARD_HEIGHT = CELL_SIZE * ROWS;
   public static final int GRID_WIDTH = 3;                  // Grid-line's width
   public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
   public static final int CELL_PADDING = CELL_SIZE / 7;
   public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // disc size
   public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width
   public static final Color COLOR_BG = new Color(34, 139, 34);  // background is now green
   public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
   public static final Color COLOR_GRID = Color.BLACK;  // Grid line
   public static final Color COLOR_BLACK = Color.BLACK;  // Black disc
   public static final Color COLOR_WHITE = Color.WHITE;   // White disc
   public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

   // Game states
   public enum State {
      PLAYING, BLACK_WON, WHITE_WON
   }
   private State currentState;  // the current game state

   // Seeds (discs)
   public enum Seed {
      BLACK, WHITE, NO_SEED
   }
   private Seed currentPlayer; // the current player
   private Seed[][] board;     // Game board of ROWS-by-COLS cells

   // UI Components
   private GamePanel gamePanel; // Drawing canvas (JPanel) for the game board
   private JLabel statusBar;  // Status Bar

   /** Constructor to setup the game and the GUI components */
   public TTTGraphicsOthello() {
      // Initialize the game objects
      initGame();

      // Set up GUI components
      gamePanel = new GamePanel();  // Construct a drawing canvas (a JPanel)
      gamePanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

      // The canvas (JPanel) fires a MouseEvent upon mouse-click
      gamePanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
            int mouseX = e.getX();
            int mouseY = e.getY();
            // Get the row and column clicked
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
            } else {  // game over
               newGame();  // restart the game
            }
            repaint();  // Callback paintComponent().
         }
      });

      // Setup the status bar (JLabel) to display status message
      statusBar = new JLabel("       ");
      statusBar.setFont(FONT_STATUS);
      statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
      statusBar.setOpaque(true);
      statusBar.setBackground(COLOR_BG_STATUS);

      // Set up content pane
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(gamePanel, BorderLayout.CENTER);
      cp.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();  // pack all the components in this JFrame
      setTitle("Othello");
      setVisible(true);  // show this JFrame

      newGame();
   }

   /** Initialize the Game (run once) */
   public void initGame() {
      board = new Seed[ROWS][COLS]; // allocate array
   }

   /** Reset the game-board contents and the status, ready for new game */
   public void newGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = Seed.NO_SEED; // all cells empty
         }
      }
      currentPlayer = Seed.BLACK;  // black plays first
      currentState = State.PLAYING; // ready to play
   }

   /** Update the game by flipping opponent's discs */
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

      // Check for game over and update the status
      if (isBoardFull()) {
         currentState = countDiscs();
      }
   }

   /** Helper method to flip opponent's discs in one direction */
   private void flipLine(Seed mySeed, int row, int col, int rowDelta, int colDelta) {
      Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
      int curRow = row + rowDelta;
      int curCol = col + colDelta;

      // Look for opponent's discs
      while (curRow >= 0 && curRow < ROWS && curCol >= 0 && curCol < COLS && board[curRow][curCol] == opponentSeed) {
         curRow += rowDelta;
         curCol += colDelta;
      }

      // If the line ends with the current player's disc, flip all in-between
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

   /** Check if the board is full */
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

   /** Count discs and determine the winner */
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

   /** Inner class DrawCanvas (extends JPanel) used for custom graphics drawing. */
   class GamePanel extends JPanel {
      private static final long serialVersionUID = 1L;

      @Override
      public void paintComponent(Graphics g) {  // Callback via repaint()
         super.paintComponent(g);    // fill background
         setBackground(COLOR_BG);    // set background color to green

         // Draw grid-lines
         g.setColor(COLOR_GRID);     // set grid color to black
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

   /** The entry main() method */
   public static void main(String[] args) {
      // Use the event-dispatching thread to build the UI for thread-safety.
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new TTTGraphicsOthello(); // Let the constructor do the job
         }
      });
   }
}
