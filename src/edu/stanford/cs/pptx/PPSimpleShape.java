/*
 * File: PPSimpleShape.java
 * ------------------------
 * This abstract class represents the class of shapes that can
 * be colored and filled.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.options.OptionParser;
import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

abstract class PPSimpleShape extends PPShape {

/**
 * Scales the shape uniformly by <code>sf</code> in each dimension.
 * Note that this method changes the initial scale factor of the shape and
 * does not offer a mechanism to scale a shape dynamically.
 *
 * @param sf The scale factor
 */

   public void setScale(double sf) {
      setScale(sf, sf);
   }

/**
 * Scales the shape independently along the two coordinate axes.
 * Note that this method changes the initial scale factor of the shape and
 * does not offer a mechanism to scale a shape dynamically.
 *
 * @param sx The scale factor in the <i>x</i> direction
 * @param sy The scale factor in the <i>y</i> direction
 */

   public void setScale(double sx, double sy) {
      Rectangle2D bounds = getBounds();
      setBounds(bounds.getX(), bounds.getY(),
                sx * bounds.getWidth(), sy * bounds.getHeight());
   }

/**
 * Rotates the shape counterclockwise by <code>theta</code> degrees.
 * Note that this method changes the initial orientation of the shape and
 * does not offer a mechanism to rotate a shape dynamically.
 *
 * @param theta The number of degrees by which the shape is rotated
 */

   public void setRotation(double theta) {
      rotation = theta;
   }

/**
 * Returns the rotation angle of the shape.
 *
 * @return The rotation angle of the shape
 */

   public double getRotation() {
      return rotation;
   }

/**
 * Sets both the line and fill color of the shape to the specified color.
 *
 * @param color The new color for both the outline and the interior
 */

   public void setColor(Color color) {
      setFillColor(color);
      setLineColor(color);
   }

/**
 * Sets both the line and fill color of the shape to the specified
 * color, which is either a standard color name or a string in the
 * form <code>"#rrggbb"</code>.
 *
 * @param str The new color expressed as a string
 */

   public void setColor(String str) {
      setColor(PPUtil.decodeColor(str));
   }

/**
 * Sets the fill color of the shape.
 *
 * @param color The new fill color
 */

   public void setFillColor(Color color) {
      fillColor = color;
   }

/**
 * Sets the fill color of the shape to the specified color,
 * which is either a standard color name or a string in the
 * form <code>"#rrggbb"</code>.  The string <code>"none"</code>
 * can be used to specify an unfilled shape.
 *
 * @param str The new color expressed as a string
 */

   public void setFillColor(String str) {
      setFillColor(PPUtil.decodeColor(str));
   }

/**
 * Gets the fill color of the shape.
 *
 * @return The fill color of the shape
 */

   public Color getFillColor() {
      return fillColor;
   }

/**
 * Sets the line color of the shape.
 *
 * @param color The new line color
 */

   public void setLineColor(Color color) {
      lineColor = color;
   }

/**
 * Sets the line color of the shape to the specified color,
 * which is either a standard color name or a string in the
 * form <code>"#rrggbb"</code>.
 *
 * @param str The new color expressed as a string
 */

   public void setLineColor(String str) {
      setLineColor(PPUtil.decodeColor(str));
   }

/**
 * Gets the line color of the shape.
 *
 * @return The line color of the shape
 */

   public Color getLineColor() {
      return lineColor;
   }

/**
 * Sets the line weight for the shape.
 *
 * @param weight The new line weight in points
 */

   public void setLineWeight(double weight) {
      lineWeight = weight;
   }

/**
 * Gets the line weight for the shape.
 *
 * @return The line weight in points
 */

   public double getLineWeight() {
      return lineWeight;
   }

/**
 * Sets the arrow mode for the start of the line.  The arrow mode is a
 * hyphen-separated string consisting of the type (<code>triangle</code>,
 * <code>stealth</code>, <code>diamond</code>, <code>oval</code>,
 * <code>circle</code>, or <code>arrow</code>), a size indicator for
 * the width of the arrowhead (<code>large</code>, <code>medium</code>,
 * or <code>small</code>), and a similar size indicator for the length
 * of the arrowhead.
 *
 * @param str A string indicating the arrowhead style and shape
 */

   public void setStartArrow(String str) {
      startArrow = str;
   }

/**
 * Gets the arrow mode for the start of the line.
 *
 * @return A string indicating the arrowhead style and shape
 */

   public String getStartArrow() {
      return startArrow;
   }

/**
 * Sets the arrow mode for the end of the line.  The arrow mode is a
 * hyphen-separated string consisting of the type (<code>triangle</code>,
 * <code>stealth</code>, <code>diamond</code>, <code>oval</code>,
 * <code>circle</code>, or <code>arrow</code>), a size indicator for
 * the width of the arrowhead (<code>large</code>, <code>medium</code>,
 * or <code>small</code>), and a similar size indicator for the length
 * of the arrowhead.
 *
 * @param str A string indicating the arrowhead style and shape
 */

   public void setEndArrow(String str) {
      endArrow = str;
   }

/**
 * Gets the arrow mode for the end of the line.
 *
 * @return A string indicating the arrowhead style and shape
 */

   public String getEndArrow() {
      return endArrow;
   }

/* Protected methods */

   protected PPSimpleShape() {
      setColor(Color.BLACK);
      setLineWeight(1);
      setStartArrow(null);
      setEndArrow(null);
   }

   protected OptionParser createOptionParser() {
      return new PPSimpleShapeOptionParser(this);
   }

   protected String getArrowTag(String options) {
      if (options == null) return "";
      String type = "triangle";
      String width = "lg";
      String length = "med";
      boolean widthSpecified = false;
      if (!options.isEmpty()) {
         for (String str : options.split("-")) {
            if (str.equals("circle")) {
               type = "oval";
               width = "med";
            } else if (isArrowType(str)) {
               type = str;
            } else if (str.equals("large")) {
               if (widthSpecified) {
                  length = "lg";
               } else {
                  width = length = "lg";
                  widthSpecified = true;
               }
            } else if (str.equals("medium")) {
               if (widthSpecified) {
                  length = "med";
               } else {
                  width = length = "med";
                  widthSpecified = true;
               }
            } else if (str.equals("small")) {
               if (widthSpecified) {
                  length = "sm";
               } else {
                  width = length = "sm";
                  widthSpecified = true;
               }
            } else {
               throw new RuntimeException("Illegal arrow specification");
            }
         }
      }
      return " type='" + type + "' w='" + width + "' len='" + length + "'";
   }

/* Private methods */

   private boolean isArrowType(String str) {
      for (String type : ARROW_TYPES) {
         if (str.equals(type)) return true;
      }
      return false;
   }

/* Arrow types */

   private static final String[] ARROW_TYPES = {
      "triangle",
      "stealth",
      "diamond",
      "oval",
      "arrow"
   };

/* Private instance variables */

   private Color fillColor;
   private Color lineColor;
   private String endArrow;
   private String startArrow;
   private double lineWeight;
   private double rotation;

}
