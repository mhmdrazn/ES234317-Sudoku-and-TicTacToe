package sudoku.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class numberList extends JPanel {
    private static final long serialVersionUID = 1L;

    private int selectedNumber = 0;
    private JButton[] numberButtons = new JButton[9];
    private int[] numberCounts = new int[9];

    public numberList() {
        setLayout(new FlowLayout());
        setBackground(new Color(33, 37, 49));

        // Create and add number buttons
        for (int i = 0; i < 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i + 1));
            numberButtons[i].setBackground(new Color(38, 49, 81));
            numberButtons[i].setForeground(Color.WHITE);
            numberButtons[i].setFont(new Font("Figtree", Font.PLAIN, 18));

            final int number = i + 1; 
            numberButtons[i].addActionListener(e -> {
                if (selectedNumber != number){
                    SudokuMain.input = number;
                    System.out.println("Selected number: " + SudokuMain.input); // Debugging line
                    selectedNumber = number;
                }
            });

            add(numberButtons[i]); // Add button to the panel
        }
    }

    // New method to check and update button states based on the game board
    public void updateButtonStates(Cell[][] cells) {
        // Reset counts
        numberCounts = new int[9];

        // Count correct guesses for each number
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (cells[row][col].status == CellStatus.CORRECT_GUESS) {
                    int number = cells[row][col].getNumber();
                    numberCounts[number - 1]++;
                }
            }
        }

        // Disable buttons for numbers that have reached 9 correct guesses
        for (int i = 0; i < 9; i++) {
            numberButtons[i].setEnabled(numberCounts[i] < 9);
            if (numberCounts[i] >= 9) {
                System.out.println("Button " + (i + 1) + " is disabled");
            }
        }
    }

    public void incrementNumberCount(int number){
        if (number < 1 || number > 9) return;
        numberCounts[number - 1]++;

        if (numberCounts[number - 1] >= 9){
            numberButtons[number - 1].setEnabled(false);
            System.out.println("Button " + number + " is disabled");
        }
    }

    public void decrementNumberButton(int number){
        if (number < 1 || number > 9) return;
        if (numberCounts[number - 1] > 0){
            numberCounts[number -1]--;
        }

        if (numberCounts[number - 1]  < 9){
            numberButtons[number - 1].setEnabled(true);
        }
    }

    public int getSelectedNumber() {
        return selectedNumber;
    }

    public void resetSelectedNumber() {
        selectedNumber = 0;
    }
}