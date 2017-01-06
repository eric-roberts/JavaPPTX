/*
 * File: PPMonospacedLabel.java
 * ----------------------------
 * This package class acts as a label in which the font appears as if it
 * were monospaced even if it isn't.  This class is used primarily to
 * display hex numbers in Helvetica Neue so that all digits are the same
 * width.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPGroup;
import edu.stanford.cs.pptx.PPTextBox;
import java.awt.Font;

class PPMonospacedLabel extends PPGroup {

   public PPMonospacedLabel(String text, String str, String unit) {
      this(text, Font.decode(str), unit);
   }

   public PPMonospacedLabel(String text, Font font, String unit) {
      PPTextBox box = new PPTextBox(text);
      box.setFont(font);
      charWidth = box.getFontMetrics().stringWidth(unit);
      int nc = text.length();
      for (int i = 0; i < nc; i++) {
         box = new PPTextBox(text.substring(i, i + 1));
         box.setFont(font);
         box.setVerticalAlignment("Middle");
         box.setHorizontalAlignment("Center");
         box.setBounds(0, 0, charWidth, box.getHeight());
         add(box, i * charWidth, 0);
      }
   }

   public double getCharWidth() {
      return charWidth;
   }

/* Private instance variables */

   private double charWidth;

}
