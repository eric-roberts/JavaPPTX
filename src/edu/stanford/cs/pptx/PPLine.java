/*
 * File: PPLine.java
 * -----------------
 * This class represents a line on a PowerPoint slide.
 */

package edu.stanford.cs.pptx;

import edu.stanford.cs.pptx.util.PPOutputStream;
import edu.stanford.cs.pptx.util.PPUtil;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * This class represents a line on a PowerPoint slide.  The usual approach
 * to working with a <code>PPLine</code> shape is to create the line
 * and then add it to a slide, as illustrated in the following code,
 * which adds lines that stretch diagonally across the PowerPoint
 * screen in both directions:
 *
 *<pre>
 *    PPSlide slide = new PPSlide();
 *    double width = slide.getWidth();
 *    double height = slide.getHeight();
 *    PPLine diagonalNWtoSE = new PPLine(0, 0, width, height);
 *    PPLine diagonalSWtoNE = new PPLine(0, height, width, 0);
 *    slide.add(diagonalNWtoSE);
 *    slide.add(diagonalSWtoNE);
 *</pre>
 *
 * It is also possible to set properties of the <code>PPLine</code> shape,
 * either by passing an option string to the constructor or by invoking methods
 * in the class.
 */

public class PPLine extends PPSimpleShape {

/**
 * Creates a new <code>PPLine</code> shape that connects the points
 * (<i>x<sub>0</sub></i>,<i>y<sub>0</sub></i>) and
 * (<i>x<sub>1</sub></i>,<i>y<sub>1</sub></i>).
 *
 * @param x0 The <i>x</i> coordinate of the starting point
 * @param y0 The <i>y</i> coordinate of the starting point
 * @param x1 The <i>x</i> coordinate of the ending point
 * @param y1 The <i>y</i> coordinate of the ending point
 */

   public PPLine(double x0, double y0, double x1, double y1) {
      if (x0 > x1) {
         quadrant = (y0 >= y1) ? 2 : 3;
      } else {
         quadrant = (y0 >= y1) ? 1 : 4;
      }
      double x = Math.min(x0, x1);
      double y = Math.min(y0, y1);
      double width = Math.abs(x1 - x0);
      double height = Math.abs(y1 - y0);
      setBounds(x, y, width, height);
   }

/**
 * Creates a new <code>PPLine</code> shape from its endpoints and an option
 * string.  This version of the constructor operates in much the same manner
 * as the standard {@link #PPLine(double,double,double,double) PPLine}
 * constructor, but allows the caller to specify an option string.
 * The advantage of embedding the option string in the constructor is that
 * doing so often makes it possible to add the shape to the slide immediately
 * without storing it in a temporary variable.
 *
 * @param x0 The <i>x</i> coordinate of the starting point
 * @param y0 The <i>y</i> coordinate of the starting point
 * @param x1 The <i>x</i> coordinate of the ending point
 * @param y1 The <i>y</i> coordinate of the ending point
 * @param options The options (see {@link PPAutoShape#setOptions(String)})
 */

   public PPLine(double x0, double y0, double x1, double y1, String options) {
      this(x0, y0, x1, y1);
      setOptions(options);
   }

/**
 * Creates a new <code>PPLine</code> shape from its <i>dx</i> and
 * <i>dy</i> displacements.  This version of the constructor is useful if
 * you want to create the line and the add it to the slide at a position
 * computed subsequently.
 *
 * @param dx The displacement of the line in the <i>x</i> direction
 * @param dy The displacement of the line in the <i>y</i> direction
 */

   public PPLine(double dx, double dy) {
      this(0, 0, dx, dy);
   }

/**
 * Sets the options for this line.  The string may contain the
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
      os.print("<p:cNvSpPr>");
      os.print("<a:spLocks noChangeShapeType='1'/>");
      os.print("</p:cNvSpPr>");
      os.print("<p:nvPr/>");
      os.print("</p:nvSpPr>");
      os.print("<p:spPr bwMode='auto'>");
      switch (quadrant) {
       case 1: os.print("<a:xfrm flipV='1'>"); break;
       case 2: os.print("<a:xfrm flipH='1' flipV='1'>"); break;
       case 3: os.print("<a:xfrm flipH='1'>"); break;
       case 4: os.print("<a:xfrm>"); break;
      }
      Point2D pt = getInitialLocation();
      os.print("<a:off " + os.getOffsetTag(pt.getX(), pt.getY()) + "/>");
      os.print("<a:ext cx='" + PPUtil.pointsToUnits(getWidth()) + "' " +
               "cy='" + PPUtil.pointsToUnits(getHeight()) + "'/>");
      os.print("</a:xfrm>");
      os.print("<a:prstGeom prst='line'>");
      os.print("<a:avLst/>");
      os.print("</a:prstGeom>");
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

   private int quadrant;

}
