/*
 * File: PPLetterFrequencies.java
 * ------------------------------
 * This file creates a Powerpoint slide that demonstrates using an array
 * to keep track of letter frequencies.
 */

import edu.stanford.cs.pptx.*;
import java.awt.*;
import java.util.*;

/**
 * This class creates a Powerpoint slide that illustrates the use of
 * an array to count letter frequencies.  The input string is displayed
 * along the right edge of a large funnel that covers the array.  Each
 * letter in turn is dropped through the funnel and shunted off to the
 * appropriate array index, where it increments the associated count.
 */

public class PPLetterFrequencies {

/* Parameters to control the slide */

   private static final String INPUT_TEXT = "TWAS BRILLIG";
   private static final String INPUT_FONT = "SansSerif-18";
   private static final String ARRAY_FONT = "Serif-18";
   private static final double FUNNEL_WIDTH = 620;
   private static final double FUNNEL_HEIGHT = 285;
   private static final double FUNNEL_MOUTH_WIDTH = 40;
   private static final double FUNNEL_MOUTH_HEIGHT = 60;
   private static final double FUNNEL_ARRAY_MARGIN = 3;
   private static final double FUNNEL_SPEED = 200;

   public static void main(String[] args) {
      ppt = new PPShow();
      slide = new PPSlide();
      slide.addTitle("Letter Frequencies");
      initFunnelParameters();
      addFunnelWalls();
      addInitialArray();
      addInputStack(INPUT_TEXT);
      processInputStack();
      ppt.add(slide);
      ppt.save("PPLetterFrequencies.pptx");
      System.out.println("PPLetterFrequencies.pptx");
   }

   private static void initFunnelParameters() {
      metricsBox = new PPTextBox(" ");
      metricsBox.setFont(INPUT_FONT);
      elementSize = (FUNNEL_WIDTH - 2 * FUNNEL_ARRAY_MARGIN) / 26;
      x0 = (ppt.getWindowWidth() - FUNNEL_WIDTH) / 2;
      y0 = ppt.getWindowHeight() - FUNNEL_HEIGHT - x0;
      yBaseline = y0 + metricsBox.getHeight();
      yArray = y0 + FUNNEL_HEIGHT - elementSize;
   }

   private static void addFunnelWalls() {
      double x1 = x0 + (FUNNEL_WIDTH - FUNNEL_MOUTH_WIDTH) / 2;
      double x2 = x1 + FUNNEL_MOUTH_WIDTH;
      double x3 = x0 + FUNNEL_WIDTH;
      double y1 = yBaseline;
      double y2 = yBaseline + FUNNEL_MOUTH_HEIGHT;
      double y3 = yArray - metricsBox.getHeight();
      slide.add(new PPLine(x0, y3, x1, y2));
      slide.add(new PPLine(x1, y2, x1, y1));
      slide.add(new PPLine(x2, y1, x2, y2));
      slide.add(new PPLine(x2, y2, x3, y3));
   }

   private static void addInitialArray() {
      for (char ch = 'A'; ch <= 'Z'; ch++) {
         PPShape arrayBox = createArrayBox(ch, 0);
         slide.add(arrayBox);
         PPTextBox label = new PPTextBox("" + (ch - 'A'));
         label.setFont("Serif-12");
         label.setHorizontalAlignment("Center");
         double x = arrayBox.getX() - 4;
         double y = arrayBox.getY() + arrayBox.getHeight();
         double width = arrayBox.getWidth() + 8;
         label.setBounds(x, y, width, label.getHeight());
         slide.add(label);
      }
   }

   private static void addInputStack(String str) {
      input = new Stack<PPTextBox>();
      double x = x0 + FUNNEL_WIDTH - metricsBox.getRightMargin();
      double y = y0;
      for (int i = str.length() - 1; i >= 0; i--) {
         char ch = str.charAt(i);
         PPTextBox box = new PPTextBox("" + ch);
         box.setFont(INPUT_FONT);
         box.setMotionPathSpeed(FUNNEL_SPEED);
         x -= box.getFontMetrics().stringWidth("" + ch) + 2;
         slide.add(box, x, y);
         input.push(box);
      }
   }

   private static void processInputStack() {
      int[] counts = new int[26];
      double left = metricsBox.getLeftMargin();
      double y1 = y0;
      double y2 = y0 + FUNNEL_MOUTH_HEIGHT + metricsBox.getHeight();
      double y3 = yArray - metricsBox.getHeight();
      while (!input.isEmpty()) {
         PPTextBox box = input.pop();
         char ch = box.getText().charAt(0);
         if (Character.isUpperCase(ch)) {
            String trigger = "/onClick";
            double cWidth = box.getFontMetrics().stringWidth("" + ch);
            double x1 = x0 + (FUNNEL_WIDTH - cWidth) / 2 - left;
            double x2 = x1;
            double x3 = x0 + FUNNEL_ARRAY_MARGIN
                           + (ch - 'A' + 0.5) * elementSize
                           - cWidth / 2 - left;
            double dy = counts[ch - 'A'] * box.getFontMetrics().getHeight();
            box.moveTo(x1, y1, "/acc" + trigger);
            box.moveTo(x2, y2, "/afterPrev");
            box.moveTo(x3, y3 - dy, "/dec/afterPrev");
            PPRect arrayBox = createArrayBox(ch, ++counts[ch - 'A']);
            slide.add(arrayBox);
            arrayBox.appear("/afterPrev");
         }
      }
   }

   private static PPRect createArrayBox(char ch, int count) {
      double x = x0 + FUNNEL_ARRAY_MARGIN + (ch - 'A') * elementSize;
      double y = yArray;
      PPRect box = new PPRect(x, y, elementSize, elementSize);
      box.setFillColor(Color.WHITE);
      box.setText("" + count);
      box.setFont(ARRAY_FONT);
      return box;
   }


/* Static variables */

   private static PPShow ppt;
   private static PPSlide slide;
   private static PPTextBox metricsBox;
   private static Stack<PPTextBox> input;
   private static double x0;
   private static double y0;
   private static double yArray;
   private static double yBaseline;
   private static double elementSize;



}
