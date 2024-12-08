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

            add(numberButtons[i]);
        }
    }
}