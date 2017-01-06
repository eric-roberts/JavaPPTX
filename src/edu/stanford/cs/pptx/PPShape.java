/*
 * File: PPShape.java
 * ------------------
 * This class encapsulates a single object on a PowerPoint slide,
 * which are collectively called "shapes".  This class represents
 * the base of a hierarchy that includes specific subclasses for
 * the most common entries on a slide.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.options.OptionParser;
import edu.stanford.cs.pptx.effect.AnimationEffect;
import edu.stanford.cs.pptx.effect.BezierMotionEffect;
import edu.stanford.cs.pptx.effect.LinearMotionEffect;
import edu.stanford.cs.pptx.effect.MotionPathState;
import edu.stanford.cs.pptx.util.PPOutputStream;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * This abstract class represents the top of the hierarchy that includes
 * all of the "shapes" that can appear on a PowerPoint slide.
 * As with any abstract class, you cannot create an object of class
 * <code>PPShape</code> but instead create an object of one of
 * its concrete subclasses, each of which inherits the properties
 * common to all shapes.
 *
 * <p>As an example, you can construct a <code>PPRect</code>
 * object and assign it to a <code>PPShape</code> variable
 * like this:</p>
 *
 *<pre>
 *    PPShape shape = new PPRect(100, 100, 200, 50);
 *</pre>
 */

public abstract class PPShape {

/**
 * Sets the name for this shape.  This name appears in the PowerPoint
 * Custom Animation dialog and makes it much easier to identify the
 * individual shapes on a slide.
 *
 * @param name The new name for this shape
 */

   public void setName(String name) {
      shapeName = name;
   }

/**
 * Returns the name for this shape.  In no name has been set, the name
 * is a string in the form
 *
 *<pre>
 *    <i>class</i>#<i>id</i>
 *</pre>
 *
 * @return The name for this shape
 */

   public String getName() {
      return (shapeName == null) ? getTypeName() + "#" + id : shapeName;
   }

/**
 * Returns the type name of this shape.
 *
 * @return The type name of this shape
 */

   public String getTypeName() {
      String className = getClass().getName();
      return className.substring(className.lastIndexOf('.') + 1);
   }

/**
 * Sets various optional parameters for this shape from an option string
 * consisting of key names (or key/value pairs for some options) in one
 * of the following forms:
 *
 *<pre>
 *    /<i>key</i>
 *</pre>
 *
 * or
 *
 *<pre>
 *    /<i>key</i>:<i>value</i>
 *</pre>
 *
 * For example, if you have created a <code>PPRect</code> called
 * <code>myBox</code>, you can set its fill color and line weight
 * like this:
 *
 *<pre>
 *    myBox.setOptions("/fillColor:BLUE/lineWeight:1.5");
 *</pre>
 *
 * The list of available options depends on the shape and are detailed in
 * the documentation of the individual shape types.
 *
 * @param str The option string used to set the options
 */

   public void setOptions(String str) {
      createOptionParser().parseOptions(str);
   }

/**
 * Sets the slide to which this shape belongs.
 *
 * @param slide The slide to which this shape belongs
 */

   public void setSlide(PPSlide slide) {
      this.slide = slide;
   }

/**
 * Returns the <code>PPSlide</code> object to which this shape
 * belongs.  Before a shape is added to a slide, the <code>getSlide</code>
 * method returns <code>null</code>.
 *
 * @return The slide to which this shape belongs
 */

   public PPSlide getSlide() {
      return slide;
   }

/**
 * Adds an animation to the shape.  Animations are specified using a
 * string that begins with the effect name, possibly followed by a set
 * of key names (or key/value pairs for some options) in one of the
 * following forms:
 *
 *<pre>
 *    /<i>key</i>
 *</pre>
 *
 * or
 *
 *<pre>
 *    /<i>key</i>:<i>value</i>
 *</pre>
 *
 * For example, you can add a simple "Appear" animation effect to a shape
 * using the following code (although you can also use the
 * {@link #appear() appear} method):
 *
 *<pre>
 *    shape.addAnimation("Appear");
 *</pre>
 *
 * Similarly, you can specify a "Checkerboard" effect that moves across the
 * shape triggered 2.0 seconds after the previous effect completes like this:
 *
 *<pre>
 *    shape.addAnimation("Checkerboard/across/afterPrev/delay:2.0");
 *</pre>
 *
 * A complete list of the implemented animations and their options is
 * available <a href="../../../../effects.html">here</a>.
 *
 * @param str The effect name, optionally followed by options
 */

