/*
 * File: MotionPathState.java
 * --------------------------
 * This class holds information about the state of the object
 * as it proceeds through a sequence of MotionPath entries.
 */

package edu.stanford.cs.pptx.effect;

import java.awt.geom.Point2D;

public class MotionPathState {

   public static final double DEFAULT_SPEED = 100.0;

   public MotionPathState(Point2D loc) {
      this(loc.getX(), loc.getY());
   }

   public MotionPathState(double x, double y) {
      this.x0 = x;
      this.y0 = y;
      setCurrentLocation(x, y);
      resetEffectLocation();
      setSpeed(DEFAULT_SPEED);
   }

   public void setCurrentLocation(double x, double y) {
      cx = x;
      cy = y;
   }

   public void setCurrentLocation(Point2D loc) {
      cx = loc.getX();
      cy = loc.getY();
   }

   public void adjustCurrentLocation(double dx, double dy) {
      cx += dx;
      cy += dy;
   }

   public void adjustEffectLocation(double dx, double dy) {
      ex += dx;
      ey += dy;
   }

   public void resetEffectLocation() {
      ex = x0;
      ey = y0;
   }

   public Point2D getCurrentLocation() {
      return new Point2D.Double(cx, cy);
   }

   public Point2D getEffectLocation() {
      return new Point2D.Double(ex, ey);
   }

   public Point2D getInitialLocation() {
      return new Point2D.Double(x0, y0);
   }

   public void setSpeed(double pixelsPerSecond) {
      speed = pixelsPerSecond;
   }

   public double getSpeed() {
      return speed;
   }

/* Private data */

   private double x0;
   private double y0;
   private double cx;
   private double cy;
   private double ex;
   private double ey;
   private double speed;

}
