/*
 * File: PPRectCallout.java
 * ------------------------
 * This class represents a rectangular box with a callout wedge.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPOutputStream;

/**
 * This class represents a rectangular box with a callout wedge.
 */

public class PPRectCallout extends PPAutoShape {

/**
 * Creates a new <code>PPRectCallout</code> shape with the specified
 * coordinates and size.  Coordinates and distances are consistent with
 * the Java graphics model.  All distances are measured in pixels, and
 * the origin for the coordinate system is in the upper left corner of
 * the screen.
 *
 * @param x The <i>x</i> coordinate of the upper left corner
 * @param y The <i>y</i> coordinate of the upper left corner
 * @param width The width of the oval
 * @param height The height of the oval
 */

   public PPRectCallout(double x, double y, double width, double height) {
      setBounds(x, y, width, height);
   }

/**
 * Creates a new <code>PPRectCallout</code> shape from the specified
 * coordinates and size.  This version of the constructor operates in
 * much the same manner as the standard
 * {@link #PPRectCallout(double,double,double,double) PPRectCallout}
 * constructor, but allows the caller to specify an option string.  The
 * advantage of embedding the option string in the constructor is that
 * doing so often makes it possible to add the shape to the slide
 * immediately without storing it in a temporary variable.
 *
 * @param x The <i>x</i> coordinate of the upper left corner
 * @param y The <i>y</i> coordinate of the upper left corner
 * @param width The width of the oval
 * @param height The height of the oval
 * @param options The options (see {@link PPAutoShape#setOptions(String)})
 */

   public PPRectCallout(double x, double y, double width, double height,
                 String options) {
      this(x, y, width, height);
      setOptions(options);
   }

/**
 * Creates a new <code>PPRectCallout</code> shape from its dimensions.
 * This version of the constructor is useful when you want to create the
 * shape and then add it to the slide at a position computed subsequently.
 *
 * @param width The width of the oval
 * @param height The height of the oval
 */

   public PPRectCallout(double width, double height) {
      this(0, 0, width, height);
   }

/**
 * Creates a new <code>PPRectCallout</code> shape from its dimensions and
 * an options string.
 *
 * @param width The width of the oval
 * @param height The height of the oval
 * @param options The options (see {@link PPAutoShape#setOptions(String)})
 */

   public PPRectCallout(double width, double height, String options) {
      this(width, height);
      setOptions(options);
   }

/**
 * Sets the position of the wedge point relative to the center of the oval.
 *
 * @param x The displacement of the wedge in the x direction
 * @param y The displacement of the wedge in the y direction
 */

   public void setWedgePoint(double x, double y) {
      wedgeX = x;
      wedgeY = y;
   }

// Add parser options for this parameter

/* Protected methods */

   @Override
   protected String presetGeometry() {
      return "wedgeRectCallout";
   }

   @Override
   protected void dumpShapeParameters(PPOutputStream os) {
      os.print("<a:avLst>");
      int adj1 = (int) Math.round(100000 * wedgeX / getWidth());
      int adj2 = (int) Math.round(100000 * wedgeY / getHeight());
      os.print("<a:gd name='adj1' fmla='val " + adj1 + "'/>");
      os.print("<a:gd name='adj2' fmla='val " + adj2 + "'/>");
      os.print("</a:avLst>");
   }

/* Private instance variables */

   private double wedgeX;
   private double wedgeY;

}
