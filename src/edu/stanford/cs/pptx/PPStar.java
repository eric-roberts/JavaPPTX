/*
 * File: PPStar.java
 * -----------------
 * This class represents a five-pointed star in a PowerPoint slide.
 */

package edu.stanford.cs.pptx;

import java.awt.geom.Rectangle2D;

/**
 * This class represents a five-pointed star in a PowerPoint slide.
 * The usual approach to working with a <code>PPStar</code>
 * shape is to create a new <code>PPStar</code> object and then
 * to add that object to a slide, as illustrated in the following
 * code, which creates a 100x100-pixel solid red star for which the
 * upper left corner of the bounding box is the point (200, 100):
 *
 *<pre>
 *    PPSlide slide = new PPSlide();
 *    PPStar star = new PPStar(100, 100);
 *    star.setColor(Color.RED);
 *    slide.add(star, 200, 100);
 *</pre>
 *
 * It is also possible to set properties of the <code>PPStar</code> shape,
 * either by passing an option string to the constructor or by invoking
 * methods in the class.
 */

public class PPStar extends PPAutoShape {

/**
 * Creates a new <code>PPStar</code> shape with the specified
 * coordinates and size.  Coordinates and distances are consistent with
 * the Java graphics model.  All distances are measured in pixels, and
 * the origin for the coordinate system is in the upper left corner of
 * the screen.
 *
 * @param x The <i>x</i> coordinate of the upper left corner
 * @param y The <i>y</i> coordinate of the upper left corner
 * @param width The width of the bounding box
 * @param height The height of the bounding box
 */

   public PPStar(double x, double y, double width, double height) {
      setBounds(x, y, width, height);
   }

/**
 * Creates a new <code>PPStar</code> shape from the specified coordinates
 * and size.  This version of the constructor operates in much the same manner
 * as the standard {@link #PPStar(double,double,double,double) PPStar}
 * constructor, but allows the caller to specify an option string.  The
 * advantage of embedding the option string in the constructor is that doing
 * so often makes it possible to add the shape to the slide immediately
 * without storing it in a temporary variable.
 *
 * @param x The <i>x</i> coordinate of the upper left corner
 * @param y The <i>y</i> coordinate of the upper left corner
 * @param width The width of the bounding box
 * @param height The height of the bounding box
 * @param options The options string
 */

   public PPStar(double x, double y, double width, double height,
                 String options) {
      this(x, y, width, height);
      setOptions(options);
   }

/**
 * Creates a new <code>PPStar</code> shape from its dimensions.  This
 * version of the constructor is useful when you want to create the shape
 * and then add it to the slide at a position computed subsequently.
 *
 * @param width The width of the bounding box
 * @param height The height of the bounding box
 */

   public PPStar(double width, double height) {
      this(0, 0, width, height);
   }

/**
 * Creates a new <code>PPStar</code> shape from its dimensions and an options
 * string.
 *
 * @param width The width of the bounding box
 * @param height The height of the bounding box
 * @param options The options string
 */

   public PPStar(double width, double height, String options) {
      this(width, height);
      setOptions(options);
   }

/**
 * Creates a new <code>PPStar</code> shape from its bounding rectangle.
 *
 * @param bb A <code>Rectangle2D</code> that bounds the rectangle
 */

   public PPStar(Rectangle2D bb) {
      this(bb.getX(), bb.getY(), bb.getWidth(), bb.getHeight());
   }

/**
 * Creates a new <code>PPStar</code> shape from its bounding rectangle and
 * an option string.
 *
 * @param bb A <code>Rectangle2D</code> that bounds the rectangle.
 * @param options The options string
 */

   public PPStar(Rectangle2D bb, String options) {
      this(bb);
      setOptions(options);
   }

/* Protected methods */

   @Override
   protected String presetGeometry() {
      return "star5";
   }

   protected PPStar() {
      /* Empty */
   }
}