   public void addAnimation(String str) {
      PPSlide slide = getSlide();
      if (slide == null) {
         throw new RuntimeException("Animations can only be applied " +
                                    "to installed shapes");
      }
      AnimationEffect effect = AnimationEffect.createEffect(str, this);
      if (effect instanceof LinearMotionEffect) {
         initMotionPathState();
         ((LinearMotionEffect) effect).setState(mpState);
      }
      slide.addAnimation(effect);
   }

/**
 * Adds an <code>Appear</code> animation to this object, using the default
 * trigger of <code>/onClick</code>.
 */

   public void appear() {
      appear("");
   }

/**
 * Adds an <code>Appear</code> animation to this object, using the specifed
 * options string.  If no trigger action is specified, the trigger is assumed
 * to be <code>/onClick</code>.
 *
 * @param options The option string for the animation
 */

   public void appear(String options) {
      addAnimation("Appear/onClick" + options);
   }

/**
 * Adds a <code>Disappear</code> animation to this object, using the default
 * trigger of <code>/onClick</code>.
 */

   public void disappear() {
      disappear("");
   }

/**
 * Adds a <code>Disappear</code> animation to this object, using the specifed
 * options string.  If no trigger action is specified, the trigger is assumed
 * to be <code>/onClick</code>.
 *
 * @param options The option string for the animation
 */

   public void disappear(String options) {
      addAnimation("Disappear/onClick" + options);
   }

/**
 * Sets the default speed for motion path animations involving this shape.
 * Whenever you call the <code>move</code> or <code>moveTo</code> methods
 * to move this shape on the window, the shape will move at the specified
 * speed unless that value is overridden explicitly.  Setting a speed means
 * that objects move at a constant rate, independent of the length of the
 * motion.
 *
 * @param pixelsPerSecond The speed of the object
 */

   public void setMotionPathSpeed(double pixelsPerSecond) {
      speed = pixelsPerSecond;
   }

/**
 * Returns the motion path speed for this shape in pixels per second.
 *
 * @return The motion path speed for this shape in pixels per second
 */

   public double getMotionPathSpeed() {
      return speed;
   }

/**
 * Adds a motion path animation that moves this shape the specified
 * distance from its current location in each dimension.  This form
 * of the <code>move</code> method uses the most common options
 * for the motion path segment, which indicate that the shape should move
 * at its current speed (as set by <code>setMotionPathSpeed</code>
 * and that the animation should begin immediately after the preceding
 * one.  For more fine-grained control of the motion path, use the version
 * of {@link #move(double,double,java.lang.String) move}
 * that uses an additional parameter to specify options.
 *
 * @param dx The distance this object moves in the <i>x</i> direction
 * @param dy The distance this object moves in the <i>y</i> direction
 */

   public void move(double dx, double dy) {
      move(dx, dy, "");
   }

/**
 * Adds a motion path animation that moves this shape the specified
 * distance from its current location in each dimension.  This form
 * of the <code>move</code> method allows the caller to specify
 * additional options, as follows:
 *
 * <table width=100% border=0 cellspacing=6 cellpadding=0 summary="">
 * <tr><td valign=top><code>/afterPrev</code></td><td valign=top>
 *             Indicates that this motion path segment should start
 *             when the preceding animation effect finishes;
 *             <code>/afterPrev</code> is the default for
 *             motion paths.</td></tr>
 * <tr><td valign=top><code>/withPrev</code></td><td valign=top>
 *             Indicates that this motion path segment should run in
 *             parallel with the preceding animation effect.</td></tr>
 * <tr><td valign=top><code>/onClick</code></td><td valign=top>
 *             Indicates that this motion path segment should start when
 *             the user clicks the mouse.</td></tr>
 * <tr><td valign=top><code>/speed:</code><i>pixels/sec</i></td><td valign=top>
 *             Indicates that the shape should move at the specified
 *             speed, overriding the default speed of the object.
 *             The speed value is measured in pixels per second.</td></tr>
 * <tr><td valign=top><code>/duration:</code><i>seconds</i></td><td valign=top>
 *             Indicates that the motion path should take the specified
 *             time, overriding the speed of the object.  To move a shape
 *             instantly, specify a duration of 0.</td></tr>
 * <tr><td valign=top><code>/acc</code></td><td valign=top>
 *             Indicates that the shape should accelerate at the beginning
 *             of its motion, giving a smoother feel to the animation.  In
 *             general, the <code>/acc</code> option appears only on
 *             the first segment of a chained motion path.</td></tr>
 * <tr><td valign=top><code>/dec</code></td><td valign=top>
 *             Indicates that the shape should decelerate at the end
 *             of its motion, giving a smoother feel to the animation.  In
 *             general, the <code>/dec</code> option appears only on
 *             the last segment of a chained motion path.</td></tr>
 * </table>
 *
 * @param dx The distance this object moves in the <i>x</i> direction
 * @param dy The distance this object moves in the <i>y</i> direction
 * @param options A string specifying additional options about the motion
 */

