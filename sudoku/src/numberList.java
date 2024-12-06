/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */

package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class numberList extends JPanel {
    private static final long serialVersionUID = 1L;
    private JButton[] numberButtons;
    private JLabel[] numberCountLabels;
    private GameBoardPanel boardPanel;

    public numberList() {
        setLayout(new GridLayout(1, 9, 5, 5));
        setBackground(new Color(33, 37, 49));

        numberButtons = new JButton[9];
        numberCountLabels = new JLabel[9];

        for (int i = 0; i < 9; i++) {
            final int number = i + 1;
            
            // Create a panel for each number to hold button and count
            JPanel numberPanel = new JPanel(new BorderLayout());
            numberPanel.setBackground(new Color(33, 37, 49));

            // Number button
            numberButtons[i] = new JButton(String.valueOf(number));
            numberButtons[i].setBackground(new Color(59, 65, 84));
            numberButtons[i].setForeground(Color.WHITE);
            numberButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SudokuMain.input = number;
                }
            });

            // Number count label
            numberCountLabels[i] = new JLabel("0/9", SwingConstants.CENTER);
            numberCountLabels[i].setForeground(Color.WHITE);
            numberCountLabels[i].setFont(new Font("Figtree", Font.PLAIN, 10));
            numberCountLabels[i].setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

            numberPanel.add(numberButtons[i], BorderLayout.CENTER);
            numberPanel.add(numberCountLabels[i], BorderLayout.SOUTH);

            add(numberPanel);
        }
    }

    // Method to update number counts (should be cal    led after each cell update)
    public void updateNumberCounts(Cell[][] cells) {
        int[] counts = new int[9];
    
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (cells[row][col].status == CellStatus.CORRECT_GUESS || cells[row][col].status == CellStatus.GIVEN) {
                    int number = cells[row][col].getNumber();
                    counts[number - 1]++;
                }
            }
        }
    
        for (int i = 0; i < 9; i++) {
            numberCountLabels[i].setText(counts[i] + "/9");
            numberButtons[i].setEnabled(counts[i] < 9);
        }
    }
}