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

/**
 * Enum Seed digunakan untuk:
 * 1. Player: mengambil nilai CROSS atau NOUGHT.
 * 2. Cell content: mengambil nilai CROSS, NOUGHT, atau NO_SEED.
 * 
 * Setiap nilai juga memiliki ikon tampilan (teks atau gambar).
 * Gambar dapat digambar dengan:
 *   g.drawImage(content.getImage(), x, y, width, height, null);
 */
public enum Seed {
   CROSS("X", "tictactoe/src/images/cat.gif"),       // displayName, imageFilename
   NOUGHT("O", "tictactoe/src/images/dog.gif"),
   NO_SEED(" ", null);          // NO_SEED tidak memiliki gambar

   // Variabel instance
   private final String displayName;
   private final Image img;

   // Konstruktor (harus private)
   private Seed(String name, String imageFilename) {
      this.displayName = name;

      // Memuat gambar jika file path diberikan
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

   // Getter untuk displayName
   public String getDisplayName() {
      return displayName;
   }

   // Getter untuk gambar
   public Image getImage() {
      return img;
   }
}
