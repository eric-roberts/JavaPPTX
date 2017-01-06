/*
 * File: PPCheckerboard.java
 * -------------------------
 * This program illustrates the operation of the edu.stanford.cs.pptx
 * package by creating a checkerboard on the screen.
 */

import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.PPSlide;
import java.awt.Color;

/**
 * This class creates a standard 8x8 checkerboard on the
 * PowerPoint screen.  The checkerboard uses the square size
 * shown in the constant definitions and centers the completed
 * checkerboard on the screen.
 */

public class PPCheckerboard {

   public static void main(String[] args) {
      PPShow ppt = new PPShow();
      PPSlide slide = new PPSlide();
      slide.addTitle("Checkerboard Example");
      double cx = ppt.getWindowWidth() / 2;
      double cy = ppt.getWindowHeight() / 2;
      double x0 = cx - SQUARE_SIZE * N_COLUMNS / 2;
      double y0 = cy - SQUARE_SIZE * N_ROWS / 2;
      for (int i = 0; i < N_ROWS; i++) {
         for (int j = 0; j < N_COLUMNS; j++) {
            double x = x0 + j * SQUARE_SIZE;
            double y = y0 + i * SQUARE_SIZE;
            PPRect sq = new PPRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            Color color = ((i + j) % 2 == 0) ? Color.WHITE : Color.BLACK;
            sq.setFillColor(color);
            slide.add(sq);
         }
      }
      ppt.add(slide);
      ppt.save("PPCheckerboard.pptx");
      System.out.println("PPCheckerboard.pptx");
   }

/* Constants */

   private static final double SQUARE_SIZE = 40;
   private static final int N_ROWS = 8;
   private static final int N_COLUMNS = 8;

}
