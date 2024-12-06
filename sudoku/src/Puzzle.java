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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The Sudoku number puzzle to be solved
*/
public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    int[][] playerGuesses = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    // Random number generator for additional randomness
    private Random random = new Random();

    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the difficulty level
    public void newPuzzle(String difficultyLevel) {
        int cellsToGuess;
        switch (difficultyLevel.toLowerCase()) {
            case "easy":
                cellsToGuess = 20; // Number of empty cells
                break;
            case "medium":
                cellsToGuess = 35;
                break;
            case "hard":
                cellsToGuess = 50;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level: " + difficultyLevel);
        }
    
        // Reset the numbers and isGiven arrays
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                numbers[row][col] = 0;
                isGiven[row][col] = false;
                playerGuesses[row][col] = 0;
            }
        }

        // Generate a full valid Sudoku puzzle
        generatePuzzle(numbers, 0, 0);
    
        // Create a list of all cell indices (0 to 80 for a 9x9 grid)
        List<Integer> cellIndices = new ArrayList<>();
        for (int i = 0; i < SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE; i++) {
            cellIndices.add(i);
        }
    
        // Shuffle the indices to randomize which cells will be blank
        Collections.shuffle(cellIndices);
    
        // Create the puzzle by removing the desired number of cells
        for (int i = 0; i < cellsToGuess; i++) {
            int index = cellIndices.get(i);
            int row = index / SudokuConstants.GRID_SIZE;
            int col = index % SudokuConstants.GRID_SIZE;
            numbers[row][col] = 0; // Make the cell blank
            isGiven[row][col] = false; // Mark it as a cell to guess
        }
    
        // Now set isGiven for the non-empty cells (those that remain filled in numbers)
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (numbers[row][col] != 0) {
                    isGiven[row][col] = true; // Mark as given
                }
            }
        }
    }

    public CellStatus checkGuess(int row, int col, int guess) {
        // Validate input
        if (row < 0 || row >= SudokuConstants.GRID_SIZE || 
            col < 0 || col >= SudokuConstants.GRID_SIZE) {
            return CellStatus.WRONG_GUESS;
        }
    
        // Check if it's a given cell
        if (isGiven[row][col]) {
            return CellStatus.GIVEN;
        }
    
        // Update player's guess
        playerGuesses[row][col] = guess;
    
        // Check if the guess is correct
        if (numbers[row][col] == guess) {
            return CellStatus.CORRECT_GUESS;
        } else {
            return CellStatus.WRONG_GUESS;
        }
    }

    public boolean isPuzzleSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                // Skip given cells
                if (isGiven[row][col]) continue;
                
                // Check if non-given cells match the solution
                if (playerGuesses[row][col] != numbers[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean generatePuzzle(int[][] puzzle, int row, int col) {
        // Check if we've reached the end of the rows
        if (row == SudokuConstants.GRID_SIZE) {
            return true; // The grid is successfully filled
        }
    
        // Calculate the next row and column
        int nextRow = (col == SudokuConstants.GRID_SIZE - 1) ? row + 1 : row;
        int nextCol = (col + 1) % SudokuConstants.GRID_SIZE;
    
        // If the cell is already filled, move to the next
        if (puzzle[row][col] != 0) {
            return generatePuzzle(puzzle, nextRow, nextCol);
        }
    
        // Create a shuffled list of numbers (1-9) for randomness
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= SudokuConstants.GRID_SIZE; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums);
    
        // Try placing each number in the current cell
        for (int num : nums) {
            if (isSafe(puzzle, row, col, num)) {
                puzzle[row][col] = num; // Place the number
                if (generatePuzzle(puzzle, nextRow, nextCol)) {
                    return true; // Continue to the next cell
                }
                puzzle[row][col] = 0; // Backtrack if necessary
            }
        }
    
        return false; // No valid number found, trigger backtracking
    }
    
    public boolean isSafe(int[][] puzzle, int row, int col, int num) {
        // Check if the number is already in the row or column
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (puzzle[row][i] == num || puzzle[i][col] == num) {
                return false;
            }
        }
    
        // Check if the number is already in the 3x3 subgrid
        int boxRowStart = row - row % 3;
        int boxColStart = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (puzzle[boxRowStart + i][boxColStart + j] == num) {
                    return false;
                }
            }
        }
    
        return true; // The placement is valid
    }

    public boolean isCorrectGuess(int row, int col, int guess) {
        // Check if the cell is not a given cell and the guess matches the solution
        return !isGiven[row][col] && numbers[row][col] == guess;
    }

    public int getCorrectNumber(int row, int col) {
        return numbers[row][col];
    }

    public int[][] getNumbers() {
        return numbers;
    }

    public boolean[][] getIsGiven() {
        return isGiven;
    }
}