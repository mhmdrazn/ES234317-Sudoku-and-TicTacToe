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

public class AIPLayerEasy extends AIPlayer {

   private int[][] preferredMoves = {
         {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2},
         {0, 1}, {1, 0}, {1, 2}, {2, 1}};

   public AIPLayerEasy(Board board) {
      super(board);
   }

   @Override
   public int[] move() {
      for (int[] move : preferredMoves) {
         int row = move[0];
         int col = move[1];
         if (cells[row][col].content == Seed.NO_SEED) {
            return new int[] {row, col};
         }
      }
      return null;
   }
}