   public void move(double dx, double dy, String options) {
      PPSlide slide = getSlide();
      if (slide == null) {
         throw new RuntimeException("Animations can only be applied " +
                                    "to installed shapes");
      }
      initMotionPathState();
      String str = "LinearMotion";
      str += "/dx:" + dx + "/dy:" + dy +
             "/speed:" + speed + "/afterPrev" + options;
      LinearMotionEffect mpEffect =
         (LinearMotionEffect) AnimationEffect.createEffect(str, this);
      mpEffect.setState(mpState);
      slide.addAnimation(mpEffect);
      mpState.adjustCurrentLocation(dx, dy);
   }

/**
 * Adds a motion path animation that moves this shape to a new location.
 * This method is similar to the {@link #move(double,double)} method
 * except that the motion is indicated by specifying the final location
 * rather than the <i>dx</i> and <i>dy</i> displacements.
 *
 * @param x The final <i>x</i> coordinate after the motion
 * @param y The final <i>y</i> coordinate after the motion
 */

   public void moveTo(double x, double y) {
      Point2D current = getLocation();
      move(x - current.getX(), y - current.getY());
   }

/**
 * Adds a motion path animation that moves this shape to a new location.
 * This method is similar to the {@link #move(double,double,String)}
 * method except that the motion is indicated by specifying the final
 * location rather than the <i>dx</i> and <i>dy</i> displacements.
 *
 * @param x The final <i>x</i> coordinate after the motion
 * @param y The final <i>y</i> coordinate after the motion
 * @param options A string specifying additional options about the motion
 */

   public void moveTo(double x, double y, String options) {
      Point2D current = getLocation();
      move(x - current.getX(), y - current.getY(), options);
   }

/**
 * Adds an animation to move this shape along the specified Bezier curve.
 * The control points of the curve are the current point, the two
 * intermediate points given by (x1, y1) and (x2, y2), and the final
 * point (x, y).
 *
 * @param x1 The <i>x</i> coordinate of the first Bezier point
 * @param y1 The <i>y</i> coordinate of the first Bezier point
 * @param x2 The <i>x</i> coordinate of the second Bezier point
 * @param y2 The <i>y</i> coordinate of the second Bezier point
 * @param x3 The final <i>x</i> coordinate after the motion
 * @param y3 The final <i>y</i> coordinate after the motion
 */

   public void curveTo(double x1, double y1, double x2, double y2,
                       double x3, double y3) {
      curveTo(x1, y1, x2, y2, x3, y3, "");
   }

/**
 * Adds an animation to move this shape along the specified Bezier curve.
 * The control points of the curve are the current point, the two
 * intermediate points given by (x1, y1) and (x2, y2), and the final
 * point (x3, y3).  This version allows the client to specify options.
 *
 * @param x1 The <i>x</i> coordinate of the first Bezier point
 * @param y1 The <i>y</i> coordinate of the first Bezier point
 * @param x2 The <i>x</i> coordinate of the second Bezier point
 * @param y2 The <i>y</i> coordinate of the second Bezier point
 * @param x3 The final <i>x</i> coordinate after the motion
 * @param y3 The final <i>y</i> coordinate after the motion
 * @param options A string specifying additional options about the motion
 */

   public void curveTo(double x1, double y1, double x2, double y2,
                       double x3, double y3, String options) {
      Point2D current = getLocation();
      double dx1 = x1 - current.getX();
      double dy1 = y1 - current.getY();
      double dx2 = x2 - current.getX();
      double dy2 = y2 - current.getY();
      double dx3 = x3 - current.getX();
      double dy3 = y3 - current.getY();
      PPSlide slide = getSlide();
      if (slide == null) {
         throw new RuntimeException("Animations can only be applied " +
                                    "to installed shapes");
      }
      initMotionPathState();
      String str = "BezierMotion";
      str += "/dx1:" + dx1 + "/dy1:" + dy1 +
             "/dx2:" + dx2 + "/dy2:" + dy2 + 
             "/dx3:" + dx3 + "/dy3:" + dy3 + 
             "/speed:" + speed + "/afterPrev" + options;
      BezierMotionEffect mpEffect =
         (BezierMotionEffect) AnimationEffect.createEffect(str, this);
      mpEffect.setState(mpState);
      slide.addAnimation(mpEffect);
      mpState.adjustCurrentLocation(dx3, dy3);
   }

/**
 * Returns the current location of the shape.  In the typical case in which
 * no motion paths are involved, this location is simply the initial location.
 * If an object is moving as part of a motion path sequence, this method
 * returns the current location on the path.  Note that there is no symmetric
 * <code>setLocation</code> method to ensure that the intent is clear.  If
 * you want to set the initial location of a shape, you need to call
 * {@link #setInitialLocation(double,double) setInitialLocation};
 * to move it to a new location using a motion path, use
 * {@link #moveTo(double,double) moveTo}.
 *
 * @return A <code>Point2D</code> indicating the current location of the shape
 */

