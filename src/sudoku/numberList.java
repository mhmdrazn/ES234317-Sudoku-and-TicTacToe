package src.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class numberList extends JPanel {
    private static final long serialVersionUID = 1L;

    private int selectedNumber = 0; // Store the selected number
    private JButton[] numberButtons = new JButton[9]; // Array to hold number buttons

    // Constructor
    public numberList() {
        setLayout(new FlowLayout()); // Use FlowLayout to align buttons horizontally
        setBackground(new Color(30, 30, 60)); // Dark blue background color

        // Create and add number buttons to the panel
        for (int i = 0; i < 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i + 1));
            numberButtons[i].setBackground(new Color(50, 50, 100)); // Darker color for buttons
            numberButtons[i].setForeground(Color.WHITE); // White text on buttons
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 18)); // Set button font

            // Add ActionListener to handle button clicks
            final int number = i + 1;  // Store the number on the button
            numberButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedNumber = number; // Update the selected number when button is clicked
                    System.out.println("Selected number: " + selectedNumber); // Debugging line
                }
            });

            add(numberButtons[i]); // Add button to the panel
        }
    }

    // Get the selected number
    public int getSelectedNumber() {
        return selectedNumber;
    }

    // Reset the selected number (if needed, for example, in a "Clear" button)
    public void resetSelectedNumber() {
        selectedNumber = 0;
    }
}
