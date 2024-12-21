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
import java.net.URL;
import javax.swing.ImageIcon;

public enum Seed {
   CROSS("X", "tictactoe/src/images/x.png"),
   NOUGHT("O", "tictactoe/src/images/o.png"),
   NO_SEED(" ", null);

   private final String displayName;
   private final Image img;

   private Seed(String name, String imageFilename) {
      this.displayName = name;

      if (imageFilename != null) {
         URL imgURL = getClass().getClassLoader().getResource(imageFilename);
         if (imgURL != null) {
            this.img = new ImageIcon(imgURL).getImage();
         } else {
            System.err.println("Error: File gambar '" + imageFilename + "' tidak ditemukan.");
            this.img = null;
         }
      } else {
         this.img = null;
      }
   }

   public String getDisplayName() {
      return displayName;
   }

   public Image getImage() {
      return img;
   }
}