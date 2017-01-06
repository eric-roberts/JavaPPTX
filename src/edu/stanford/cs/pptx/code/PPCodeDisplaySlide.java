/*
 * File: PPCodeDisplaySlide.java
 * -----------------------------
 * This class defines a slide that can display a multipage code display that
 * scrolls from one slide to the next.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPRect;
import edu.stanford.cs.pptx.PPSlide;
import java.awt.Color;
import java.awt.Font;

/**
 * This class defines a slide that can display a step-by-step trace of
 * a program.
 */

public class PPCodeDisplaySlide extends PPSlide {

/**
 * Creates a new <code>PPCodeTraceSlide</code> object.
 */

   public PPCodeDisplaySlide() {
      width = getWidth() - 2 * SIDE_MARGIN;
      height = getHeight() - TOP_MARGIN - BOTTOM_MARGIN;
      oldCodeImage = new PPCodeImage(width, height);
      newCodeImage = new PPCodeImage(width, height);
      setFont(DEFAULT_CODE_FONT);
      hasOldCode = false;
      hasBackgroundMasks = false;
   }

/**
 * Sets the font for the code box.
 *
 * @param font The font for the code box
 */

   public void setFont(Font font) {
      oldCodeImage.setFont(font);
      newCodeImage.setFont(font);
   }

/**
 * Sets the font for the code box.  In this version of <code>setFont</code>,
 * the font is expressed as a string in the form
 *
 *<pre>
 *    "<i>family</i>-<i>style</i>-<i>size</i>"
 *</pre>
 *
 * @param str A string representing the font
 */

   public void setFont(String str) {
      setFont(Font.decode(str));
   }

/**
 * Sets the old code.  If a slide contains both new and old code, the
 * old code must be set first.
 *
 * @param code The code from the old code page
 * @return The <code>PPCodeImage</code> for the old code
 */

   public PPCodeImage setOldCode(String[] code) {
      hasOldCode = true;
      oldCodeImage.setCode(code);
      return oldCodeImage;
   }

/**
 * Sets the new code.  If a slide contains both new and old code, the
 * old code must be set first.
 *
 * @param code The code from the new code page
 * @return The <code>PPCodeImage</code> for the new code
 */

   public PPCodeImage setNewCode(String[] code) {
      newCodeImage.setCode(code);
      PPRect box = new PPRect(SIDE_MARGIN, TOP_MARGIN, width, height);
      box.setFillColor(Color.WHITE);
      box.setLineColor(Color.BLACK);
      box.setLineWeight(1);
      add(box);
      if (hasOldCode) {
         add(oldCodeImage, SIDE_MARGIN, TOP_MARGIN);
         oldCodeImage.addAnimation("FlyOut/toTop/afterPrev/fast");
      }
      add(newCodeImage, SIDE_MARGIN, TOP_MARGIN);
      if (hasOldCode) {
         newCodeImage.addAnimation("FlyIn/fromBottom/withPrev/fast");
         addBackgroundMasks();
      }
      return newCodeImage;
   }

/**
 * Adds background masks to the top and bottom of the slide.  These masks
 * make it possible to scroll code images out of the window without having
 * them appear on the screen.
 */

   public void addBackgroundMasks() {
      if (hasBackgroundMasks) return;
      PPRect topMask = new PPRect(0, 0, getWidth(), TOP_MARGIN);
      topMask.setFillColor("bg");
      topMask.setLineColor("none");
      add(topMask);
      PPRect bottomMask = new PPRect(0, getHeight() - BOTTOM_MARGIN,
                                     getWidth(), BOTTOM_MARGIN);
      bottomMask.setFillColor("bg");
      bottomMask.setLineColor("none");
      add(bottomMask);
      sendToFront(getTitleShape());
      PPRect frame = new PPRect(SIDE_MARGIN, TOP_MARGIN, width, height);
      frame.setFillColor("none");
      frame.setLineColor(Color.BLACK);
      frame.setLineWeight(1);
      add(frame);
      hasBackgroundMasks = true;
   }

/* Private constants */

   private static final String DEFAULT_CODE_FONT = "Courier New-Bold-14";
   private static final double BOTTOM_MARGIN = 32;
   private static final double SIDE_MARGIN = 24;
   private static final double TOP_MARGIN = 85;

/* Private instance variables */

   private PPCodeImage newCodeImage;
   private PPCodeImage oldCodeImage;
   private boolean hasBackgroundMasks;
   private boolean hasOldCode;
   private double width;
   private double height;

}