   public Point2D getLocation() {
      if (mpState == null) {
         return getInitialLocation();
      } else {
         return mpState.getCurrentLocation();
      }
   }

/**
 * Returns the current <i>x</i> coordinate of the shape.
 *
 * @return The current <i>x</i> coordinate of the shape
 */

   public double getX() {
      return getLocation().getX();
   }

/**
 * Returns the current <i>y</i> coordinate of the shape.
 *
 * @return The current <i>y</i> coordinate of the shape
 */

   public double getY() {
      return getLocation().getY();
   }

/**
 * Returns the coordinates of the center of the shape.  Subclasses can
 * override this method if they assume a different geometric model of the
 *
 * @return The coordinates of the center
 */

   public Point2D getCenter() {
      return new Point2D.Double(getX() + getWidth() / 2,
                                getY() + getHeight() / 2);
   }

/**
 * Returns the initial location of the shape ignoring any motion path
 * animations.
 *
 * @return A <code>Point2D</code> indicating the initial location of the shape
 */

   public Point2D getInitialLocation() {
      Rectangle2D bounds = getBounds();
      if (bounds == null) return new Point2D.Double(0, 0);
      return new Point2D.Double(bounds.getX(), bounds.getY());
   }

/**
 * Sets the initial location for this shape on the slide.  Note that
 * this method affects the location at which the shape appears on
 * the initial slide and does not have anything to do with motion
 * paths.
 *
 * @param x The initial <i>x</i> coordinate for the shape
 * @param y The initial <i>y</i> coordinate for the shape
 */

   public void setInitialLocation(double x, double y) {
      Rectangle2D rect = getBounds();
      setBounds(x, y, rect.getWidth(), rect.getHeight());
   }

/**
 * Sets the initial location for this shape on the slide.  Note that
 * this method affects the location at which the shape appears on
 * the initial slide and does not have anything to do with motion
 * paths.
 *
 * @param loc A <code>Point2D</code> value specifying the initial location
 */

   public void setInitialLocation(Point2D loc) {
      setInitialLocation(loc.getX(), loc.getY());
   }

/**
 * Sets the initial bounding rectangle for this shape, which affects both
 * its location and dimensions.
 *
 * @param x The initial <i>x</i> coordinate for the shape
 * @param y The initial <i>y</i> coordinate for the shape
 * @param width The desired width of the shape
 * @param height The desired height of the shape
 */

   public void setBounds(double x, double y, double width, double height) {
      setBounds(new Rectangle2D.Double(x, y, width, height));
   }

/**
 * Sets the initial bounding rectangle for this shape on the slide.
 *
 * @param bounds A <code>Rectangle2D</code> value specifying the initial bounds
 */

   public void setBounds(Rectangle2D bounds) {
      this.bounds = bounds;
   }

/**
 * Returns the bounding rectangle at which this shape was originally
 * placed on the slide.
 *
 * @return The bounding rectangle for this shape
 */

   public Rectangle2D getBounds() {
      return bounds;
   }

/**
 * Returns the width of the shape
 *
 * @return The width of this shape
 */

   public double getWidth() {
      return getBounds().getWidth();
   }

/**
 * Returns the height of the shape.
 *
 * @return The height of this shape
 */

   public double getHeight() {
      return getBounds().getHeight();
   }

/**
 * Returns a printable representation of this shape.
 *
 * @return A printable representation of this shape
 */

   public String toString() {
      return getName();
   }

/**
 * Returns a unique numeric id for this shape.
 *
 * @return A unique numeric id for this shape
 */

   public int getShapeId() {
      return id;
   }

/* Protected methods */

   protected PPShape() {
      id = ++nextId;
      shapeName = null;
      mpState = null;
      setMotionPathSpeed(MotionPathState.DEFAULT_SPEED);
   }

   protected void dumpShape(PPOutputStream os) {
      throw new RuntimeException("Not yet implemented");
   }

   protected void dumpShapeRels(PPOutputStream os) {
      /* Empty */
   }

   protected void adjustLocation(double dx, double dy) {
      Rectangle2D bounds = getBounds();
      setInitialLocation(bounds.getX() + dx, bounds.getY() + dy);
   }

   protected OptionParser createOptionParser() {
      return new PPShapeOptionParser(this);
   }

/* Private methods */

   private void initMotionPathState() {
      if (mpState == null) {
         mpState = new MotionPathState(getInitialLocation());
      }
   }

/* Private class variables */

   private static int nextId = 0;

/* Private instance variables */

   private MotionPathState mpState;
   private PPSlide slide;
   private Rectangle2D bounds;
   private String shapeName;
   private double speed;
   private int id;

}
