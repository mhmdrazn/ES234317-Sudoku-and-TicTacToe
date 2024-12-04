package tictactoe.src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTTGraphicsOthello extends JFrame {
   private static final long serialVersionUID = 1L;

   public static final int ROWS = 8; 
   public static final int COLS = 8;

   public static final int CELL_SIZE = 60;
   public static final int BOARD_WIDTH = CELL_SIZE * COLS;
   public static final int BOARD_HEIGHT = CELL_SIZE * ROWS;
   public static final int GRID_WIDTH = 10;
   public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

   public static final int CELL_PADDING = CELL_SIZE / 5;
   public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
   public static final int SYMBOL_STROKE_WIDTH = 8;
   public static final Color COLOR_BG = Color.WHITE;
   public static final Color COLOR_GRID = Color.LIGHT_GRAY;
   public static final Color COLOR_BLACK = Color.BLACK;
   public static final Color COLOR_WHITE = Color.WHITE;

   private enum State {
      PLAYING, GAME_OVER
   }

   private enum Seed {
      BLACK, WHITE, NO_SEED
   }

   private State currentState;
   private Seed currentPlayer;
   private Seed[][] board;

   private GamePanel gamePanel;
   private JLabel statusBar;

   public TTTGraphicsOthello() {
      board = new Seed[ROWS][COLS];
      initGame();

      gamePanel = new GamePanel();
      gamePanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
      gamePanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
               int row = e.getY() / CELL_SIZE;
               int col = e.getX() / CELL_SIZE;

               if (currentState == State.PLAYING) {
                  if (board[row][col] == Seed.NO_SEED) {
                     board[row][col] = currentPlayer;
                     updateGame(currentPlayer, row, col);
                     currentPlayer = (currentPlayer == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
                  }
               } else {
                  initGame();
               }
               repaint();
         }
      });

      statusBar = new JLabel(" ");
      statusBar.setOpaque(true);
      statusBar.setBackground(Color.LIGHT_GRAY);

      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(gamePanel, BorderLayout.CENTER);
      cp.add(statusBar, BorderLayout.PAGE_END);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
      setTitle("8x8 Flip Game");
      setVisible(true);

      initGame();
   }

   private void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
               board[row][col] = Seed.NO_SEED;
         }
      }
      board[3][3] = Seed.WHITE;
      board[3][4] = Seed.BLACK;
      board[4][3] = Seed.BLACK;
      board[4][4] = Seed.WHITE;
      currentPlayer = Seed.BLACK;
      currentState = State.PLAYING;
   }

   private void updateGame(Seed mySeed, int rowSelected, int colSelected) {
      Seed opponentSeed = (mySeed == Seed.BLACK) ? Seed.WHITE : Seed.BLACK;
      flipSeeds(mySeed, opponentSeed, rowSelected, colSelected);

      if (isBoardFull()) {
         currentState = State.GAME_OVER;
         int blackCount = countSeeds(Seed.BLACK);
         int whiteCount = countSeeds(Seed.WHITE);
         if (blackCount > whiteCount) {
               statusBar.setText("Black wins! Black: " + blackCount + ", White: " + whiteCount);
         } else if (whiteCount > blackCount) {
               statusBar.setText("White wins! White: " + whiteCount + ", Black: " + blackCount);
         } else {
               statusBar.setText("It's a draw! Black: " + blackCount + ", White: " + whiteCount);
         }
      }
   }

   private void flipSeeds(Seed mySeed, Seed opponentSeed, int row, int col) {
      flipDirection(mySeed, opponentSeed, row, col, 0, 1);   // Right
      flipDirection(mySeed, opponentSeed, row, col, 0, -1);  // Left
      flipDirection(mySeed, opponentSeed, row, col, 1, 0);   // Down
      flipDirection(mySeed, opponentSeed, row, col, -1, 0);  // Up
      flipDirection(mySeed, opponentSeed, row, col, 1, 1);   // Down-Right Diagonal
      flipDirection(mySeed, opponentSeed, row, col, -1, -1); // Up-Left Diagonal
      flipDirection(mySeed, opponentSeed, row, col, 1, -1);  // Down-Left Diagonal
      flipDirection(mySeed, opponentSeed, row, col, -1, 1);  // Up-Right Diagonal
   }

   private void flipDirection(Seed mySeed, Seed opponentSeed, int row, int col, int dRow, int dCol) {
      int r = row + dRow, c = col + dCol;
      while (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == opponentSeed) {
         r += dRow;
         c += dCol;
      }

      if (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == mySeed) {
         r -= dRow;
         c -= dCol;
         while (r != row || c != col) {
               board[r][c] = mySeed;
               r -= dRow;
               c -= dCol;
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

   private int countSeeds(Seed seed) {
      int count = 0;
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
               if (board[row][col] == seed) {
                  count++;
               }
         }
      }
      return count;
   }

   class GamePanel extends JPanel {
      private static final long serialVersionUID = 1L;

      @Override
      protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         setBackground(COLOR_BG);

         g.setColor(COLOR_GRID);
         for (int row = 1; row < ROWS; ++row) {
               g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF, BOARD_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
         }
         for (int col = 1; col < COLS; ++col) {
               g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH_HALF, 0, GRID_WIDTH, BOARD_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
         }

         for (int row = 0; row < ROWS; ++row) {
               for (int col = 0; col < COLS; ++col) {
                  int x = col * CELL_SIZE + CELL_PADDING;
                  int y = row * CELL_SIZE + CELL_PADDING;
                  if (board[row][col] == Seed.BLACK) {
                     g.setColor(COLOR_BLACK);
                     g.fillOval(x, y, SYMBOL_SIZE, SYMBOL_SIZE);
                  } else if (board[row][col] == Seed.WHITE) {
                     g.setColor(COLOR_WHITE);
                     g.fillOval(x, y, SYMBOL_SIZE, SYMBOL_SIZE);
                     g.setColor(COLOR_BLACK);
                     g.drawOval(x, y, SYMBOL_SIZE, SYMBOL_SIZE);
                  }
               }
         }
      }
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(TTTGraphics::new);
   }
}
