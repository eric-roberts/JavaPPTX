/*
 * File: PPOval.java
 * -----------------
 * This class represents an elliptical shape in a PowerPoint slide.
 */

package edu.stanford.cs.pptx;

/**
 * This class represents an elliptical shape in a PowerPoint slide.
 * The usual approach to working with a <code>PPOval</code> shape is
 * to create a new <code>PPOval</code> object and then add
 * that object to a slide, as illustrated in the following code,
 * which adds a circle of radius 50 at the center of the screen:
 *
 *<pre>
 *    PPSlide slide = new PPSlide();
 *    double xc = slide.getWidth() / 2;
 *    double yc = slide.getHeight() / 2;
 *    PPOval circle = new PPOval(xc - 50, yc - 50, 100, 100);
 *    slide.add(circle);
 *</pre>
 *
 * It is also possible to set properties of the <code>PPRect</code> shape,
 * either by passing an option string to the constructor or by invoking methods
 * in the class.
 */

public class PPOval extends PPAutoShape {

/**
 * Creates a new <code>PPOval</code> shape with the specified
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

   public PPOval(double x, double y, double width, double height) {
      setBounds(x, y, width, height);
   }

/**
 * Creates a new <code>PPOval</code> shape from the specified coordinates
 * and size.  This version of the constructor operates in much the same manner
 * as the standard {@link #PPOval(double,double,double,double) PPOval}
 * constructor, but allows the caller to specify an option string.  The
 * advantage of embedding the option string in the constructor is that doing
 * so often makes it possible to add the shape to the slide immediately
 * without storing it in a temporary variable.
 *
 * @param x The <i>x</i> coordinate of the upper left corner
 * @param y The <i>y</i> coordinate of the upper left corner
 * @param width The width of the oval
 * @param height The height of the oval
 * @param options The options (see {@link PPAutoShape#setOptions(String)})
 */

   public PPOval(double x, double y, double width, double height,
                 String options) {
      this(x, y, width, height);
      setOptions(options);
   }

/**
 * Creates a new <code>PPOval</code> shape from its dimensions.
 * This version of the constructor is useful when you want to create the shape
 * and then add it to the slide at a position computed subsequently.
 *
 * @param width The width of the oval
 * @param height The height of the oval
 */

   public PPOval(double width, double height) {
      this(0, 0, width, height);
   }

/**
 * Creates a new <code>PPOval</code> shape from its dimensions and an options
 * string.
 *
 * @param width The width of the oval
 * @param height The height of the oval
 * @param options The options (see {@link PPAutoShape#setOptions(String)})
 */

   public PPOval(double width, double height, String options) {
      this(width, height);
      setOptions(options);
   }

/* Protected methods */

   @Override
   protected String presetGeometry() {
      return "ellipse";
   }

}
