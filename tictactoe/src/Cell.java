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

import java.awt.*;

public class Cell {
   public static final int SIZE = 120;
   public static final int PADDING = SIZE / 5;
   public static final int SEED_SIZE = SIZE - PADDING * 2;

   Seed content;
   int row, col;

   public Cell(int row, int col) {
      this.row = row;
      this.col = col;
      content = Seed.NO_SEED;
   }

   public void newGame() {
      content = Seed.NO_SEED;
   }

   public void paint(Graphics graphic) {
      int x1 = col * SIZE + PADDING;
      int y1 = row * SIZE + PADDING;
      if (content == Seed.CROSS || content == Seed.NOUGHT) {
         graphic.drawImage(content.getImage(), x1, y1, SEED_SIZE, SEED_SIZE, null);
      }
   }
}