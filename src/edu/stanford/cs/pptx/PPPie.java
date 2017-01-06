/*
 * File: PPPie.java
 * ----------------
 * This class represents a pie-shaped wedge.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPOutputStream;

/**
 * This class represents a pie-shaped wedge.
 */

public class PPPie extends PPAutoShape {

/**
 * Creates a new <code>PPPie</code> shape with the specified coordinates,
 * size, and angles.  Coordinates and distances are consistent with
 * the Java graphics model.  All distances are measured in pixels, and
 * the origin for the coordinate system is in the upper left corner of
 * the screen.  The start and sweep angles are measured in degrees
 * counterclockwise from the +x axis.
 *
 * @param x The <i>x</i> coordinate of the upper left corner
 * @param y The <i>y</i> coordinate of the upper left corner
 * @param width The width of the full oval
 * @param height The height of the full oval
 * @param start The starting angle of the wedge in degrees
 * @param sweep The number of degrees covered by the wedge
 */

   public PPPie(double x, double y, double width, double height,
                double start, double sweep) {
      setBounds(x, y, width, height);
      if (start < 0) {
         this.start = -start % 360;
      } else {
         this.start = 360 - start % 360;
      }
      if (sweep < 0) {
         finish = this.start - sweep;
         if (finish < 0) finish = 360 - (-finish % 360);
      } else {
         finish = this.start;
         this.start = finish - sweep;
         if (this.start < 0) this.start = 360 - (-this.start % 360);
      }
   }

/**
 * Creates a new <code>PPPie</code> shape from the specified
 * coordinates and size.  This version of the constructor operates in
 * much the same manner as the standard
 * {@link #PPPie(double,double,double,double,double,double) PPPie}
 * constructor, but allows the caller to specify an option string.  The
 * advantage of embedding the option string in the constructor is that
 * doing so often makes it possible to add the shape to the slide
 * immediately without storing it in a temporary variable.
 *
 * @param x The <i>x</i> coordinate of the upper left corner
 * @param y The <i>y</i> coordinate of the upper left corner
 * @param width The width of the oval
 * @param height The height of the oval
 * @param start The starting angle of the wedge in degrees
 * @param sweep The number of degrees covered by the wedge
 * @param options The options (see {@link PPAutoShape#setOptions(String)})
 */

   public PPPie(double x, double y, double width, double height,
                double start, double sweep, String options) {
      this(x, y, width, height, start, sweep);
      setOptions(options);
   }

/**
 * Creates a new <code>PPPie</code> shape from its dimensions and angles.
 * This version of the constructor is useful when you want to create the
 * shape and then add it to the slide at a position computed subsequently.
 *
 * @param width The width of the oval
 * @param height The height of the oval
 * @param start The starting angle of the wedge in degrees
 * @param sweep The number of degrees covered by the wedge
 */

   public PPPie(double width, double height, double start, double sweep) {
      this(0, 0, width, height, start, sweep);
   }

/**
 * Creates a new <code>PPPie</code> shape from its dimensions, angles, and
 * an options string.
 *
 * @param width The width of the oval
 * @param height The height of the oval
 * @param start The starting angle of the wedge in degrees
 * @param sweep The number of degrees covered by the wedge
 * @param options The options (see {@link PPAutoShape#setOptions(String)})
 */

   public PPPie(double width, double height, double start, double sweep,
                String options) {
      this(width, height, start, sweep);
      setOptions(options);
   }

// Add parser options for this parameter

/* Protected methods */

   @Override
   protected String presetGeometry() {
      return "pie";
   }

   @Override
   protected void dumpShapeParameters(PPOutputStream os) {
      os.print("<a:avLst>");
      int adj1 = (int) Math.round(60000 * start);
      int adj2 = (int) Math.round(60000 * finish);
      os.print("<a:gd name='adj1' fmla='val " + adj1 + "'/>");
      os.print("<a:gd name='adj2' fmla='val " + adj2 + "'/>");
      os.print("</a:avLst>");
   }

/* Private instance variables */

   private double start;
   private double finish;

}
