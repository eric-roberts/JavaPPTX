/*
 * File: PPUSFlag.java
 * -------------------
 * This file creates a PowerPoint slide that contains a US flag.
 */

import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.PPSlide;
import edu.stanford.cs.pptx.PPStar;
import java.awt.Color;

/**
 * This program creates a PowerPoint slide that contains a US flag.
 */

public class PPUSFlag {

   private static final double FLAG_WIDTH = 642;
   private static final double FLAG_HEIGHT = 338;
   private static final double FIELD_FRACTION = 0.40;
   private static final double STAR_FRACTION = 0.40;

   public static void main(String[] args) {
      ppt = new PPShow();
      slide = new PPSlide();
      x0 = (PPShow.WIDTH - FLAG_WIDTH) / 2;
      y0 = (PPShow.HEIGHT - FLAG_HEIGHT) / 2;
      slide.addTitle("USFlag");
      addStripes();
      addField();
      ppt.add(slide);
      ppt.save("PPUSFlag.pptx");
      System.out.println("PPUSFlag.pptx");
   }

/* Adds the stripes */

   private static void addStripes() {
      double stripeHeight = FLAG_HEIGHT / 13;
      for (int i = 12; i >= 0; i--) {
         PPRect stripe = new PPRect(x0, y0 + i * stripeHeight,
                                    FLAG_WIDTH, stripeHeight);
         stripe.setColor((i % 2 == 0) ? Color.RED : Color.WHITE);
         slide.add(stripe);
      }
   }

/* Adds the star field */

   private static void addField() {
      double width = FIELD_FRACTION * FLAG_WIDTH;
      double height = FLAG_HEIGHT * 7 / 13;
      PPRect field = new PPRect(x0, y0, width, height);
      field.setColor(Color.BLUE);
      slide.add(field);
      double dx = width / 6;
      double dy = height / 5;
      double starSize = STAR_FRACTION * Math.min(dx, dy);
      for (int row = 0; row < 5; row++) {
         double y = y0 + (row + 0.5) * dy;
         for (int col = 0; col < 6; col++) {
            double x = x0 + (col + 0.5) * dx;
            PPStar star = new PPStar(x - starSize / 2, y - starSize / 2,
                                     starSize, starSize);
            star.setColor(Color.WHITE);
            slide.add(star);
         }
      }
      for (int row = 0; row < 4; row++) {
         double y = y0 + (row + 1) * dy;
         for (int col = 0; col < 5; col++) {
            double x = x0 + (col + 1) * dx;
            PPStar star = new PPStar(x - starSize / 2, y - starSize / 2,
                                     starSize, starSize);
            star.setColor(Color.WHITE);
            slide.add(star);
         }
      }
   }

/* Private variables */

   private static PPShow ppt;
   private static PPSlide slide;
   private static double x0;
   private static double y0;

}
