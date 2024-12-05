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

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * The Cell class model the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number and status.
 */
public class Cell extends JButton {
    private static final long serialVersionUID = 1L;

    public static final Color BG_GIVEN = new Color(33, 37, 49); // RGB
    public static final Color FG_GIVEN = new Color(255, 255, 255);
    public static final Color FG_NOT_GIVEN = new Color(245, 245, 245);
    public static final Color BG_TO_GUESS = new Color(16, 16, 24);
    public static final Color BG_CORRECT_GUESS = new Color(59, 65, 84);
    public static final Color FG_WRONG_GUESS = new Color(218, 0 , 4);
    public static final Color BG_WRONG_GUESS = new Color(59, 65, 84);
    public static final Font FONT_NUMBERS = new Font("Figtree", Font.PLAIN, 24);

    public static final Color BORDER_COLOR = new Color(23, 27, 39);  // Custom border color
    public static final Color BORDER_WRONG_COLOR = new Color(255, 0, 0);

    // Define properties (package-visible)
    /** The row and column number [0-8] of this cell */
    int row, col;
    /** The puzzle number [1-9] for this cell */
    int number;
    /** The status of this cell defined in enum CellStatus */
    CellStatus status;

    /** Constructor */
    public Cell(int row, int col) {
        super();   // JTextField
        this.row = row;
        this.col = col;
        // Inherited from JTextField: Beautify all the cells once for all
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
        setBorder(new LineBorder(BORDER_COLOR));
        setCellBorder(row,col);
    }

    /** Reset this cell for a new game, given the puzzle number and isGiven */
    public void newGame(int number, boolean isGiven) {
        this.number = number;
        status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        paint();    // paint itself
    }

    /** This Cell (JTextField) paints itself based on its status */
    public void paint() {
        if (status == CellStatus.GIVEN) {
            // Inherited from JTextField: Set display properties
            super.setText(number + "");
            super.setEnabled(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        } else if (status == CellStatus.TO_GUESS) {
            // Inherited from JTextField: Set display properties
            super.setText("");
            super.setEnabled(true);
            super.addActionListener( e -> {
                if(status == CellStatus.TO_GUESS || status == CellStatus.WRONG_GUESS){
                    setText("" + SudokuMain.input);
                }
            });
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_NOT_GIVEN);
        } else if (status == CellStatus.CORRECT_GUESS) {  // from TO_GUESS
            super.setBackground(BG_CORRECT_GUESS);
            super.setForeground(FG_GIVEN);
            super.setEnabled(true);
        } else if (status == CellStatus.WRONG_GUESS) {    // from TO_GUESS
            super.setBackground(BG_WRONG_GUESS);
            super.setForeground(FG_WRONG_GUESS);
            super.setEnabled(true);
        }
    }

    private void setCellBorder(int row, int col) {
        int top = (row % 3 == 0) ? 2 : 1;
        int left = (col % 3 == 0) ? 2 : 1;
        int bottom = (row % 3 == 2) ? 2 : 1;
        int right = (col % 3 == 2) ? 2 : 1;
        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, new Color(0, 2, 7)));
    }

    public int getNumber(){
        return number;
    }
}