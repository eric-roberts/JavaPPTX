/*
 * File: PPBackgroundRect.java
 * ---------------------------
 * This file creates a PowerPoint slide with a background color rectangle.
 */

import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.PPSlide;

/**
 * This class creates a PowerPoint slide with a background color rectangle.
 */

public class PPBackgroundRect {

   public static void main(String[] args) {
      ppt = new PPShow();
      slide = new PPSlide();
      slide.addTitle("Background Rectangle");
      PPRect bgr = new PPRect(100, 150, 500, 200);
      bgr.setFillColor("bg");
      slide.add(bgr);
      ppt.add(slide);
      ppt.save("PPBackgroundRect.pptx");
      System.out.println("PPBackgroundRect.pptx");
   }

/* Static variables */

   private static PPShow ppt;
   private static PPSlide slide;

}
