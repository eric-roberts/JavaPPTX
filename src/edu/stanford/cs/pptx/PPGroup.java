/*
 * File: PPGroup.java
 * ------------------
 * This class represents a PowerPoint group.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPOutputStream;
import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This class defines a PowerPoint object that consists of a collection
 * of other objects.  Once assembled, the objects that make up the
 * <code>PPGroup</code> can be manipulated as a unit.
 *
 * <p>The following code creates a group consisting of a red rectangle
 * and a green oval, which then appears as a unit when the mouse is
 * clicked:</p>
 *
 *<pre>
 *    PPSlide slide = new PPSlide();
 *    double xc = slide.getWidth() / 2;
 *    double yc = slide.getHeight() / 2;
 *    PPGroup group = new PPGroup();
 *    group.add(new PPRect(200, 100, "/color:red"));
 *    group.add(new PPOval(200, 100, "/color:green"));
 *    slide.add(group, xc - group.getWidth() / 2, yc - group.getHeight() / 2);
 *    group.appear();
 *</pre>
 */

public class PPGroup extends PPShape {

/**
 * Creates an empty <code>PPGroup</code> object.
 */

   public PPGroup() {
      bounds = new Rectangle2D.Double();
      contents = new ArrayList<PPShape>();
   }

/**
 * Adds a new shape to this <code>PPGroup</code>.
 *
 * @param shape The shape to add
 */

   public void add(PPShape shape) {
      contents.add(shape);
      bounds.add(shape.getBounds());
   }

/**
 * Adds a new shape to this <code>PPGroup</code> at the offset position
 * specified by the point (<code>x</code>,&nbsp;<code>y</code>)
 * relative to the upper left corner of the group.
 *
 * @param shape The shape to add
 * @param x The <i>x</i>-coordinate of the shape
 * @param y The <i>y</i>-coordinate of the shape
 */

   public void add(PPShape shape, double x, double y) {
      shape.setInitialLocation(x, y);
      add(shape);
   }

/**
 * Sends the specified shape to the front of the z-ordering.
 *
 * @param shape The shape to be moved to the front
 */

   public void sendToFront(PPShape shape) {
      contents.remove(shape);
      contents.add(shape);
   }

/**
 * Sends the specified shape to the back of the z-ordering.
 *
 * @param shape The shape to be moved to the back
 */

   public void sendToBack(PPShape shape) {
      contents.remove(shape);
      contents.add(0, shape);
   }

/**
 * Returns the list of shapes contained in this <code>PPGroup</code>.
 *
 * @return The list of shapes contained in this <code>PPGroup</code>
 */

   public ArrayList<PPShape> getShapes() {
      return contents;
   }

/**
 * Sets the bounding rectangle for this <code>PPGroup</code>.
 *
 * @param bounds A rectangle specifying the new bounds
 */

   public void setBounds(Rectangle2D bounds) {
      double mx = 0;
      double my = 0;
      for (PPShape shape : contents) {
         mx = Math.min(mx, shape.getX());
         my = Math.min(my, shape.getY());
      }
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

/* Protected methods */

   @Override
   protected void dumpShape(PPOutputStream os) {
      os.print("<p:grpSp>");
      os.print("<p:nvGrpSpPr>");
      os.print("<p:cNvPr id='" + getShapeId() + "' " +
               "name='" + getName() + "'/>");
      os.print("<p:cNvGrpSpPr/>");
      os.print("<p:nvPr/>");
      os.print("</p:nvGrpSpPr>");
      os.print("<p:grpSpPr>");
      Point2D pt = getInitialLocation();
      Rectangle2D bb = getGroupBounds();
      String offsetTag = os.getOffsetTag(pt.getX() + bb.getX(),
                                         pt.getY() + bb.getY());
      os.print("<a:xfrm>");
      os.print("<a:off " + offsetTag + "/>");
      os.print("<a:ext cx='" + PPUtil.pointsToUnits(bb.getWidth()) + "' " +
               "cy='" + PPUtil.pointsToUnits(bb.getHeight()) + "'/>");
      os.print("<a:chOff " + offsetTag + "/>");
      os.print("<a:chExt cx='" + PPUtil.pointsToUnits(bb.getWidth()) + "' " +
               "cy='" + PPUtil.pointsToUnits(bb.getHeight()) + "'/>");
      os.print("</a:xfrm>");
      os.print("</p:grpSpPr>");
      Point2D start = getInitialLocation();
      os.adjustOffset(start.getX(), start.getY());
      for (PPShape shape : contents) {
         shape.dumpShape(os);
      }
      os.adjustOffset(-getX(), -getY());
      os.print("</p:grpSp>");
   }

   @Override
   protected void dumpShapeRels(PPOutputStream os) {
      for (PPShape shape : contents) {
         shape.dumpShapeRels(os);
      }
   }

/* Private methods */

   private Rectangle2D getGroupBounds() {
      int nShapes = contents.size();
      Rectangle2D bounds = new Rectangle2D.Double(0, 0, 0, 0);
      for (int i = 0; i < nShapes; i++) {
         if (i == 0) {
            bounds.setRect(contents.get(i).getBounds());
         } else {
            bounds.add(contents.get(i).getBounds());
         }
      }
      return bounds;
   }

/* Private instance variables */

   private ArrayList<PPShape> contents;
   private Rectangle2D bounds;

}
