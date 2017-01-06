/*
 * File: PPFreeform.java
 * ---------------------
 * This class represents a freeform curve on a PowerPoint slide.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPOutputStream;
import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * This class represents a freeform curve on a PowerPoint slide.
 */

public class PPFreeform extends PPSimpleShape {

/**
 * Creates a new <code>PPFreeform</code> shape from the specified path.
 *
 * @param path The path that defines this freeform object
 */

   public PPFreeform(PPPath path) {
      this.path = path;
   }

/**
 * Creates a new <code>PPFreeform</code> shape from the specified path.
 * This version allows the client to specify the shape options.
 *
 * @param path The path that defines this freeform object
 * @param options The formatting options for the shape
 */

   public PPFreeform(PPPath path, String options) {
      this.path = path;
      setOptions(options);
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

/* Protected methods */

   @Override
   protected void dumpShape(PPOutputStream os) {
      os.print("<p:sp>");
      os.print("<p:nvSpPr>");
      os.print("<p:cNvPr id='" + getShapeId() + "' " +
               "name='" + getName() + "'/>");
      os.print("<p:cNvSpPr/>");
      os.print("<p:nvPr/>");
      os.print("</p:nvSpPr>");
      os.print("<p:spPr bwMode='auto'>");
      Rectangle2D bb = path.getBounds();
      os.print("<a:xfrm>");
      os.print("<a:off " + os.getOffsetTag(bb.getX(), bb.getY()) + "/>");
      os.print("<a:ext cx='" + PPUtil.pointsToUnits(bb.getWidth()) +
               "' cy='" + PPUtil.pointsToUnits(bb.getHeight()) + "'/>");
      os.print("</a:xfrm>");
      os.adjustOffset(-bb.getX(), -bb.getY());
      os.print("<a:custGeom>");
      path.dumpPath(os);
      os.print("</a:custGeom>");
      os.adjustOffset(bb.getX(), bb.getY());
      Color color = getLineColor();
      os.print("<a:noFill/>");
      os.print("<a:ln w='" + PPUtil.pointsToUnits(getLineWeight()) + "'>");
      os.print("<a:solidFill>");
      if (color == null) {
         os.print("<a:schemeClr val='tx1'/>");
      } else {
         os.print(PPUtil.getColorTag(color));
      }
      os.print("</a:solidFill>");
      os.print("<a:prstDash val='solid'/>");
      os.print("<a:round/>");
      os.print("<a:headEnd" + getArrowTag(getStartArrow()) + "/>");
      os.print("<a:tailEnd" + getArrowTag(getEndArrow()) + "/>");
      os.print("</a:ln>");
      os.print("<a:effectLst/>");
      os.print("</p:spPr>");
      os.print("</p:sp>");
   }

/* Private instance variables */

   private PPPath path;

}

