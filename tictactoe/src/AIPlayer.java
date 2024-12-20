/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */

package tictactoe.src;

public abstract class AIPlayer {
    protected int ROWS = Board.ROWS;
    protected int COLS = Board.COLS;

    protected Cell[][] cells;
    protected Seed mySeed;
    protected Seed oppSeed;

    public AIPlayer(Board board) {
        cells = board.cells;
    }

    public void setSeed(Seed seed) {
        this.mySeed = seed;
        oppSeed = (mySeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    abstract int[] move();
}