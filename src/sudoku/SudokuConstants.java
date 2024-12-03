/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */

package src.sudoku;

public class SudokuConstants {
    public static int GRID_SIZE = 9; // Default size
    public static int SUBGRID_SIZE = 3; // Default sub-grid size

    public static void setSize(int gridSize) {
        GRID_SIZE = gridSize;
        SUBGRID_SIZE = (gridSize == 12) ? 4 : (gridSize == 6) ? 2 : 3;
    }
}
