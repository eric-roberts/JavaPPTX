/*
 * File: PPYarnPattern.java
 * ------------------------
 * This file creates a PowerPoint slide that weaves a pattern of yarn
 * around an array of pegs.
 */

import edu.stanford.cs.pptx.PPLine;
import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPShow;
import edu.stanford.cs.pptx.PPSlide;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * This program creates a pattern that simulates the process of winding a
 * piece of colored yarn around an array of pegs along the edges of the
 * canvas.  On each cycle, the yarn is stretched from its current peg to
 * the one DELTA pegs further on.
 */

public class PPYarnPattern {

   public static void main(String[] args) {
      PPShow ppt = new PPShow();
      PPSlide slide = new PPSlide();
      slide.addTitle("Yarn Pattern");
      double dy = 25;
      double x = (ppt.getWindowWidth() - PEG_SEP * N_ACROSS) / 2;
      double y = (ppt.getWindowHeight() - PEG_SEP * N_DOWN) / 2 + dy;
      double width = N_ACROSS * PEG_SEP;
      double height = N_DOWN * PEG_SEP;
      PPRect background = new PPRect(x, y, width, height);
      background.setFillColor(Color.WHITE);
      slide.add(background);
      Point2D[] pegs = initPegArray(x, y);
      int thisPeg = 0;
      String trigger = "/onClick";
      for (int i = 0; i == 0 || thisPeg != 0; i++) {
         if (i == ON_CLICK_COUNT) {
            trigger = "/afterPrev/delay:" + ANIMATION_DELAY;
         }
         int nextPeg = (thisPeg + DELTA) % pegs.length;
         Point2D p0 = pegs[thisPeg];
         Point2D p1 = pegs[nextPeg];
         PPLine line = new PPLine(p0.getX(), p0.getY(), p1.getX(), p1.getY());
         line.setColor(YARN_COLOR);
         slide.add(line);
         line.appear(trigger);
         thisPeg = nextPeg;
      }
      ppt.add(slide);
      ppt.save("PPYarnPattern.pptx");
      System.out.println("PPYarnPattern.pptx");
   }

/* Creates a peg array whose upper left corner is at (x, y) */

   private static Point2D[] initPegArray(double x, double y) {
      Point2D[] pegs = new Point2D[2 * (N_ACROSS + N_DOWN)];
      int cp = 0;
      for (int i = 0; i < N_ACROSS; i++) {
         pegs[cp++] = new Point2D.Double(x + i * PEG_SEP, y);
      }
      for (int i = 0; i < N_DOWN; i++) {
         pegs[cp++] = new Point2D.Double(x + N_ACROSS * PEG_SEP,
                                         y + i * PEG_SEP);
      }
      for (int i = N_ACROSS; i > 0; i--) {
         pegs[cp++] = new Point2D.Double(x + i * PEG_SEP,
                                         y + N_DOWN * PEG_SEP);
      }
      for (int i = N_DOWN; i > 0; i--) {
         pegs[cp++] = new Point2D.Double(x, y + i * PEG_SEP);
      }
      return pegs;
   }

/* Constants */

   private static final int N_ACROSS = 43;
   private static final int N_DOWN = 31;
   private static final int DELTA = 61;
   private static final int ON_CLICK_COUNT = 4;
   private static final double PEG_SEP = 10;
   private static final double ANIMATION_DELAY = 0.02;
   private static final Color YARN_COLOR = Color.MAGENTA;

}
