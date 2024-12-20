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

import java.awt.Image;
import java.io.File;
// import java.net.URL;
import javax.swing.ImageIcon;

public enum Seed {
   CROSS("X", "tictactoe/src/images/x.png"),
   NOUGHT("O", "tictactoe/src/images/o.png"),
   NO_SEED(" ", null);

   private String displayName;
   private Image img = null;

   private Seed(String name, String imageFilename) {
      this.displayName = name;

      if (imageFilename != null) {
         File imgFile = new File(imageFilename);
         if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(imageFilename);
            img = icon.getImage();
         } else {
            System.err.println("Couldn't find file " + imageFilename);
         }
      }
   }

   public String getDisplayName() {
      return displayName;
   }
   public Image getImage() {
      return img;
   }
}