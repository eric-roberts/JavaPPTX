/*
 * File: PPPath.java
 * -----------------
 * This class represents a path that defines a freeform shape.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPOutputStream;
import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This class defines a path consisting of line segments, arc segments, and
 * cubic bezier curves.
 */

public class PPPath {

/**
 * Creates a new empty path.
 */

   public PPPath() {
      elements = new ArrayList<PathElement>();
   }

/**
 * Moves to the specified point without drawing a line.
 *
 * @param x The x coordinate of the new current point
 * @param y The y coordinate of the new current point
 */

   public void moveTo(double x, double y) {
      elements.add(new MoveToElement(x, y));
   }

/**
 * Moves to the specified point without drawing a line.
 *
 * @param pt The new current point
 */

   public void moveTo(Point2D pt) {
      moveTo(pt.getX(), pt.getY());
   }

/**
 * Draws a line to the specified point.
 *
 * @param x The x coordinate of the endpoint
 * @param y The y coordinate of the endpoint
 */

   public void lineTo(double x, double y) {
      elements.add(new LineToElement(x, y));
   }

/**
 * Draws a line to the specified point.
 *
 * @param pt The endpoint of the line
 */

   public void lineTo(Point2D pt) {
      lineTo(pt.getX(), pt.getY());
   }

/**
 * Draws an arc from the current point.  The parameters <code>rx</code>
 * and <code>ry</code> indicate the radii of the ellipse from which the
 * arc is taken; <code>start</code> and <code>sweep</code> represent the
 * starting and sweep angles of the arc, which implicitly define the
 * center of the arc.  All angles are measured in degrees counterclockwise
 * from the +<i>x</i> axis.
 *
 * @param rx The radius of the ellipse in the x direction
 * @param ry The radius of the ellipse in the y direction
 * @param start The start angle of the arc
 * @param sweep The sweep angle of the arc
 */

   public void arcTo(double rx, double ry, double start, double sweep) {
      elements.add(new ArcToElement(rx, ry, start, sweep));
   }

/**
 * Draws a Bezier curve using the specified control points.
 *
 * @param x1 The x coordinate of the first control point
 * @param y1 The y coordinate of the first control point
 * @param x2 The x coordinate of the second control point
 * @param y2 The y coordinate of the second control point
 * @param x3 The x coordinate of the endpoint
 * @param y3 The y coordinate of the endpoint
 */

   public void curveTo(double x1, double y1, double x2, double y2,
                       double x3, double y3) {
      elements.add(new CurveToElement(x1, y1, x2, y2, x3, y3));
   }

/**
 * Draws a Bezier curve using the specified control points.
 *
 * @param p1 The first control point
 * @param p2 The second control point
 * @param p3 The endpoint
 */

   public void curveTo(Point2D p1, Point2D p2, Point2D p3) {
      curveTo(p1.getX(), p1.getY(), p2.getX(), p2.getY(),
              p3.getX(), p3.getY());
   }

/* Protected methods */

   protected Rectangle2D getBounds() {
      Rectangle2D bb = new Rectangle2D.Double();
      for (PathElement element : elements) {
         element.adjustBounds(bb);
      }
      return bb;
   }
      
   protected void dumpPath(PPOutputStream os) {
      Rectangle2D bb = getBounds();
      os.print("<a:pathLst>");
      os.print("<a:path w='" + PPUtil.pointsToUnits(bb.getWidth()) +
               "' h='" + PPUtil.pointsToUnits(bb.getHeight()) + "'>");
      for (PathElement element : elements) {
         element.dumpElement(os);
      }
      os.print("</a:path>");
      os.print("</a:pathLst>");
   }

/* Private instance variables */

   private ArrayList<PathElement> elements;

}

abstract class PathElement {

   public abstract void adjustBounds(Rectangle2D bb);
   public abstract void dumpElement(PPOutputStream os);

}

class MoveToElement extends PathElement {

   public MoveToElement(double x, double y) {
      this.x = x;
      this.y = y;
   }

   @Override
   public void adjustBounds(Rectangle2D bb) {
      if (bb.isEmpty()) {
         bb.setRect(x, y, 0, 0);
      }
   }
   
   @Override
   public void dumpElement(PPOutputStream os) {
      os.print("<a:moveTo>");
      os.print("<a:pt " + os.getOffsetTag(x, y) + "/>");
      os.print("</a:moveTo>");
   }

/* Private instance variables */

   private double x;
   private double y;

}

class LineToElement extends PathElement {

   public LineToElement(double x, double y) {
      this.x = x;
      this.y = y;
   }

   @Override
   public void adjustBounds(Rectangle2D bb) {
      bb.add(x, y);
   }

   @Override
   public void dumpElement(PPOutputStream os) {
      os.print("<a:lnTo>");
      os.print("<a:pt " + os.getOffsetTag(x, y) + "/>");
      os.print("</a:lnTo>");
   }

/* Private instance variables */

   private double x;
   private double y;

}

class ArcToElement extends PathElement {

   public ArcToElement(double rx, double ry, double start, double sweep) {
      this.rx = rx;
      this.ry = ry;
      this.start = start;
      this.sweep = sweep;
   }

   @Override
   public void adjustBounds(Rectangle2D bb) {
      // Fix this eventually to compute the true bounds
   }

   @Override
   public void dumpElement(PPOutputStream os) {
      os.print("<a:arcTo wR='" + PPUtil.pointsToUnits(rx) +
               "' hR='" + PPUtil.pointsToUnits(ry) +
               "' stAng='" + Math.round(-60000 * start) +
               "' swAng='" + Math.round(-60000 * sweep) + "'/>");
   }

/* Private instance variables */

   private double rx;
   private double ry;
   private double start;
   private double sweep;

}

class CurveToElement extends PathElement {

   public CurveToElement(double x1, double y1, double x2, double y2,
                         double x3, double y3) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      this.x3 = x3;
      this.y3 = y3;
   }

   @Override
   public void adjustBounds(Rectangle2D bb) {
      // Fix this eventually to compute the true bounds
      bb.add(x1, y1);
      bb.add(x2, y2);
      bb.add(x3, y3);
   }

   @Override
   public void dumpElement(PPOutputStream os) {
      os.print("<a:cubicBezTo>");
      os.print("<a:pt " + os.getOffsetTag(x1, y1) + "/>");
      os.print("<a:pt " + os.getOffsetTag(x2, y2) + "/>");
      os.print("<a:pt " + os.getOffsetTag(x3, y3) + "/>");
      os.print("</a:cubicBezTo>");
   }

/* Private instance variables */

   private double x1;
   private double y1;
   private double x2;
   private double y2;
   private double x3;
   private double y3;

}
