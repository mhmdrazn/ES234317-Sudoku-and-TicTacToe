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

public class AIPlayerMedium extends AIPlayer {

    public AIPlayerMedium(Board board) {
        super(board);
    }

    @Override
    public int[] move(){
        int[] bestMove = {0, 0};
        int bestScore = Integer.MIN_VALUE;

        // Ckeck all possible move
        for (int row = 0; row < ROWS; row++){
            for (int col = 0; col < COLS; col++){
                if (cells[row][col].content == Seed.NO_SEED){
                    // Make a move
                    cells[row][col].content = mySeed;

                    // Evaluate the score
                    int score  = evaluateBoard();

                    // Delete the seed
                    cells[row][col].content = Seed.NO_SEED;
                
                    // DO a move based on the best score
                    if (score > bestScore){
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }

        return bestMove;
    }

    private int evaluateBoard(){
        int score = 0;

        // check all row, col, and diagonals
        score += evaluateLines();
        return score;
    }

    private int evaluateLines(){
        int score = 0;

        // evaluate rows
        for (int row = 0; row < ROWS; row++){
            score += evaluateLine(row, 0, row, 1, row, 2);
        }

        // evaluate column
        for (int col = 0; col < COLS; col++){
            score += evaluateLine(0, col, 1,  col, 2, col);
        }

        // evaluate diagonal
        score += evaluateLine(0, 0, 1, 1, 2, 2);
        score += evaluateLine(0, 2, 1, 1, 2, 0);
        
        return score;
    }

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3){
        int score = 0;

        // check AI
        score += getLineScore(row1, col1, row2, col2, row3, col3, mySeed);

        // check the opponent
        score -= getLineScore(row1, col1, row2, col2, row3, col3, oppSeed);

        return score;
    }

    private int getLineScore(int row1, int col1, int row2, int col2, int row3, int col3, Seed seed){
        int seedCount = 0;
        int emptyCount = 0;

        // count the seeds cells
        if (cells[row1][col1].content == Seed.CROSS || cells[row1][col1].content == Seed.NOUGHT) seedCount++;
        if (cells[row2][col2].content == Seed.CROSS || cells[row1][col1].content == Seed.NOUGHT) seedCount++;
        if (cells[row3][col3].content == Seed.CROSS || cells[row1][col1].content == Seed.NOUGHT) seedCount++;

        // count the empty cells
        if (cells[row1][col1].content == Seed.NO_SEED) emptyCount++;
        if (cells[row2][col2].content == Seed.NO_SEED) emptyCount++;
        if (cells[row3][col3].content == Seed.NO_SEED) emptyCount++;

        // check if the lines contains opponent's seed
        if (seedCount + emptyCount < 3){
            return 0; // contains opponent's seed, no score
        }

        // return score based on the number of seeds and empty cells
        if (seedCount == 3) return 100;
        if (seedCount == 2 && emptyCount == 1) return 200;
        if (seedCount == 1 && emptyCount == 2) return 1;

        return 0;
    }
}
