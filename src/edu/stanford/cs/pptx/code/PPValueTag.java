/*
 * File: PPValueTag.java
 * ---------------------
 * This class represents a value that labels the value of an expression
 * in a stack frame.
 */

package edu.stanford.cs.pptx.code;

import edu.stanford.cs.pptx.PPGroup;
import edu.stanford.cs.pptx.PPLine;
import edu.stanford.cs.pptx.PPOvalCallout;
import edu.stanford.cs.pptx.PPTextBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

/**
 * This class represents a value that labels the value of an expression
 * in a stack frame.  The tag hangs below a code region and consists of
 * a line and an oval callout.
 */

public class PPValueTag extends PPGroup {

/**
 * Creates a new value tag below the specified rectangle with the indicated
 * value.
 *
 * @param bb The bounding box around the code this tag marks
 * @param value The value displayed in the oval
 */

   public PPValueTag(Rectangle2D bb, Object value) {
      this(bb, value, DEFAULT_OVAL_WIDTH, DEFAULT_OVAL_HEIGHT);
   }

/**
 * Creates a new value tag below the specified rectangle with the indicated
 * value.  This version allows the client to set the width of the oval.
 *
 * @param bb The bounding box around the code this tag marks
 * @param value The value displayed in the oval
 * @param width The width of the oval
 */

   public PPValueTag(Rectangle2D bb, Object value, double width) {
      this(bb, value, width, DEFAULT_OVAL_HEIGHT);
   }

/**
 * Creates a new value tag below the specified rectangle with the indicated
 * value.  This version allows the client to set the size of the oval.
 *
 * @param bb The bounding box around the code this tag marks
 * @param value The value displayed in the oval
 * @param width The width of the oval
 * @param height The height of the oval
 */

   public PPValueTag(Rectangle2D bb, Object value,
                     double width, double height) {
      lineMarker = new PPLine(bb.getX(), bb.getY() + bb.getHeight(),
                              bb.getX() + bb.getWidth(),
                              bb.getY() + bb.getHeight());
      lineMarker.setLineWeight(DEFAULT_LINE_WEIGHT);
      lineMarker.setLineColor(DEFAULT_LINE_COLOR);
      add(lineMarker);
      double x = bb.getX() + (bb.getWidth() - width) / 2;
      double y = bb.getY() + bb.getHeight() +
                 (WEDGE_FRACTION - 0.5) * height + WEDGE_OFFSET;
      valueOval = new PPOvalCallout(x, y, width, height);
      valueOval.setFillColor(DEFAULT_FILL_COLOR);
      valueOval.setLineWeight(DEFAULT_LINE_WEIGHT);
      valueOval.setLineColor(DEFAULT_LINE_COLOR);
      add(valueOval);
      valueOval.setWedgePoint(0, -WEDGE_FRACTION * height);
      valueTextBox = new PPTextBox();
      valueTextBox.setBounds(x, y, width, height + TEXT_DELTA_H);
      valueTextBox.setVerticalAlignment("Middle");
      valueTextBox.setHorizontalAlignment("Center");
      valueTextBox.setFont(DEFAULT_FONT);
      valueTextBox.setText(value.toString());
      add(valueTextBox);
   }

/**
 * Sets the fill color of the value tag.
 *
 * @param color The new fill color
 */

   public void setFillColor(Color color) {
      valueOval.setFillColor(color);
   }

/**
 * Sets the fill color of the value tag to the specified color,
 * which is either a standard color name or a string in the
 * form <code>"#rrggbb"</code>.  The string <code>"none"</code>
 * can be used to specify an unfilled shape.
 *
 * @param str The new color expressed as a string
 */

   public void setFillColor(String str) {
      valueOval.setFillColor(str);
   }

/**
 * Sets the line color of the value tag.
 *
 * @param color The new line color
 */

   public void setLineColor(Color color) {
      lineMarker.setLineColor(color);
      valueOval.setLineColor(color);
   }

/**
 * Sets the line color of the value tag to the specified color,
 * which is either a standard color name or a string in the
 * form <code>"#rrggbb"</code>.
 *
 * @param str The new color expressed as a string
 */

   public void setLineColor(String str) {
      lineMarker.setLineColor(str);
      valueOval.setLineColor(str);
   }

/**
 * Sets the line weight for the value tag.
 *
 * @param weight The new line weight in points
 */

   public void setLineWeight(double weight) {
      lineMarker.setLineWeight(weight);
      valueOval.setLineWeight(weight);
   }

/**
 * Sets the font for the value box.
 *
 * @param font The new font for the value box
 */

   public void setFont(Font font) {
      valueTextBox.setFont(font);
   }

/**
 * Sets the font for the value box.  In this version of
 * <code>setFont</code>, the font is expressed as a string in the form
 *
 *<pre>
 *    "<i>family</i>-<i>style</i>-<i>size</i>"
 *</pre>
 *
 * @param str A string representing the font
 */

   public void setFont(String str) {
      valueTextBox.setFont(str);
   }

/**
 * Sets the font color of the value tag.
 *
 * @param color The new font color
 */

   public void setFontColor(Color color) {
      valueTextBox.setFontColor(color);
   }

/**
 * Sets the font color of the value tag to the specified color,
 * which is either a standard color name or a string in the
 * form <code>"#rrggbb"</code>.
 *
 * @param str The new color expressed as a string
 */

   public void setFontColor(String str) {
      valueTextBox.setFontColor(str);
   }

/* Private constants */

   private static final Color DEFAULT_FILL_COLOR = new Color(0xFFFF99);
   private static final Color DEFAULT_LINE_COLOR = Color.RED;
   private static final String DEFAULT_FONT = "Courier New-Bold-20";
   private static final double DEFAULT_LINE_WEIGHT = 2;
   private static final double DEFAULT_OVAL_WIDTH = 60;
   private static final double DEFAULT_OVAL_HEIGHT = 30;
   private static final double TEXT_DELTA_H = -3;
   private static final double WEDGE_FRACTION = 0.8;
   private static final double WEDGE_OFFSET = 3;

/* Private instance variables */

   private PPLine lineMarker;
   private PPOvalCallout valueOval;
   private PPTextBox valueTextBox;

}
