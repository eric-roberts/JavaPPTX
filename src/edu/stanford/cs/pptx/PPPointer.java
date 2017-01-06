/*
 * File: PPPointer.java
 * --------------------
 * This class represents a pointer that moves through a series of control
 * points.
 */

package edu.stanford.cs.pptx;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * This class represents a pointer that moves through a series of control
 * points.
 */

public class PPPointer extends PPFreeform {

/**
 * Creates a new <code>PPPointer</code> shape from the specified path.
 *
 * @param path The path followed by the pointer
 */

   public PPPointer(PPPath path) {
      super(path);
      setStartArrow("circle");
      setEndArrow("triangle");
   }

/**
 * Creates a new <code>PPPointer</code> shape from the specified points.
 *
 * @param points The points on the path
 */

   public PPPointer(Point2D... points) {
      this(createPointerPath(points));
   }

/**
 * Creates a pointer that links the center of the <code>start</code> object
 * with the center of one of the edges of the <code>end</code> object.
 * The path consists of horizontal and vertical segments with rounded
 * corners.  The displacements from the current point and the next one
 * are given by the list of values.  These values alternate between x
 * and y displacements.
 *
 * @param start The starting object
 * @param end The ending object
 * @param displacements An alternating list of x and y displacements
 */

   public PPPointer(PPShape start, PPShape end, double... displacements) {
      this(createPointerPath(createPointArray(start, end, displacements)));
   }
   
/**
 * Sets the options for this path.  The string may contain the
 * following option specifications:
 *
 * <table width=100% border=0 cellspacing=6 cellpadding=0 summary="">
 * <tr><td><code>/lineColor:</code><i>color</i>
 *     <br><code>/line:</code><i>color</i></td>
 *     <td>Sets the line color for this line.  Colors may be specified
 *         using their Java names or as a six-digit hexadecimal string
 *         with two digits for each of the red, green, and blue
 *         components.</td></tr>
 * <tr><td><code>/lineWeight:</code><i>pixels</i>
 *     <br><code>/weight:</code><i>pixels</i></td>
 *     <td>Specifies the line weight in pixels.</td></tr>
 * </table>
 *
 * @param options A string specifying the options for this line
 */

   public void setOptions(String options) {
      super.setOptions(options);
   }

/* Static methods */

/**
 * Creates a path from the array of points.  The method inserts rounded
 * corners at each inflection point.
 *
 * @param points The points on the path
 * @return The path that contains these points
 */

   public static PPPath createPointerPath(Point2D... points) {
      PPPath path = new PPPath();
      int nPoints = points.length;
      path.moveTo(points[0]);
      for (int i = 1; i < nPoints - 1; i++) {
         double x0 = points[i - 1].getX();
         double y0 = points[i - 1].getY();
         double x1 = points[i].getX();
         double y1 = points[i].getY();
         double x2 = points[i + 1].getX();
         double y2 = points[i + 1].getY();
         double theta = Math.atan2(y0 - y1, x1 - x0);
         double phi = Math.atan2(y1 - y2, x2 - x1);
         double q = Math.signum((x1 - x0) * (y2 - y0) - (y1 - y0) * (x2 - x0));
         if (q == 0) {
            path.lineTo(x1, y1);
         } else {
            double start = theta + q * Math.PI / 2;
            double sweep = phi - theta;
            double ax = x1 - ARC_RADIUS * Math.cos(theta);
            double ay = y1 + ARC_RADIUS * Math.sin(theta);
            path.lineTo(ax, ay);
            path.arcTo(ARC_RADIUS, ARC_RADIUS, Math.toDegrees(start),
                                               Math.toDegrees(sweep));
         }
      }
      path.lineTo(points[nPoints - 1]);
      return path;
   }

/**
 * Creates an array of points that link the center of the <code>start</code>
 * object with the center of one of the edges of the <code>end</code> object.
 * The path consists of horizontal and vertical segments with rounded
 * corners.  The displacements from the current point and the next one
 * are given by the list of values.  These values alternate between x
 * and y displacements.
 *
 * @param start The starting object
 * @param end The ending object
 * @param displacements An alternating list of x and y displacements
 * @return An array of points linking <code>start</code> and <code>end</code>
 */

   public static Point2D[] createPointArray(PPShape start, PPShape end,
                                            double... displacements) {
      ArrayList<Point2D> points = new ArrayList<Point2D>();
      Point2D center = start.getCenter();
      points.add(center);
      double cx = center.getX();
      double cy = center.getY();
      boolean vertical = false;
      for (double delta : displacements) {
         if (delta != 0) {
            if (vertical) {
               cy += delta;
            } else {
               cx += delta;
            }
            points.add(new Point2D.Double(cx, cy));
         }
         vertical = !vertical;
      }
      center = end.getCenter();
      double ex = end.getX() - 1;
      if (cx > ex) ex += 2 * (center.getX() - end.getX()) + 2;
      double ey = center.getY();
      if (ey != cy) points.add(new Point2D.Double(cx, ey));
      points.add(new Point2D.Double(ex, ey));
      return points.toArray(new Point2D[points.size()]);
   }

/**
 * Adjusts the start of the point array by the specified displacements.
 *
 * @param points An array of points
 * @param dx The x adjustment
 * @param dy The y adjustment
 */

   public static void adjustStart(Point2D[] points, double dx, double dy) {
      Point2D p0 = points[0];
      Point2D p1 = points[1];
      if (p0.getX() == p1.getX()) p1.setLocation(p1.getX() + dx, p1.getY());
      if (p0.getY() == p1.getY()) p1.setLocation(p1.getX(), p1.getY() + dy);
      p0.setLocation(p0.getX() + dx, p0.getY() + dy);
   }

/**
 * Adjusts the end of the point array by the specified displacements.
 *
 * @param points An array of points
 * @param dx The x adjustment
 * @param dy The y adjustment
 */

   public static void adjustEnd(Point2D[] points, double dx, double dy) {
      int n = points.length;
      Point2D p0 = points[n - 1];
      Point2D p1 = points[n - 2];
      if (p0.getX() == p1.getX()) p1.setLocation(p1.getX() + dx, p1.getY());
      if (p0.getY() == p1.getY()) p1.setLocation(p1.getX(), p1.getY() + dy);
      p0.setLocation(p0.getX() + dx, p0.getY() + dy);
   }

/* Private constants */

   private static final double ARC_RADIUS = 8;

}
