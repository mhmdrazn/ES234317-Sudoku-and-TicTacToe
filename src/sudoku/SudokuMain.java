package src.sudoku;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // private variables
   GameBoardPanel board = new GameBoardPanel();
   JButton btnNewGame = new JButton("New Game");

   // Constructor
   public void play() {
      CardLayout cardLayout = new CardLayout();
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());

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
}